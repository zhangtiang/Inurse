package com.bluetooth.le.soloman;


import com.bluetooth.le.soloman.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentSetting extends Fragment {

	public GlobalVar appState;
	public TextView tv_step, tv_caroli, tv_distance, tv_sporttime;
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
		
		View view = inflater.inflate(R.layout.fragment_setting, container, false);
		findView(view);		
		
				
        return view;       
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

//		
//		checkB_record.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, 
//                    boolean isChecked) { 
//                // TODO Auto-generated method stub 
//                appState.recTag = isChecked;
//            } 
//        });
//        
//		checkB_sleep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, 
//                    boolean isChecked) { 
//                // TODO Auto-generated method stub 
//                if(isChecked){	//默认checked，是今日睡眠
//                	checkB_sleep.setText(R.string.today_sleep);
//                	showToday();
//                }else{
//                	checkB_sleep.setText(R.string.sleep_debt);
//                	showDept();
//                }
//            } 
//        });
//		
//		checkB_autoClock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, 
//                    boolean isChecked) { 
//                // TODO Auto-generated method stub 
//                if(isChecked){
//                	alarmOn();
//                }else{//默认unchecked，不设闹钟
//                	
//                }
//            } 
//        });
	}

	
	
	
	
}



