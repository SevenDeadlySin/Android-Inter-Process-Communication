package com.example.raksa.usingmessengerforipc;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Raksa on 5/18/2017.
 */

public class MyService extends Service {

    private MyLocalBinder myLocalBinder = new MyLocalBinder();

    public class MyLocalBinder extends Binder {


        //bind Service to Component
         public MyService getService(){
            return MyService.this;
        }

        //Service method in Binder
        public int add(int value1 , int value2){
           return addService(value1,value2);
        }

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myLocalBinder;
    }

    public int addService(int value1 , int value2){
        return value1 + value2;
    }
}
