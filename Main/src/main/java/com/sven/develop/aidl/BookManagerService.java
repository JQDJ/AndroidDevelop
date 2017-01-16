package com.sven.develop.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.util.Arrays;

public class BookManagerService extends Service {

    String TAG = BookManagerService.class.getSimpleName();

    public BookManagerService() {

    }

    private IBookManager.Stub mBinder = new IBookManager.Stub() {

        @Override
        public int addBook(Book book, IEventListener listener) throws RemoteException {
            Log.d(TAG,"addBook thread = "+Thread.currentThread().getName()+" listener = "+listener);
            listener.onBookAdded(book);
            return 2;
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int pid = getCallingPid();
            int uid = getCallingUid();
            Log.w(TAG, "onTransact pid = " + pid + " uid = " + uid+" thread = "+Thread.currentThread().getName());
            String[] packages = getPackageManager().getPackagesForUid(uid);
            Log.w(TAG, "onTransact packages = " + Arrays.toString(packages));
            return super.onTransact(code, data, reply, flags);
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        int permission = checkCallingOrSelfPermission("com.sven.develop.BOOK_MANAGER_SERVERCE");
        Log.e(TAG, "onBind permission = " + permission+" thread = "+Thread.currentThread().getName());
        if (permission == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        Log.e(TAG, "onBind mBinder = " + mBinder);
        return mBinder;
    }
}
