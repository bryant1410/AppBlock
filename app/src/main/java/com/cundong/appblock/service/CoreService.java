package com.cundong.appblock.service;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import com.cundong.appblock.WarningActivity;
import com.cundong.appblock.util.BlockUtils;
import com.cundong.appblock.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class CoreService extends Service {

	private static final int delayMillis = 1000;

	private ActivityManager mActivityManager;
	
	private Handler mHandler;
	
	private ArrayList<String> mBlockList = null;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
	    mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    
		mHandler = new Handler();
		mHandler.postDelayed(mRunnable, delayMillis);
		
		Logger.getLogger().i( "onCreate" );
	}
	
	private Runnable mRunnable = new Runnable() {
		
		public void run() {

			Logger.getLogger().d("block service is running...");
			
			mBlockList = BlockUtils.getBlockList(getApplicationContext());

			String packageName;

			if (Build.VERSION.SDK_INT >= 21) {
				List<ActivityManager.AppTask> appTasks = getAppTasks();
				ActivityManager.AppTask appTask = appTasks.get(0);

				packageName = appTask.getTaskInfo().baseIntent.getComponent().getPackageName();
			} else {

				ComponentName topActivity = mActivityManager.getRunningTasks(1).get(0).topActivity;
				packageName = topActivity.getPackageName();
			}

			if ( mBlockList!=null && mBlockList.contains(packageName) ) {
				Intent intent = new Intent(getApplicationContext(), WarningActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
			
			mHandler.postDelayed(mRunnable, delayMillis);
		}
	};

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private List<ActivityManager.AppTask> getAppTasks()  {
		ActivityManager mgr = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		List<ActivityManager.AppTask> tasks = mgr.getAppTasks();

		return tasks;
	}

	@Override
	public void onDestroy() {

		mHandler.removeCallbacks(mRunnable);
		super.onDestroy();
		
		Logger.getLogger().i("onDestroy");
	}
}