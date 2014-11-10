package com.soloman.shuimian;


import com.bluetooth.le.soloman.GlobalVar;
import com.bluetooth.le.soloman.R;
import com.bluetooth.le.soloman.gridUser;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.TextView;

public class FragmentShuimian extends Fragment {

	private GlobalVar appState;
	
	private Button btn_shuimianselectuser, btn_sleep, btn_wakeup;
	private CheckBox checkB_record;
	private Chronometer chronometer1;
	private long recordingTime = 0;// 记录下来的总时间

	//public sportDataThread st = null;
	
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
		
		View view = inflater.inflate(R.layout.fragment_shuimian, container, false);
		findView(view);	


				
        return view;       
	}
	


	public void findView(View view){
		btn_shuimianselectuser = (Button) view.findViewById(R.id.btn_shuimianselectuser);
		btn_sleep = (Button) view.findViewById(R.id.btn_sleep);
		btn_wakeup = (Button) view.findViewById(R.id.btn_wakeup);
		chronometer1 = (Chronometer)view.findViewById(R.id.chronometer1);
		chronometer1.setFormat("%s");
		checkB_record = (CheckBox) view.findViewById(R.id.checkB_record);
		
		btn_shuimianselectuser.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	Intent it = new Intent(getActivity(), gridUser.class);
    			startActivityForResult(it, 1);	
            }
		});
		
		btn_sleep.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {  
//            	chronometer1.setBase(SystemClock.elapsedRealtime());  
            	chronometer1.setBase(SystemClock.elapsedRealtime() - recordingTime);// 跳过已经记录了的时间，起到继续计时的作用
            	chronometer1.start(); 
            }
		});
		
		btn_wakeup.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {  
            	chronometer1.stop();
            	recordingTime = SystemClock.elapsedRealtime() - chronometer1.getBase();// 保存这次记录了的时间

            }
		});
				
		checkB_record.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
            @Override
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) { 
                // TODO Auto-generated method stub 
                appState.recTag = isChecked;
                if (isChecked){
                	checkB_record.setText("Recording");
                	checkB_record.setTextColor(0xffffffff);
                }else{
                	checkB_record.setText("Not Record");
                	checkB_record.setTextColor(0xffa6a6a6);
                }
            } 
        });
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != -1 && resultCode !=0) {
			appState.userID = data.getStringExtra("uid");
			appState.userName = data.getStringExtra("name");
			appState.note = data.getStringExtra("note");			

		}
	}
	
}



