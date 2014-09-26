package com.bluetooth.le.soloman;

import android.app.Application;
import android.bluetooth.BluetoothGattCharacteristic;

public class GlobalVar extends Application{

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
		
	// 设置模式
	public void setMode(BluetoothGattCharacteristic gattCharacteristic) {
		byte c1[] = new byte[7];
		c1[0] = (byte) 0xf5;
		c1[1] = 0x11;
		c1[2] = 0x02;
		c1[3] = 0x00; // body
		c1[4] = 0x00; // ℃
		c1[5] = (byte) (c[3] ^ c[4]);
		c1[6] = (byte) 0xff;

		sendData(gattCharacteristic, c1);
	}
}
