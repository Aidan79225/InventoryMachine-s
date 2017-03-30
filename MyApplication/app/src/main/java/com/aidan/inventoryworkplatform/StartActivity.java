package com.aidan.inventoryworkplatform;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;

import com.aidan.inventoryworkplatform.FragmentManager.FragmentManagerActivity;

/**
 * Created by Aidan on 2017/3/30.
 */

public class StartActivity extends AppCompatActivity {
    private ActivityJumpTimer timer;
    private static final int ACTIVITYJUMP_DELAY = 300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        timer = new ActivityJumpTimer(this);
        timer.sendEmptyMessageDelayed(ActivityJumpTimer.JUMP_TO_HOMEACTIVITY, ACTIVITYJUMP_DELAY);

    }
    @Override
    public void onResume(){
        super.onResume();
    }
    private void start(){
        if(checkIMEI()){
            gotoFragmentManagerActivity();
        }else{
            showCancelDialog();
        }
    }
    private boolean checkIMEI(){
        TelephonyManager mTelManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String mIMEI = mTelManager.getDeviceId();
        for(String IMEI : KeyConstants.IMEIS){
            if(mIMEI.equals(IMEI))return true;
        }
        return false;
    }
    private void showCancelDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("無法開啟");
        builder.setMessage("程式鎖定，請洽詢管理人員");
        builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }

    private void gotoFragmentManagerActivity(){
        Intent intent = new Intent();
        intent.setClass(this, FragmentManagerActivity.class);
        startActivity(intent);
        finish();
    }
    private class ActivityJumpTimer extends Handler {
        private static final int JUMP_TO_HOMEACTIVITY = 0x0000;
        private StartActivity activity;

        private ActivityJumpTimer(StartActivity activity) {
            this.activity = activity;
        }

        public void handleMessage(Message msg) {
            if (msg.what == JUMP_TO_HOMEACTIVITY) {
                //startVersionCheck(activity);
                start();
            }
        }
    }

}
