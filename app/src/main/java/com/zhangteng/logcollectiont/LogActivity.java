package com.zhangteng.logcollectiont;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zhangteng.catcher.AndroidCrash;
import com.zhangteng.catcher.reporter.mailreporter.CrashEmailReporter;

import java.util.List;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        initEmailReporter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> list = null;
                list.get(0);
            }
        }, 1000);
    }

    /**
     * 使用EMAIL发送日志
     */
    @SuppressWarnings("unused")
    private void initEmailReporter() {
        CrashEmailReporter reporter = new CrashEmailReporter(this) {
            /**
             * 重写此方法，可以弹出自定义的崩溃提示对话框，而不使用系统的崩溃处理。
             * @param thread
             * @param ex
             */
            @Override
            public void closeApp(Thread thread, Throwable ex) {
                final Activity activity = LogActivity.this;
                Toast.makeText(activity, "发生异常，正在退出", Toast.LENGTH_SHORT).show();
            }
        };
        reporter.setReceiver("763263311@qq.com");
        reporter.setSender("763263311@qq.com");
        reporter.setSendPassword(BuildConfig.qqMailKey);
        reporter.setSMTPHost("smtp.qq.com");
        reporter.setPort("465");
//        reporter.setPort("587");
        AndroidCrash.getInstance().setCrashReporter(reporter).init(this);

    }
}
