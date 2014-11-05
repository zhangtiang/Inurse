package com.bluetooth.le.soloman;

import java.math.BigDecimal;
import com.soloman.DB.Database;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.database.Cursor;
import android.telephony.TelephonyManager;

public class GlobalVar extends Application{
	public String firm;
	//public Integer version = Integer.valueOf(android.os.Build.VERSION.SDK);
	public String version = android.os.Build.VERSION.RELEASE;
	public float screenWidth, screenHeight;
	public float density; // 屏幕密度（0.75 / 1.0 /1.25/ 1.5）
	public int densityDpi; // 屏幕密度DPI（120 / 160 /200/ 240）
	public float wh; // 长宽比
	public TelephonyManager tm;
	public String IMEI;
	public String card1num, simserial;
	
	public FileUtils file = new FileUtils();
	public String sdcard;
	
	public String userID, userName,note, deviceAddress;
	public boolean autoConnect, dataArrive;
	
	public int autodel, automail, autoupload, autosave;
	public String whendel, mail1, mail2, whenmail, mailtime1, mailtime2, serverurl,path, ext, separate;
	public int fielduid, fieldfname,fieldlname, fielddevicetype, fielddeviceid,fielddate, fieldvalue, fieldmode, fieldunit, fieldnote;
	
	 /**读写BLE终端*/
	public BluetoothAdapter BluetoothAdapter = null;
	public BluetoothLeClass mBLE_send, mBLE_reciv;
	public BluetoothGattCharacteristic  gattCharacteristic_send, gattCharacteristic_reciv;
	public byte c[] =new byte [16];
	
	public boolean runThread = false;
	public boolean firstActivityRunning = false;
	
	public static final int REQUEST_TIMEOUT = 3*1000;//设置请求超时3秒钟  
	public static final int SO_TIMEOUT = 50*1000;  //设置等待数据超时时间5秒钟  
	
	public void init_BluetoothLeClass () {
		mBLE_reciv = new BluetoothLeClass(this);
		mBLE_send = new BluetoothLeClass(this);
	}
	
	public void sendData(BluetoothGattCharacteristic  gattCharacteristic, byte c[]) {
    	//设置数据内容
		gattCharacteristic.setValue(c);
		//往蓝牙模块写入数据
		 mBLE_send.writeCharacteristic(gattCharacteristic);
    }
	

	// 获取温度
	public void getTemp(BluetoothGattCharacteristic gattCharacteristic) {
		byte c1[] = new byte[5];
		c1[0] = (byte) 0xf5;
		c1[1] = 0x10;
		c1[2] = 0x00;
		c1[3] = 0x00;
		c1[4] = (byte) 0xff;

		sendData(gattCharacteristic, c1);
	}
	
	public float surface, body, room;
	public String mode, unit;
	//计算温度
	public void calcTemp(byte c[]){
		if ( c[1]==0x10  && c[2] == 0x08){
			surface =  (float) (( (int) ((c[4] & 0xff)) * 256 + (int)(c[3] & 0xff) ) / 10.0);
			body = (float) (( (int) ((c[6] & 0xff)) * 256 + (int)(c[5] & 0xff )) / 10.0);
			room = (float) (( (int) ((c[8] & 0xff)) * 256 +(int) (c[7] & 0xff )) / 10.0);	
			
			if (c[9] == 0x01){ 				
				mode = "surface";
			}else if (c[9] == 0x00){ 				
				mode = "body";
			}else if (c[9] == 0x02){ 				
				mode = "room";
			}
			
			if (c[10] == 0x00){
				unit = "℃";
			}else if (c[10] == 0x01){
				unit = "℉";
				surface = surface * 9 / 5 + 32;
				body = body * 9 / 5 + 32;
				room = room * 9 / 5 + 32;
			}
			BigDecimal bd = new BigDecimal(surface);
			bd = bd.setScale(1,BigDecimal.ROUND_HALF_UP);  
			surface = bd.floatValue();
			
			bd = new BigDecimal(body);
			bd = bd.setScale(1,BigDecimal.ROUND_HALF_UP);  
			body = bd.floatValue();
			
			bd = new BigDecimal(room);
			bd = bd.setScale(1,BigDecimal.ROUND_HALF_UP);  
			room = bd.floatValue();
		}
	}
		
	// 设置模式
	public void setMode(BluetoothGattCharacteristic gattCharacteristic, byte mode, byte unit) {
		byte c1[] = new byte[7];
		c1[0] = (byte) 0xf5;
		c1[1] = 0x11;
		c1[2] = 0x02;
		c1[3] = mode; // body surface room
		c1[4] = unit; // ℃ ℉  
		c1[5] = (byte) (c[3] ^ c[4]);
		c1[6] = (byte) 0xff;

		sendData(gattCharacteristic, c1);
	}
	
	//数据库-----------------------------------
	private Database database;

	public Boolean isDBOpen(){
		return database.isOpen();
	}
	
	public Database getDB() {
		System.out.println("全局getDB");
		database = new Database(this);
		database.open();
		return database;
	}
	
	public void dbClose(){
		database.close();
	}
	
	public void add_patient(String id, String firstname, String lastname, String tel, String mail, String note ){
		database.add_patient( id,  firstname,  lastname, tel, mail, note);
	}
	
	public Cursor get_patient(){
		return database.get_patient();
	}
	
	public Cursor get_patient(String uid){
		return database.get_patient(uid);
	}
	
	public int Update_patient(String id, String firstname, String lastname, String tel, String mail, String note ) {
		return database.Update_patient(id, firstname, lastname, tel, mail, note );
	}
	
	public long del_patient(String id) {
		return database.del_patient(id);
	}
	
	public long add_Record(String id, String devicetype, String mode, String unit, String value, String date, String time ) {
		return database.add_Record(id, devicetype, mode, unit, value, date, time);
	}
	
	public Cursor getRecord(String devicetype ) {
		return database.getRecord(devicetype );
	}
	
	public Cursor getRecord(String id, String devicetype ) {
		return database.getRecord(id, devicetype );
	}
		
	public Cursor getRecord(String id, String devicetype, String mode ) {
		return database.getRecord(id, devicetype, mode );
	}
	
	public int updateRecord(String id, String date, String note ) {
		return database.updateRecord(id, date , note);
	}
	
	public long delRecord(String id, String date) {
		return database.delRecord(id, date);
	}
	
	public long add_setting(int autodel, String whendel, 
			int automail, String mail1, String mail2, String whenmail, String mailtime1, String mailtime2,
			int autoupload, String serverurl, 
			int autosave, String path, String ext, String separate,
			int userid, int fname, int lname, int devicetype, int deviceid, int date, int value, int mode, int unit, int note) {
		return database.add_setting(autodel, whendel, automail, mail1, mail2, whenmail, mailtime1, mailtime2, autoupload, serverurl, autosave, path, ext, separate, userid, fname, lname, devicetype, deviceid, date, value, mode, unit, note);
	}
	
	public Cursor getSetting ( ) {
		return database.getSetting();
	}
	
	public int updateSetting(int autodel, String whendel, 
			int automail, String mail1, String mail2, String whenmail, String mailtime1, String mailtime2,
			int autoupload, String serverurl, 
			int autosave, String path, String ext, String separate,
			int userid, int fname, int lname, int devicetype, int deviceid, int date, int value, int mode, int unit, int note ) {
		return database.updateSetting(autodel, whendel, automail, mail1, mail2, whenmail, mailtime1, mailtime2, autoupload, serverurl, autosave, path, ext, separate, userid, fname, lname, devicetype, deviceid, date, value, mode, unit, note);
	}
}
