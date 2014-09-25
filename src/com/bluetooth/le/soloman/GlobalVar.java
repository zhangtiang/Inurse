package com.bluetooth.le.soloman;

import java.math.BigDecimal;

import android.app.Application;
import android.bluetooth.BluetoothGattCharacteristic;

public class GlobalVar extends Application{

	 /**读写BLE终端*/
	public BluetoothLeClass mBLE_send, mBLE_reciv;
	public byte c[] =new byte [16];
	
	
	public int step;	//步数 
	public float distance, caroli;//里程 卡路里
	public int sporttime; //运动时间 分钟
	public String sptime = "00:00";//运动时间00:00
	
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
	
	//获取实时数据
	public void StartSportDateForTime (BluetoothGattCharacteristic  gattCharacteristic) {		
		c[0] = 0x09;	//
		c[1] = 0x00;	//A
		c[2] = 0x00;	//B
		c[3] = 0x00;	//C
		c[4] = 0x00;	//D
		c[5] = 0x00;	//E
		c[6] = 0x00;	//F
		c[7] = 0x00;	//G
		c[8] = 0x00;	//H
		c[9] = 0x00;	//I
		c[10] = 0x00;	//J
		c[11] = 0x00;	//K
		c[12] = 0x00;	//L
		c[13] = 0x00;	//M
		c[14] = 0x00;	//N
		c[15] = c[0];	//CRC
		for (int i = 1; i<15; i++){
			c[15] += c[i];
		}
		c[15] &= 0xff;
		sendData( gattCharacteristic, c);
	}
	
	//停止实时数据
		public void StopSportDateForTime (BluetoothGattCharacteristic  gattCharacteristic) {		
			c[0] = 0x0A;	//
			c[1] = 0x00;	//A
			c[2] = 0x00;	//B
			c[3] = 0x00;	//C
			c[4] = 0x00;	//D
			c[5] = 0x00;	//E
			c[6] = 0x00;	//F
			c[7] = 0x00;	//G
			c[8] = 0x00;	//H
			c[9] = 0x00;	//I
			c[10] = 0x00;	//J
			c[11] = 0x00;	//K
			c[12] = 0x00;	//L
			c[13] = 0x00;	//M
			c[14] = 0x00;	//N
			c[15] = c[0];	//CRC
			for (int i = 1; i<15; i++){
				c[15] += c[i];
			}
			c[15] &= 0xff;
			sendData( gattCharacteristic, c);
		}
		
		//处理实施运动数据
		public void ExecuteStortData (byte c[]) {
			step =(c[1] & 0xff) *256 *256 + (c[2] & 0xff) *256 +(c[3] & 0xff);
			
			caroli = (float) ( ((c[7] & 0xff) * 256 * 256 + (c[8] & 0xff) * 256 +(c[9] & 0xff)) / 100.00 ) ;
			//BigDecimal   b   =   new   BigDecimal(caroli);  
			//caroli   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue();  
			
			distance = (float) ( ((c[10] & 0xff) * 256 *256 + (c[11] & 0xff) * 256 + (c[12] & 0xff)) / 100.00 ) ;
			
			sporttime = (c[13] & 0xff) *256 +(c[14] & 0xff); //分钟
			String hour,min;
			hour = String.valueOf(sporttime / 60);
			min = String.valueOf(sporttime % 60);
			if (hour.length() < 2){
				hour = "0" + hour;
			}
			if (min.length() < 2){
				min = "0" + min;
			}
			sptime = hour + ":" + min;
		}
	
	// 获取版本
	public void getVer(BluetoothGattCharacteristic gattCharacteristic) {
		c[0] = (byte) 0xf5;
		c[1] = 0x01;
		c[2] = 0x00;

		sendData(gattCharacteristic, c);
	}
		//获取温度
		public void getTemp (BluetoothGattCharacteristic  gattCharacteristic) {		
			byte c1[] =new byte [5];
			c1[0] = (byte) 0xf5;	
			c1[1] = 0x10;	
			c1[2] = 0x00;
		c1[3] = 0x00; 
		c1[4] =(byte) 0xff;
		
			sendData( gattCharacteristic, c1);
		}
		
		//设置模式
				public void setMode (BluetoothGattCharacteristic  gattCharacteristic) {		
					byte c1[] =new byte [7];
					c1[0] = (byte) 0xf5;	
					c1[1] = 0x11;	
					c1[2] = 0x02;
			        c1[3] = 0x00;	//body
			        c1[4] = 0x00;	//℃
			        c1[5] = (byte) (c[3] ^ c[4]);
					c1[6] = (byte) 0xff;

					sendData( gattCharacteristic, c1);
				}
}
