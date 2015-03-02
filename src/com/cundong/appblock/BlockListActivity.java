package com.cundong.appblock;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import com.cundong.appblock.adapter.AppListAdapter;
import com.cundong.appblock.util.BlockUtils;

public class BlockListActivity extends ListActivity {

	private AppListAdapter mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_applist);

//		Intent intent = new Intent(Intent.ACTION_MAIN,null);  
//		intent.addCategory(Intent.CATEGORY_LAUNCHER);  
//		
//		List< ResolveInfo> appList1 = getPackageManager().queryIntentActivities(intent,0);  
//		ActivityInfo d = appList1.get(0).activityInfo;
//		String a = d.packageName;
//		String n = d.loadLabel(getPackageManager()).toString();
		
		List<PackageInfo> appList = getPackageManager().getInstalledPackages(0);
		List<PackageInfo> installedList = new ArrayList<PackageInfo>();

		for (PackageInfo packageInfo : appList) {
			
			if ( !isSystemPackage(packageInfo) && !getApplicationInfo().packageName.equals(packageInfo.packageName)) {
				installedList.add(packageInfo);
			}
		}

		mAdapter = new AppListAdapter(this, installedList, BlockUtils.getBlockList(getApplicationContext()));
		setListAdapter(mAdapter);
	}

	private boolean isSystemPackage(PackageInfo packageInfo) {
		return ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
				: false;
	}
}