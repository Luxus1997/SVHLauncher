package com.haoyue.svhlauncher.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoyue.svhlauncher.R;
import com.haoyue.svhlauncher.SVHApplication;
import com.haoyue.svhlauncher.activity.base.IActivity;
import com.haoyue.svhlauncher.eventtask.CountDownTask;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends IActivity implements View.OnClickListener {

    //退出
    TextView exit_tstv;
    //身高体重
    LinearLayout ll_stature;
    //血压心率
    LinearLayout ll_blood_pressure;
    //体温
    LinearLayout ll_temperature;
    //全部测序
    LinearLayout ll_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FindView();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        CountDown();
    }

    private void FindView() {
        exit_tstv = findViewById(R.id.exit_tstv);
        ll_stature = findViewById(R.id.ll_stature);
        ll_blood_pressure = findViewById(R.id.ll_blood_pressure);
        ll_temperature = findViewById(R.id.ll_temperature);
        ll_all = findViewById(R.id.ll_all);

        exit_tstv.setOnClickListener(this);
        ll_stature.setOnClickListener(this);
        ll_blood_pressure.setOnClickListener(this);
        ll_temperature.setOnClickListener(this);
        ll_all.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SVHApplication.getInstance().SvhClick();
        switch (v.getId()) {
            case R.id.exit_tstv:
                startActivity(new Intent(MainActivity.this, ADActivity.class));
                finish();
                break;
            case R.id.ll_stature:
                Intent stature_intent = new Intent(MainActivity.this, StatureActivity.class);
                Bundle stature_bundle = new Bundle();
                stature_bundle.putBoolean("isAll", false);
                stature_bundle.putBoolean("isUser", false);
                stature_intent.putExtras(stature_bundle);
                startActivity(stature_intent);
                finish();
                break;
            case R.id.ll_blood_pressure:
                Intent pressure_intent = new Intent(MainActivity.this, PressureActivity.class);
                Bundle pressure_bundle = new Bundle();
                pressure_bundle.putBoolean("isAll", false);
                pressure_bundle.putBoolean("isUser", false);
                pressure_intent.putExtras(pressure_bundle);
                startActivity(pressure_intent);
                finish();
                break;
            case R.id.ll_temperature:
                Intent temp_intent = new Intent(MainActivity.this, TempActivity.class);
                Bundle temp_bundle = new Bundle();
                temp_bundle.putBoolean("isAll", false);
                temp_bundle.putBoolean("isUser", false);
                temp_intent.putExtras(temp_bundle);
                startActivity(temp_intent);
                finish();
                break;
            case R.id.ll_all:
                Intent all_intent = new Intent(MainActivity.this, StatureActivity.class);
                Bundle all_bundle = new Bundle();
                all_bundle.putBoolean("isAll", true);
                all_bundle.putBoolean("isUser", false);
                all_intent.putExtras(all_bundle);
                startActivity(all_intent);
                finish();
                break;
        }
    }

    private CountTimer countTimerView;

    public class CountTimer extends CountDownTimer {

        public CountTimer(long millisInFuture, long countDownInterval, Context context) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            SVHApplication.getInstance().SvhClick();
            startActivity(new Intent(MainActivity.this, ADActivity.class));
            finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int time = (int) (millisUntilFinished / 1000);
            EventBus.getDefault().postSticky(new CountDownTask(time));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCountDownTask(CountDownTask count) {
        exit_tstv.setText(" 退出[" + count.second + "s] ");
    }

    private void CountDown() {
        countTimerView = new CountTimer(90 * 1000, 1000, MainActivity.this);
    }

    private void timeStart() {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                countTimerView.start();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        countTimerView.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        timeStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}