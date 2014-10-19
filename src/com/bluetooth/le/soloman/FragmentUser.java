package com.bluetooth.le.soloman;


import java.util.ArrayList;
import java.util.HashMap;

import com.bluetooth.le.soloman.R;
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
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentUser extends Fragment {

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
		
		View view = inflater.inflate(R.layout.fragment_user, container, false);
	
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
	public class ZuJian_user {
		public LinearLayout list_user;
		public CheckBox list_user_xuanzhong;
		public TextView list_uid;
		public TextView list_username;
		public TextView list_tel;
		public TextView list_mail;
		public TextView list_note;	
	}
	
	private ArrayList<HashMap<String, Object>> lst;
	// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
	private MyListAdapter saImageItems;
	private ListView listView_user;
	private HashMap<String, Object> map = new HashMap<String, Object>();

	private void updateUI(View view) {
		// TODO Auto-generated method stub
		lst = new ArrayList<HashMap<String, Object>>();
		saImageItems = new MyListAdapter(getActivity(), lst);// 没什么解释
		listView_user = (ListView) view.findViewById(R.id.lv_user);

		map.put("uid", "A10203");
		map.put("xinmin", "John Smith");
		map.put("tel", "13800009999");
		map.put("mail", "dfsidfudf@163.com");
		map.put("note", "There is nothing to say!");
		lst.add(map);
		
		map = new HashMap<String, Object>();
		map.put("uid", "A10204");
		map.put("xinmin", "Ken Block");
		map.put("tel", "18911112222");
		map.put("mail", "dfssdfsddf@163.com");
		map.put("note", "I'm healthy!");
		lst.add(map);
		
		map = new HashMap<String, Object>();
		map.put("uid", "A10205");
		map.put("xinmin", "James Bond");
		map.put("tel", "18911112222");
		map.put("mail", "dfssdfsddf@163.com");
		map.put("note", "I'm healthy!");
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
		listView_user.setAdapter(saImageItems);
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
		ZuJian_user zuJian = null;

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			if (convertView == null) {
				zuJian = new ZuJian_user();
				// 获取组件布局
				convertView = layoutInflater.inflate(R.layout.lv_user_body, null);
				zuJian.list_user = (LinearLayout) convertView.findViewById(R.id.list_user);
				zuJian.list_user_xuanzhong = (CheckBox) convertView.findViewById(R.id.list_user_xuanzhong);
				zuJian.list_uid = (TextView) convertView.findViewById(R.id.list_uid);
				zuJian.list_username = (TextView) convertView.findViewById(R.id.list_username);
				zuJian.list_tel = (TextView) convertView.findViewById(R.id.list_tel);
				zuJian.list_mail = (TextView) convertView.findViewById(R.id.list_mail);
				zuJian.list_note = (TextView) convertView.findViewById(R.id.list_note);

				// 这里要注意，是使用的tag来存储数据的。
				convertView.setTag(zuJian);
			} else {
				zuJian = (ZuJian_user) convertView.getTag();

			}

			// 绑定数据、以及事件触发
			zuJian.list_uid.setText((String) data.get(position).get("uid"));
			zuJian.list_username.setText((String) data.get(position).get("xinmin"));
			zuJian.list_tel.setText((String) data.get(position).get("tel"));
			zuJian.list_mail.setText((String) data.get(position).get("mail"));
			zuJian.list_note.setText((String) data.get(position).get("note"));

			return convertView;
		}
	}
}


