package com.example.raksa.usingmessengerforipc;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;

/**
 * Created by Raksa on 5/18/2017.
 */

public class MyService extends Service {


    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 43:
                break;

                default:
                    super.handleMessage(msg);
            }


        }
    }


    private MyHandler myHandler = new MyHandler();

    Messenger myMessenger = new Messenger(myHandler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myMessenger.getBinder() ;
    }

    public int addService(int value1 , int value2){
        return value1 + value2;
    }
}
