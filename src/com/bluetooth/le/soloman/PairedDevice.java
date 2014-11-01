package com.bluetooth.le.soloman;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class PairedDevice extends Activity{

	public GlobalVar appState;
	public TextView paired_ther;
	public Button del_ther, paired_rtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		appState = (GlobalVar) getApplicationContext(); // 获得全局变量
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 设置成竖屏		
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 	//去掉title
		setContentView(R.layout.paireddevice);
		
		findView();
		setOnClickListener();
		readDevice();
	}

	private void readDevice() {
		// TODO Auto-generated method stub
		//Thermometer温度计
        if ( appState.file.isFileExist("inurse/Thermometer.txt") ){
        	String lastDevice =  appState.file.readFile(appState.file.SDPATH + "inurse/Thermometer.txt");
        	paired_ther.setText(lastDevice);
        }
	}

	private void setOnClickListener() {
		// TODO Auto-generated method stub
		del_ther.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				appState.file.deleteFile(appState.file.SDPATH + "inurse/Thermometer.txt");
				paired_ther.setText("");
			}
		});
		
		paired_rtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void findView() {
		// TODO Auto-generated method stub
		paired_ther = (TextView) findViewById(R.id.paired_ther);
		del_ther = (Button) findViewById(R.id.del_ther);
		paired_rtn = (Button) findViewById(R.id.paired_rtn);
	}
}
