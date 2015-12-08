package com.cundong.appblock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cundong.appblock.service.CoreService;
import com.cundong.appblock.util.BlockUtils;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button startBlock, blockListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBlock = (Button) findViewById(R.id.start_block_btn);
        blockListBtn = (Button) findViewById(R.id.set_block_list);

        if (BlockUtils.isBlockServiceRunning(this, CoreService.class)) {
            startBlock.setText("停止屏蔽应用");
        } else {
            startBlock.setText("开始屏蔽应用");
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
                startBlock.setText("开始屏蔽应用");
            } else {
                Intent intent = new Intent();
                intent.setClass(this, CoreService.class);
                this.startService(intent);
                startBlock.setText("停止屏蔽应用");
            }
        } else if (view == blockListBtn) {
            Intent intent = new Intent();
            intent.setClass(this, BlockListActivity.class);
            this.startActivity(intent);
        }
    }
}