package com.bluetooth.le.soloman;


import com.bluetooth.le.soloman.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentHome extends Fragment {

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
		
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		findView(view);
		
		// 得到当前线程的Looper实例，由于当前线程是UI线程也可以通过Looper.getMainLooper()得到
		Looper looper = Looper.myLooper();
		// 此处甚至可以不需要设置Looper，因为 Handler默认就使用当前线程的Looper
		messageHandler = new MessageHandler(looper);

		new sportDataThread().start();
				
        return view;       
	}

	private Handler messageHandler;
	private void updateHandler(Object obj) {
		// 创建一个Message对象，并把得到的网络信息赋值给Message对象
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
				if ("sportdata".equals((String) msg.obj)) {
					tv_step.setText(String.valueOf(appState.step));
					tv_caroli.setText(String.valueOf(appState.caroli));
					tv_distance.setText(String.valueOf(appState.distance));
					tv_sporttime.setText(appState.sptime);
				}

			}
		}
	}
	
	
	// 更新数据进程----------------------------------------
	public class sportDataThread extends Thread {
		public sportDataThread() {
			appState.runThread = true;
		}

		@Override
		public void run() {
			while (appState.runThread && !this.isInterrupted()) {
				//System.out.println("sportDataThread run again");
				updateHandler("sportdata");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	//==================end thread
	
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
		tv_step = (TextView) view.findViewById(R.id.tv_step);
		tv_caroli = (TextView) view.findViewById(R.id.tv_caroli);
		tv_distance= (TextView) view.findViewById(R.id.tv_distance);
		tv_sporttime= (TextView) view.findViewById(R.id.tv_sporttime);
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



