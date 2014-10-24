package com.bluetooth.le.soloman;

import java.math.BigDecimal;
import java.util.Date;

import com.soloman.DB.Database;

import android.app.Application;
import android.bluetooth.BluetoothGattCharacteristic;
import android.database.Cursor;
import android.text.format.Time;

public class GlobalVar extends Application{

	public FileUtils file = new FileUtils();
	public String userID, userName, deviceAddress;
	public boolean autoConnect, dataArrive;
	
	 /**读写BLE终端*/
	public BluetoothLeClass mBLE_send, mBLE_reciv;
	BluetoothGattCharacteristic  gattCharacteristic_send, gattCharacteristic_reciv;
	public byte c[] =new byte [16];
	
	public boolean runThread = false;
	public boolean firstActivityRunning = false;
	
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
	
	public Cursor getRecord(String id, String devicetype ) {
		return database.getRecord(id, devicetype );
	}
		
	public Cursor getRecord(String id, String devicetype, String mode ) {
		return database.getRecord(id, devicetype, mode );
	}
}
