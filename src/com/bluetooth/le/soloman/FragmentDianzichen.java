package com.bluetooth.le.soloman;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import com.bluetooth.le.soloman.R;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentDianzichen extends Fragment {

	public GlobalVar appState;
	private BluetoothAdapter mBluetoothAdapter = null;
	private BluetoothSocket btSocket = null;
	private OutputStream outStream = null;
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private static final String TAG = "Dianzichen";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		appState = (GlobalVar) getActivity().getApplicationContext(); // 获得全局变量
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return inflater.inflate(R.layout.fragment_sleep, container, false);	
		
		View view = inflater.inflate(R.layout.fragment_themometer, container, false);
		

		findView(view);

		
		// 得到当前线程的Looper实例,由于当前线程是UI线程也可以通过Looper.getMainLooper()得到
		Looper looper = Looper.myLooper();
		// 此处甚至可以不需要设置Looper,因为 Handler默认就使用当前线程的Looper
		messageHandler = new MessageHandler(looper);
	
		
        return view;       
	}

	
	
	
	private Handler messageHandler;
	private void updateHandler(Object obj) {
		// 创建一个Message对象,并把得到的网络信息赋值给Message对象
		Message message = Message.obtain();// 第一步
		message = Message.obtain();// 第一步
		message.obj = obj; // 第二步
		messageHandler.sendMessage(message);// 第三步
	}
	
	// 子类化一个Handler
	class MessageHandler extends Handler {
		public MessageHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			// 更新UI
			if (!((String) msg.obj == null)) {
				if ("dianzichendata".equals((String) msg.obj)) {
					if (appState.dataArrive){
						
					}					
				}

			}
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
//		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice("00:00:00:00:00:00");
		
		
		
		BluetoothDevice device = null;
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();  
		for (BluetoothDevice device1 : devices)
		{  
		    System.out.println(device1.getName());  
		    if ( "Electronic Scale".equals(device1.getName()) ){
		    	device = device1;
		    	break;
		    }
		} 

		
		
		try {
//			btSocket = mBluetoothAdapter.createRfcommSocketToServiceRecord(MY_UUID);
			final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";  
		    UUID uuid = UUID.fromString(SPP_UUID);  
			btSocket = device.createRfcommSocketToServiceRecord(uuid);  
		} catch (IOException e) {
			Log.e(TAG, "ON RESUME： Socket creation failed.", e);
		}
		mBluetoothAdapter.cancelDiscovery();
		try {
			btSocket.connect();
			Log.e(TAG, "ON RESUME： BT connection established, data transfer link open.");
		} catch (IOException e) {
			try {
				btSocket.close();
			} catch (IOException e2) {
				Log.e(TAG, "ON RESUME： Unable to close socket during connection failure", e2);
			}
		}
	}
	
	@Override
	public void onStop(){
		super.onStop();
		

	}
	
	/*
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		if (mAudioCapture != null) {
            mAudioCapture.stop();
            mAudioCapture.release();
            mAudioCapture = null;
        }
	}
	
	public void onClose() {
		if (mAudioCapture != null) {
            mAudioCapture.stop();
            mAudioCapture.release();
            mAudioCapture = null;
        }
	}
	*/
	
	public void findView(View view){
		
		
	}
	
	

		
}



