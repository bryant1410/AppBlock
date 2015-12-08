package com.cundong.appblock.service;

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
import com.cundong.appblock.util.TopActivityUtils;

import java.util.ArrayList;

/**
 * Created by cundong on 2015/1/24.
 *
 * 后台默默运行着的Service
 */
public class CoreService extends Service {

    private static final int delayMillis = 1000;

    private Context mContext;
    private ActivityManager mActivityManager;

    private Handler mHandler;

    private ArrayList<String> mBlockList = null;
    
    private Runnable mRunnable = new Runnable() {

        public void run() {

            Logger.getLogger().d("block service is running...");

            mBlockList = BlockUtils.getBlockList(getApplicationContext());

            String packageName = null;

            ComponentName componentName = TopActivityUtils.getTopActivity(mContext, mActivityManager);

            if (componentName != null) {
                packageName = componentName.getPackageName();
                Logger.getLogger().i("packageName:" + packageName);
            } else {
                Logger.getLogger().e("getTopActivity Error!");
            }

            if (mBlockList != null && mBlockList.contains(packageName)) {

                Intent intent = new Intent(getApplicationContext(), WarningActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            mHandler.postDelayed(mRunnable, delayMillis);
        }
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
        mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, delayMillis);

        Logger.getLogger().i("onCreate");
    }

    @Override
    public void onDestroy() {

        mHandler.removeCallbacks(mRunnable);
        super.onDestroy();

        Logger.getLogger().i("onDestroy");
    }
}