package com.bluetooth.le.soloman;


import java.util.ArrayList;
import java.util.HashMap;

import com.bluetooth.le.soloman.R;
import com.bluetooth.le.soloman.FragmentUser.MyListAdapter;
import com.bluetooth.le.soloman.FragmentUser.ZuJian_user;

import android.content.Context;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentThemometerCloud extends Fragment {

	public GlobalVar appState;
	
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
		
		View view = inflater.inflate(R.layout.fragment_themometercloud, container, false);
	
		findView(view);
		updateUI(view);
		
//		view.setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				Log.i("info","FagmentThemometer onTouch");
//				return false;
//			}
//
//		});
		
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
		
	}

	//------------------------------------------------------------------
		public class ZuJian_themocloud {
			public LinearLayout list_themocloud;
			public TextView list_themocloud_xuanzhong;
			public TextView list_themocloud_uid;
			public TextView list_themocloud_username;
			public TextView list_themocloud_value;
			public TextView list_themocloud_time;
		}
		
		private ArrayList<HashMap<String, Object>> lst;
		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
		private MyListAdapter saImageItems;
		private ListView lv_cloudrecord;
		private HashMap<String, Object> map = new HashMap<String, Object>();

		private void updateUI(View view) {
			// TODO Auto-generated method stub
			lst = new ArrayList<HashMap<String, Object>>();
			saImageItems = new MyListAdapter(getActivity(), lst);// 没什么解释
			lv_cloudrecord = (ListView) view.findViewById(R.id.lv_cloudrecord);

			map.put("uid", "A10203");
			map.put("xinmin", "John Smith");
			map.put("value", "37.5℃");
			map.put("time", "2014/10/19");
			lst.add(map);
			
			map = new HashMap<String, Object>();
			map.put("uid", "A10203");
			map.put("xinmin", "John Smith");
			map.put("value", "37.5℃");
			map.put("time", "2014/10/19");
			lst.add(map);
			
			map = new HashMap<String, Object>();
			map.put("uid", "A10203");
			map.put("xinmin", "John Smith");
			map.put("value", "37.5℃");
			map.put("time", "2014/10/19");
			lst.add(map);

			// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
			// MyListAdapter saImageItems = new MyListAdapter(this, lst);// 没什么解释

			// 绑定数据
			BinderListData(saImageItems);
		}

		// 绑定数据
		public void BinderListData(MyListAdapter saImageItems) {
			// ListView listView_cart = (ListView)
			// findViewById(R.id.listView_chakan);
			// 添加并且显示
			lv_cloudrecord.setAdapter(saImageItems);
			saImageItems.notifyDataSetChanged();
		}
	    
		/*
		 * 以下是自定义的BaseAdapter类
		 */
		public class MyListAdapter extends BaseAdapter {
			private ArrayList<HashMap<String, Object>> data;
			private LayoutInflater layoutInflater;
			private Context context;

			public MyListAdapter(Context context,
					ArrayList<HashMap<String, Object>> data) {
				this.context = context;
				this.data = data;
				this.layoutInflater = LayoutInflater.from(context);
			}

			/**
			 * 获取列数
			 */
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return data.size();
			}

			/**
			 * 获取某一位置的数据
			 */
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return data.get(position);
			}

			/**
			 * 获取唯一标识
			 */
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			/**
			 * android绘制每一列的时候，都会调用这个方法
			 */
			ZuJian_themocloud zuJian = null;

			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub

				if (convertView == null) {
					zuJian = new ZuJian_themocloud();
					// 获取组件布局
					convertView = layoutInflater.inflate(R.layout.lv_themometercloud_body, null);
					
					zuJian.list_themocloud = (LinearLayout) convertView.findViewById(R.id.list_themocloud);
					
					zuJian.list_themocloud_xuanzhong = (TextView) convertView.findViewById(R.id.list_themocloud_xuanzhong);
					zuJian.list_themocloud_uid = (TextView) convertView.findViewById(R.id.list_themocloud_uid);
					zuJian.list_themocloud_username = (TextView) convertView.findViewById(R.id.list_themocloud_username);
					zuJian.list_themocloud_value = (TextView) convertView.findViewById(R.id.list_themocloud_value);
					zuJian.list_themocloud_time = (TextView) convertView.findViewById(R.id.list_themocloud_time);

					// 这里要注意，是使用的tag来存储数据的。
					convertView.setTag(zuJian);
				} else {
					zuJian = (ZuJian_themocloud) convertView.getTag();

				}

				// 绑定数据、以及事件触发
				zuJian.list_themocloud_uid.setText((String) data.get(position).get("uid"));
				zuJian.list_themocloud_username.setText((String) data.get(position).get("xinmin"));
				zuJian.list_themocloud_value.setText((String) data.get(position).get("value"));
				zuJian.list_themocloud_time.setText((String) data.get(position).get("time"));

				return convertView;
			}
		}
	
}



