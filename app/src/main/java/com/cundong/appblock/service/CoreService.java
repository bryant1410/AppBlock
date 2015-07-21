package com.cundong.appblock.service;

import java.util.ArrayList;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.cundong.appblock.WarningActivity;
import com.cundong.appblock.util.BlockUtils;
import com.cundong.appblock.util.Logger;

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

			Logger.getLogger().d( "block service is running..." );
			
			mBlockList = BlockUtils.getBlockList(getApplicationContext());
			
			ComponentName topActivity = mActivityManager.getRunningTasks(1)
					.get(0).topActivity;
			
			if ( mBlockList!=null && mBlockList.contains(topActivity.getPackageName()) ) {
				
				Logger.getLogger().i( "block packageName：" + topActivity.getPackageName() );
				Logger.getLogger().i( "block className：" + topActivity.getClassName() );
				
				Intent tancIntent = new Intent(getApplicationContext(), WarningActivity.class);
				tancIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(tancIntent);
			}
			
			mHandler.postDelayed(mRunnable,
					delayMillis);
		}
	};

	@Override
	public void onDestroy() {

		mHandler.removeCallbacks(mRunnable);
		super.onDestroy();
		
		Logger.getLogger().i( "onDestroy" );
	}
}