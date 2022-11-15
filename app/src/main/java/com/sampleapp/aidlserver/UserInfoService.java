package com.sampleapp.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class UserInfoService extends Service {
    public UserInfoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private final IAidlUserInfoInterface.Stub binder = new IAidlUserInfoInterface.Stub() {
        @Override
        public int getUserAge() throws RemoteException {
            return 24;
        }

        @Override
        public String getUserName() throws RemoteException {
            return "李蕾";
        }
    };
}