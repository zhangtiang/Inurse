package com.bluetooth.le.soloman;


import com.bluetooth.le.soloman.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentThemometer extends Fragment {

	public GlobalVar appState;
	public Button btn_getTemp, btn_swichcewenmode, btn_swichcewenunit;
	public TextView tv_user1_cewen, tv_user2_cewen, tv_device_cewen, tv_tempre, tv_cewenwendu, tv_cewenunit, tv_cewennum;
	public byte mode, unit;
	int ti, tj;
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
		
		View view = inflater.inflate(R.layout.fragment_themometer, container, false);
		
		ti = 0;
		tj = 0;
		findView(view);
		
		view.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i("info","FagmentThemometer onTouch");
				cewen();
				return false;
			}

		});
		
		// 得到当前线程的Looper实例，由于当前线程是UI线程也可以通过Looper.getMainLooper()得到
		Looper looper = Looper.myLooper();
		// 此处甚至可以不需要设置Looper，因为 Handler默认就使用当前线程的Looper
		messageHandler = new MessageHandler(looper);

				
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

				}

			}
		}
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
		btn_getTemp = (Button) view.findViewById(R.id.btn_getTemp);
		btn_swichcewenmode  = (Button) view.findViewById(R.id.btn_swichcewenmode);
		btn_swichcewenunit = (Button) view.findViewById(R.id.btn_swichcewenunit);
		tv_tempre = (TextView) view.findViewById(R.id.tv_tempre);
		tv_user1_cewen = (TextView) view.findViewById(R.id.tv_user1_cewen);
		tv_user2_cewen = (TextView) view.findViewById(R.id.tv_user2_cewen);
		tv_device_cewen = (TextView) view.findViewById(R.id.tv_device_cewen);
		tv_cewenwendu = (TextView) view.findViewById(R.id.tv_cewenwendu);
		tv_cewenunit = (TextView) view.findViewById(R.id.tv_cewenunit);
		tv_cewennum = (TextView) view.findViewById(R.id.tv_cewennum);
		
		tv_user1_cewen.setText("ID:A10234         User Name: Ken Block");
		tv_user2_cewen.setText("Note:This is my first patient!");
		tv_device_cewen.setText("Device ID:" + appState.deviceAddress);

		btn_getTemp.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	cewen();
            }  
        });    
		
		//切换模式0body 1surface 2room
		btn_swichcewenmode.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	ti ++;
            	appState.setMode(appState.gattCharacteristic_send, (byte) (ti % 3 & 0xff), (byte) (tj % 2 & 0xff) );
            }
		});
		
		btn_swichcewenunit.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	tj ++;
            	if (tj %2 ==0){
            		tv_cewenunit.setText("℃");
            	}else if (tj % 2 ==1){
            		tv_cewenunit.setText("℉");
            	}
            	appState.setMode(appState.gattCharacteristic_send, (byte) (ti % 3 & 0xff), (byte) (tj % 2 & 0xff) );
            	tv_cewenwendu.setText("- -");
            }
		});
		
		
						
	}

	public void cewen(){
		Log.i("info", "点击侧温度");
        appState.getTemp(appState.gattCharacteristic_send);
        Log.i("info", "已调用测温度方法");
        messageHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
            	tv_tempre.setText("表温：" + String.valueOf(appState.surface) +
            			"，体温：" + String.valueOf(appState.body) +
            			"，环温："+ String.valueOf(appState.room) +
            			"，模式："+ appState.mode +
            			"，单位："+ appState.unit );
            	
						if ("body".equals(appState.mode)) {
							tv_cewenwendu.setText(String.valueOf(appState.body));
							ti = 0;
						} else if ("surface".equals(appState.mode)) {
							tv_cewenwendu.setText(String.valueOf(appState.surface));
							ti = 1;
						} else if ("room".equals(appState.mode)) {
							tv_cewenwendu.setText(String.valueOf(appState.room));
							ti = 2;
						}

						if (appState.mode != null) {
							if ("℃".equals(appState.unit)) {
								tj = 0;
							}else if ("℉".equals(appState.unit)) {
								tj = 1;
							}
							tv_cewenunit.setText(appState.unit);
							tv_cewennum.setText("Record Total:xx\nbody:xx    surface:xx    room:xx");
						}
						
						
            }
        }, 2000);
	}
	
}



