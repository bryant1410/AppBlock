package com.cundong.appblock;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cundong.appblock.service.CoreService;
import com.cundong.appblock.util.BlockUtils;
import com.cundong.appblock.util.TopActivityUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_SETTING = 1;

    private Button startBlock, blockListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBlock = (Button) findViewById(R.id.start_block_btn);
        blockListBtn = (Button) findViewById(R.id.set_block_list);

        if (BlockUtils.isBlockServiceRunning(this, CoreService.class)) {
            startBlock.setText(R.string.stop_block);
        } else {
            startBlock.setText(R.string.start_block);
        }

        startBlock.setOnClickListener(this);
        blockListBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == startBlock) {
            if (BlockUtils.isBlockServiceRunning(this,
                    CoreService.class)) {
                Intent intent = new Intent();
                intent.setClass(this, CoreService.class);
                this.stopService(intent);
                startBlock.setText(R.string.start_block);
            } else {

                if (!TopActivityUtils.isStatAccessPermissionSet(this)) {
                    showDialog();
                } else {
                    Intent intent = new Intent();
                    intent.setClass(this, CoreService.class);
                    this.startService(intent);
                    startBlock.setText(R.string.stop_block);
                }
            }
        } else if (view == blockListBtn) {
            Intent intent = new Intent();
            intent.setClass(this, BlockListActivity.class);
            this.startActivity(intent);
        }
    }

    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //some rom has removed the newly introduced android.settings.USAGE_ACCESS_SETTINGS
                        try {
                            startActivityForResult(new Intent("android.settings.USAGE_ACCESS_SETTINGS"), REQUEST_SETTING);
                        } catch (Exception e) {
                            dialog.dismiss();
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SETTING) {
            if (TopActivityUtils.isStatAccessPermissionSet(this)) {
                //成功开启了权限

                Intent intent = new Intent();
                intent.setClass(this, CoreService.class);
                this.startService(intent);
                startBlock.setText(R.string.stop_block);

                Toast.makeText(this, R.string.permission_ok, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.permission_error, Toast.LENGTH_LONG).show();
            }
        }
    }
}