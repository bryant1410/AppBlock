package com.cundong.appblock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class WarningActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_warning);

	}

	@Override
	public void onBackPressed() {

		Intent MyIntent = new Intent(Intent.ACTION_MAIN);
		MyIntent.addCategory(Intent.CATEGORY_HOME);
		startActivity(MyIntent);

		finish();

		return;
	}
}