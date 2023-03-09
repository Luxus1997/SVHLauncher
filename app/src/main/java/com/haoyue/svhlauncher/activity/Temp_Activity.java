/*
package com.haoyue.svhlauncher.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.haoyue.svhlauncher.R;
import com.haoyue.svhlauncher.activity.base.IActivity;
import com.haoyue.svhlauncher.daobean.Physicals;
import com.haoyue.svhlauncher.eventtask.CountDownTask;
import com.haoyue.svhlauncher.eventtask.TempTask;
import com.haoyue.svhlauncher.operation.PhysicalsOperation;
import com.haoyue.svhlauncher.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

public class Temp_Activity extends IActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    //退出
    TextView exit_tstv;

    //Loading页
    ViewStub stub_loading;
    View loading_view;
    //测量结果
    ViewStub stub_msg;
    View msg_view;

    //体温
    TextView value1_tstv;
    //体温单位
    TextView value1_dw_tstv;
    //体温衡量
    TextView tv_temp;

    //重新测量
    Button bt_remeasure;
    //开启测量
    boolean isMeasure = false;

    //是否全部测量
    boolean isAll = false;
    boolean isUser = false;
    //跳过
    Button bt_skip;
    //下一项
    Button bt_next;
    //-
    double temp = 0;
    // TTS对象
    private TextToSpeech mTextToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        Bundle data = getIntent().getExtras();
        isAll = data.getBoolean("isAll");
        isUser = data.getBoolean("isUser");

        FindView();
        initTextToSpeech();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        CountDown();

        //体温测量
        startMeasure();
    }

    private void FindView() {
        exit_tstv = findViewById(R.id.exit_tstv);
        stub_loading = findViewById(R.id.stub_loading);
        stub_msg = findViewById(R.id.stub_msg);
        loading_view = stub_loading.inflate();
        msg_view = stub_msg.inflate();

        value1_tstv = findViewById(R.id.value1_tstv);
        value1_dw_tstv = findViewById(R.id.value1_dw_tstv);

        bt_skip = loading_view.findViewById(R.id.bt_skip);

        tv_temp = msg_view.findViewById(R.id.tv_temp);
        bt_remeasure = msg_view.findViewById(R.id.bt_remeasure);
        bt_next = msg_view.findViewById(R.id.bt_next);

        if (isAll) {
            bt_skip.setVisibility(View.VISIBLE);
            bt_next.setVisibility(View.VISIBLE);
        } else {
            bt_skip.setVisibility(View.GONE);
            bt_next.setVisibility(View.GONE);
        }

        exit_tstv.setOnClickListener(this);
        bt_remeasure.setOnClickListener(this);
        bt_skip.setOnClickListener(this);
        bt_next.setOnClickListener(this);
    }

    private void initTextToSpeech() {
        // 参数Context,TextToSpeech.OnInitListener
        mTextToSpeech = new TextToSpeech(this, this);
        // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
        mTextToSpeech.setPitch(1.0f);
        // 设置语速
        mTextToSpeech.setSpeechRate(0.5f);
    }

    */
/**
     * 用来初始化TextToSpeech引擎
     *
     * @param status SUCCESS或ERROR这2个值
     *//*

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // setLanguage设置语言
            int result = mTextToSpeech.setLanguage(Locale.CHINA);
            // TextToSpeech.LANG_MISSING_DATA：表示语言的数据丢失
            // TextToSpeech.LANG_NOT_SUPPORTED：不支持
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                ToastUtils.showToast(this, "语音包数据丢失或不支持");
            }
        }
    }

    private void startMeasure() {
        temp = 0;
        value1_tstv.setText("00.0");
        value1_dw_tstv.setText("℃");
        setViewStub(0);
        isMeasure = true;
    }

    private void setViewStub(int type) {
        switch (type) {
            case 0:
                stub_loading.setVisibility(View.VISIBLE);
                stub_msg.setVisibility(View.GONE);
                break;
            case 1:
                stub_loading.setVisibility(View.GONE);
                stub_msg.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_tstv:
                startActivity(new Intent(Temp_Activity.this, ADActivity.class));
                finish();
                break;
            case R.id.bt_remeasure:
                startMeasure();
                break;
            case R.id.bt_skip:
                Physicals p = PhysicalsOperation.queryPhysicals();
                if (p.isAllZero()) {
                    startActivity(new Intent(Temp_Activity.this, ADActivity.class));
                    finish();
                } else {
                    Intent skip_intent = new Intent(Temp_Activity.this, PhysicalsActivity.class);
                    Bundle skip_bundle = new Bundle();
                    skip_bundle.putBoolean("isUser", isUser);
                    skip_intent.putExtras(skip_bundle);
                    startActivity(skip_intent);
                    finish();
                }
                break;
            case R.id.bt_next:
                Physicals physicals = PhysicalsOperation.queryPhysicals();
                physicals.setTemp(temp);
                PhysicalsOperation.insertPhysicals(physicals);

                Intent next_intent = new Intent(Temp_Activity.this, PhysicalsActivity.class);
                Bundle next_bundle = new Bundle();
                next_bundle.putBoolean("isUser", isUser);
                next_intent.putExtras(next_bundle);
                startActivity(next_intent);
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
            startActivity(new Intent(Temp_Activity.this, ADActivity.class));
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTempTask(TempTask count) {
        setViewStub(1);
        temp = count.temp;
        value1_tstv.setText(count.temp + "");
        value1_dw_tstv.setText("℃");
        TempType(tv_temp, count.temp);
        String tempType;
        if (count.temp >= 37.3) {
            tempType = "偏高";
        } else {
            tempType = "正常";
        }
        if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
            mTextToSpeech.speak("您的体温：" + count.temp + "摄氏度，体温" + tempType, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void TempType(TextView tv, double temp) {
        if (temp >= 37.3) {
            tv.setText("偏高");
            tv.setTextColor(Color.parseColor("#E65A5A"));
        } else {
            tv.setText("正常");
            tv.setTextColor(Color.parseColor("#0088CC"));
        }
    }

    private void CountDown() {
        countTimerView = new CountTimer(90 * 1000, 1000, Temp_Activity.this);
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        timeStart();
    }

    @Override
    public void onDestroy() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
            mTextToSpeech = null;
        }
        super.onDestroy();
        isMeasure = false;
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 不管是否正在朗读TTS都被打断
        mTextToSpeech.stop();
        // 关闭，释放资源
        mTextToSpeech.shutdown();
    }
}*/
