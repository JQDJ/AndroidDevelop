package com.sven.develop;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.sven.develop.rxjava.RxJavaTest;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText(exceptionFromJNI());
            }
        });
    }


    public native String stringFromJNI();

    public native String exceptionFromJNI();

    static {
        System.loadLibrary("native-lib");
    }
}
