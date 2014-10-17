package com.bluetooth.le.soloman;

import java.math.BigDecimal;

import android.app.Application;
import android.bluetooth.BluetoothGattCharacteristic;

public class GlobalVar extends Application{

	public FileUtils file = new FileUtils();
	public String deviceAddress;
	public boolean autoConnect;
	
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
}
