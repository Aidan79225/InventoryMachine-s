package com.aidan.secondinventoryworkplatform;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import com.aidan.secondinventoryworkplatform.FragmentManager.FragmentManagerActivity;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_PHONE_STATE;
import static com.aidan.secondinventoryworkplatform.KeyConstants.data;
import static com.aidan.secondinventoryworkplatform.KeyConstants.isLogin;

/**
 * Created by Aidan on 2017/3/30.
 */

public class StartActivity extends Activity {
    private static final int ACTIVITYJUMP_DELAY = 300;
    private static final int REQUEST_PHONE_STATE = 0x1;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    public void onResume() {
        super.onResume();
        new ActivityJumpTimer().sendEmptyMessageDelayed(ActivityJumpTimer.JUMP_TO_HOMEACTIVITY, ACTIVITYJUMP_DELAY);

    }

    private void start() {
        int permission = ActivityCompat.checkSelfPermission(this,
                READ_PHONE_STATE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{READ_PHONE_STATE},
                    REQUEST_PHONE_STATE
            );
            return;
        }

        permission = ActivityCompat.checkSelfPermission(this,
                CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{CAMERA},
                    REQUEST_PHONE_STATE
            );
            return;
        }
        //已有權限
        action();

    }

    private void action() {
        if (checkLogin()) {
            gotoFragmentManagerActivity();
        } else if (checkIMEI()) {
            showLoginDialog();
        }
    }

    private boolean checkIMEI() {
        TelephonyManager mTelManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return true;
        }
        String mIMEI = mTelManager.getDeviceId();
        for(String IMEI : KeyConstants.IMEIS){
            if(mIMEI.equals(IMEI))return true;
        }
        Toast.makeText(this,"IMEI不符合資格，請洽管理員",Toast.LENGTH_SHORT).show();
        return true;
    }
    private boolean checkLogin(){
        settings = getSharedPreferences(data,0);
        return settings.getBoolean(isLogin,false);
    }

    private void showLoginDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("請輸入認證碼");
        final EditText editText = new EditText(this);
        editText.setGravity(Gravity.CENTER);
        editText.setHint("請輸入認證碼");
        builder.setView(editText);
        builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(KeyConstants.key.equals(editText.getText().toString())){
                    settings = getSharedPreferences(data,0);
                    settings.edit().putBoolean(isLogin,true).apply();
                    gotoFragmentManagerActivity();
                }else{
                    showCancelDialog();
                }
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

        private ActivityJumpTimer() {

        }

        public void handleMessage(Message msg) {
            if (msg.what == JUMP_TO_HOMEACTIVITY) {
                //startVersionCheck(activity);
                start();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //取得權限，進行檔案存取
                    start();
                }else{
                    showCancelDialog();
                }
                return;
        }
    }

}
