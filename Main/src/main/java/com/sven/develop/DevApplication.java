package com.sven.develop;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.sven.develop.Util.Constant;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Sven.Zhan on 2017/1/6.
 */

public class DevApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initBuglyReport();
    }

    private void initBuglyReport(){
        // 获取当前包名
        String packageName = getPackageName();
        // 获取当前进程名
        int pid = android.os.Process.myPid();
        String processName = getProcessName(pid);
        Log.e("DevApplication","initBuglyReport packageName = "+packageName+"　pid = "+pid+" processName = "+processName);
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(getApplicationContext(), "96b20ad6b6", true);
    }

    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
