package com.sven.develop.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sven.develop.R;

public class AIDLActivity extends Activity {

    String TAG = "BookActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent("com.sven.develop.aidl.BookManagerService");
//                intent.setPackage("com.sven.develop");
                Intent intent = new Intent(AIDLActivity.this,BookManagerService.class);
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
            }
        });
    }

    IBookManager mBookManager;

    ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected ComponentName = " + name + " service = " + service + " thread = " + Thread.currentThread().getName());
            mBookManager = IBookManager.Stub.asInterface(service);
            Log.e(TAG, "onServiceConnected manager = " + mBookManager);
            try {
                mBookManager.asBinder().linkToDeath(mRecipient, 0);
                mBookManager.addBook(new Book("Android", 4.5f), listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected ComponentName = " + name);
        }
    };

    IEventListener listener = new IEventListener.Stub() {
        @Override
        public void onBookAdded(Book book) throws RemoteException {
            Log.e(TAG, "onBookAdded bookname = " + book.name + " thread = " + Thread.currentThread().getName());
        }
    };

    IBinder.DeathRecipient mRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e(TAG, "binderDied mBookManager = " + mBookManager + " thread = " + Thread.currentThread().getName());
            if (mBookManager != null) {
                mBookManager.asBinder().unlinkToDeath(mRecipient, 0);
            }
            Intent intent = new Intent(AIDLActivity.this,BookManagerService.class);
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    };
}
