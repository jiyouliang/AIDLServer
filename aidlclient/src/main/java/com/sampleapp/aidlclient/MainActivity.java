package com.sampleapp.aidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sampleapp.aidlserver.IAidlUserInfoInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    IAidlUserInfoInterface iAidlUserInfoInterface;

    private TextView tvResult;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected");
            iAidlUserInfoInterface = IAidlUserInfoInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        bindMyService();
        initViews();
    }

    private void initViews() {
        findViewById(R.id.btn_get_user_info).setOnClickListener(this);
        tvResult = findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {
        getUserInfo();
    }

    private void getUserInfo() {
        Log.i(TAG, "getUserInfo");
        try {
            StringBuilder stringBuilder = new StringBuilder();
            int userAge = iAidlUserInfoInterface.getUserAge();
            String userName = iAidlUserInfoInterface.getUserName();
            stringBuilder.append("userName:").append(userName).append(", userAge:").append(userAge);
            tvResult.setText(stringBuilder.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void bindMyService() {
        Log.i(TAG, "bindMyService");
        Intent intent = new Intent("UserInfoService");
        intent.setPackage("com.sampleapp.aidlserver");
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }
}