package com.bluetooth.le.soloman;

import java.util.ArrayList;

import com.bluetooth.le.soloman.FragmentThemometer;
import com.bluetooth.le.soloman.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class FirstActivity extends FragmentActivity {

	public GlobalVar appState;
	
	public android.support.v4.app.FragmentTransaction ft;
	public FragmentHome fragmentHome;
	public FragmentSetting fragmentSetting;
	public FragmentHelp fragmentHelp;
	public FragmentUser fragmentUser;

	public MediaRecorder recorder;	
	public ViewPager viewPager;	
	public ImageView iv_home, iv_setting, iv_help, iv_user, iv_sound, iv_repair;	//首页图片  设置图片
	public Button btn_wendu;	//温度计

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		appState = (GlobalVar) getApplicationContext(); // 获得全局变量
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 设置成竖屏		
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 	//去掉title
		setContentView(R.layout.first_activity);

		appState.sdcard = Environment.getExternalStorageDirectory().toString();
		
		findView();
		setOnClickListener();
		initViewPager();
		
		
		android.support.v4.app.FragmentManager fm =  getSupportFragmentManager();
		ft = fm.beginTransaction();
		
		fragmentHome = (FragmentHome) fm.findFragmentById(R.layout.fragment_home);
		fragmentSetting = (FragmentSetting) fm.findFragmentById(R.layout.fragment_setting);
		fragmentHelp = (FragmentHelp) fm.findFragmentById(R.layout.fragment_help);
		fragmentUser = (FragmentUser) fm.findFragmentById(R.layout.fragment_user);
		
		ft.add(R.id.viewPager,new FragmentThemometer(), "home");	
		ft.add(R.id.viewPager,new FragmentThemometer(), "setting");
		ft.add(R.id.viewPager,new FragmentThemometer(), "help");
		ft.add(R.id.viewPager,new FragmentThemometer(), "user");
		
		//ft.replace(R.id.viewPager, fragmentSleep);
		ft.commit();	

	

	}

	
	private void setOnClickListener() {
		// TODO Auto-generated method stub
		iv_home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("info", "iv_home onClicked");
				viewPager.setCurrentItem(0);
			}
		});
		
		iv_setting.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("info", "iv_setting onClicked");
				viewPager.setCurrentItem(1);
			}
		});
		
		iv_help.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("info", "iv_help onClicked");
				viewPager.setCurrentItem(2);
			}
		});
		
		iv_user.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("info", "iv_user onClicked");
				viewPager.setCurrentItem(3);
			}
		});
		
		iv_repair.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("info", "iv_repair onClicked");
				
			}
		});
		
		iv_sound.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("info", "iv_sound onClicked");

			}
		});
		
	}
	
	public void btn_wendu_onclick(View view){
		Log.i("info","点击温度计按钮");
//		Intent it = new Intent(FirstActivity.this, CeliangActivity.class);
		Intent it = new Intent(FirstActivity.this, DeviceScanActivity.class);
		startActivity(it);
	}


//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();

		appState.firstActivityRunning = false;
		appState.runThread = false;
	}
	
	// 检测按键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			System.out.println("返回按钮");
			setResult(RESULT_OK);
			finish();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
			
	public void findView(){
		viewPager = (ViewPager) findViewById(R.id.viewPager);		
	
		iv_home = (ImageView) findViewById(R.id.iv_home);		
		iv_setting = (ImageView) findViewById(R.id.iv_setting);
		iv_help = (ImageView) findViewById(R.id.iv_help);
		iv_user = (ImageView) findViewById(R.id.iv_user);
		iv_repair = (ImageView) findViewById(R.id.iv_repair);
		iv_sound = (ImageView) findViewById(R.id.iv_sound);
		
		btn_wendu =  (Button) findViewById(R.id.btn_wendu);
	}

	
	private ArrayList<Fragment> fragmentArryList;  
	public void initViewPager(){
		//viewPager = (ViewPager)findViewById(R.id.viewpager);  
		fragmentArryList = new ArrayList<Fragment>();  
 
		fragmentHome = new FragmentHome();
		fragmentSetting = new FragmentSetting();
		fragmentHelp = new FragmentHelp();
		fragmentUser = new FragmentUser();
        
		fragmentArryList.add(fragmentHome);  
		fragmentArryList.add(fragmentSetting);  
		fragmentArryList.add(fragmentHelp);
		fragmentArryList.add(fragmentUser);
          
        //给ViewPager设置适配器  
		viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentArryList));  
		viewPager.setCurrentItem(0);//设置当前显示标签页为第一页  
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器  		
	}
	
	
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 0:
				iv_home.setBackgroundResource(R.drawable.home1);
				iv_setting.setBackgroundResource(R.drawable.setting);
				iv_help.setBackgroundResource(R.drawable.help);
				iv_user.setBackgroundResource(R.drawable.user);
				break;
			case 1:
				iv_setting.setBackgroundResource(R.drawable.setting1);
				iv_home.setBackgroundResource(R.drawable.home);
				iv_help.setBackgroundResource(R.drawable.help);
				iv_user.setBackgroundResource(R.drawable.user);
				break;
			case 2:
				iv_home.setBackgroundResource(R.drawable.home);
				iv_setting.setBackgroundResource(R.drawable.setting);
				iv_help.setBackgroundResource(R.drawable.help1);
				iv_user.setBackgroundResource(R.drawable.user);
				break;
			case 3:
				iv_home.setBackgroundResource(R.drawable.home);
				iv_setting.setBackgroundResource(R.drawable.setting);
				iv_help.setBackgroundResource(R.drawable.help);
				iv_user.setBackgroundResource(R.drawable.user1);
				break;
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		public int currentPageIndex;
		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
//			switch (arg0) {
//			case 0:
//				iv_home.setBackgroundResource(R.drawable.home1);
//				iv_setting.setBackgroundResource(R.drawable.setting);
//				break;
//			case 1:
//				iv_setting.setBackgroundResource(R.drawable.setting1);
//				iv_home.setBackgroundResource(R.drawable.home);
//				break;
//
//			}
//			
//			fragmentArryList.get(currentPageIndex).onPause(); // 调用切换前Fargment的onPause()
//			if (fragmentArryList.get(arg0).isAdded()) {
//				fragmentArryList.get(arg0).onResume(); // 调用切换后Fargment的onResume()
//			}
//			currentPageIndex = arg0;
		}
		
		
		
	}
	
	public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
		ArrayList<Fragment> list;

		public MyFragmentPagerAdapter(FragmentManager fm,
				ArrayList<Fragment> list) {
			super(fm);
			this.list = list;

		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			return list.get(arg0);
		}			
	} 

}
