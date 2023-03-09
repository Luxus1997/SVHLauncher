package com.haoyue.svhlauncher.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoyue.svhlauncher.R;
import com.haoyue.svhlauncher.SVHApplication;
import com.haoyue.svhlauncher.activity.base.IActivity;
import com.haoyue.svhlauncher.countdowntimer.CountDownTimerSupport;
import com.haoyue.svhlauncher.countdowntimer.OnCountDownTimerListener;
import com.haoyue.svhlauncher.daobean.Physicals;
import com.haoyue.svhlauncher.eventtask.CountDownTask;
import com.haoyue.svhlauncher.eventtask.PressureTask;
import com.haoyue.svhlauncher.eventtask.S2Task;
import com.haoyue.svhlauncher.operation.PhysicalsOperation;
import com.haoyue.svhlauncher.utils.DataUtils;
import com.haoyue.svhlauncher.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

public class PressureActivity extends IActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    //退出
    TextView exit_tstv;
    //Loading页
    ViewStub stub_loading;
    View loading_view;
    //测量结果
    ViewStub stub_msg;
    View msg_view;
    //测量失败
    ViewStub stub_error;
    View msg_error;
    LinearLayout result_v;
    LinearLayout result_bottom;

    //高压
    TextView value1_tstv;
    //高压单位
    TextView value1_dw_tstv;
    //低压
    TextView value2_tstv;
    //低压单位
    TextView value2_dw_tstv;
    //心率
    TextView value3_tstv;
    //心率单位
    TextView value3_dw_tstv;

    //测量提示语
    TextView pressure_tvmsg;
    //重新测量
    Button bt_remeasure;
    Button error_remeasure;
    //高压衡量
    TextView tv_high_pressure;
    //低压衡量
    TextView tv_low_pressure;
    //心率衡量
    TextView tv_heart_rate;

    int high_pressure;
    int low_pressure;
    int heart_rate;
    //开启测量
    boolean isMeasure = false;

    //是否全部测量
    boolean isAll = false;
    boolean isUser = false;
    //跳过
    Button bt_skip;
    //下一项
    Button bt_next;
    // TTS对象
    private TextToSpeech mTextToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure);

        Bundle data = getIntent().getExtras();
        isAll = data.getBoolean("isAll");
        isUser = data.getBoolean("isUser");

        FindView();
        initTextToSpeech();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        CountDown();

        //高压
        value1_tstv.setText("000");
        value1_dw_tstv.setText("mmHg");
        //低压
        value2_tstv.setText("000");
        value2_dw_tstv.setText("mmHg");
        //心率
        value3_tstv.setText("00");
        value3_dw_tstv.setText("次/分钟");

        startMeasure();
    }

    private void FindView() {
        exit_tstv = findViewById(R.id.exit_tstv);
        stub_loading = findViewById(R.id.stub_loading);
        stub_msg = findViewById(R.id.stub_msg);
        stub_error = findViewById(R.id.stub_error);
        result_v = findViewById(R.id.result_v);
        result_bottom = findViewById(R.id.result_bottom);
        loading_view = stub_loading.inflate();
        msg_view = stub_msg.inflate();
        msg_error = stub_error.inflate();

        value1_tstv = findViewById(R.id.value1_tstv);
        value1_dw_tstv = findViewById(R.id.value1_dw_tstv);
        value2_tstv = findViewById(R.id.value2_tstv);
        value2_dw_tstv = findViewById(R.id.value2_dw_tstv);
        value3_tstv = findViewById(R.id.value3_tstv);
        value3_dw_tstv = findViewById(R.id.value3_dw_tstv);

        pressure_tvmsg = loading_view.findViewById(R.id.pressure_tvmsg);
        bt_skip = loading_view.findViewById(R.id.bt_skip);

        tv_high_pressure = msg_view.findViewById(R.id.tv_high_pressure);
        tv_low_pressure = msg_view.findViewById(R.id.tv_low_pressure);
        tv_heart_rate = msg_view.findViewById(R.id.tv_heart_rate);
        bt_remeasure = msg_view.findViewById(R.id.bt_remeasure);
        bt_next = msg_view.findViewById(R.id.bt_next);

        error_remeasure = msg_error.findViewById(R.id.error_remeasure);

        if (isAll) {
            bt_skip.setVisibility(View.VISIBLE);
            bt_next.setVisibility(View.VISIBLE);
        } else {
            bt_skip.setVisibility(View.GONE);
            bt_next.setVisibility(View.GONE);
        }

        exit_tstv.setOnClickListener(this);
        bt_remeasure.setOnClickListener(this);
        error_remeasure.setOnClickListener(this);
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

    /**
     * 用来初始化TextToSpeech引擎
     *
     * @param status SUCCESS或ERROR这2个值
     */
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
        setViewStub(0);
        value1_tstv.setText("000");
        value2_tstv.setText("000");
        value3_tstv.setText("00");
        pressure_tvmsg.setText(getResources().getText(R.string.measure_bp_ts1));
        if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
            mTextToSpeech.speak(getString(R.string.measure_bp_ts1), TextToSpeech.QUEUE_FLUSH, null);
        }
        SVHApplication.getInstance().getSerialPortUtil().sendSerialPort(DataUtils.convertStringToHex("S2$\r\n"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onS2Task(S2Task s2Task) {
        if (!isMeasure) {
            isMeasure = true;
            pressure_tvmsg.setText(getResources().getText(R.string.measure_fp_ts2));
            if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
                mTextToSpeech.speak(getString(R.string.measure_fp_ts2), TextToSpeech.QUEUE_FLUSH, null);
            }
            mTimer.pause();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPressureTask(PressureTask pressureTask) {
        if (!pressureTask.FinalData) {
            setViewStub(2);
            //测量失败
            if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
                mTextToSpeech.speak(getString(R.string.measure_fail), TextToSpeech.QUEUE_FLUSH, null);
            }
        } else {
            setViewStub(1);
            high_pressure = pressureTask.high_pressure;
            low_pressure = pressureTask.low_pressure;
            heart_rate = pressureTask.heart_rate;
            //高压
            value1_tstv.setText(high_pressure + "");
            value1_dw_tstv.setText("mmHg");
            //低压
            value2_tstv.setText(low_pressure + "");
            value2_dw_tstv.setText("mmHg");
            //心率
            value3_tstv.setText(heart_rate + "");
            value3_dw_tstv.setText("次/分钟");
            //高压衡量
            hPressureType(tv_high_pressure, high_pressure);
            //低压衡量
            lPressureType(tv_low_pressure, low_pressure);
            //心率衡量
            heartType(tv_heart_rate, heart_rate);
            if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
                mTextToSpeech.speak("您的高压：" + high_pressure + "毫米汞柱，低压：" + low_pressure + "毫米汞柱，心率：每分钟" + heart_rate + "次", TextToSpeech.QUEUE_FLUSH, null);
            }
        }
        isMeasure = false;
        mTimer.reset();
        mTimer.start();
    }

    private void hPressureType(TextView tv, int high_pressure) {
        if (high_pressure < 90) {
            tv.setText("偏低");
            tv.setTextColor(Color.parseColor("#E65A5A"));
        } else if (high_pressure >= 90 && high_pressure < 140) {
            tv.setText("正常");
            tv.setTextColor(Color.parseColor("#0088CC"));
        } else if (high_pressure >= 140) {
            tv.setText("偏高");
            tv.setTextColor(Color.parseColor("#E65A5A"));
        } else {
            tv.setText("");
        }
    }

    private void lPressureType(TextView tv, int low_pressure) {
        if (low_pressure < 60) {
            tv.setText("偏低");
            tv.setTextColor(Color.parseColor("#E65A5A"));
        } else if (low_pressure >= 60 && low_pressure < 90) {
            tv.setText("正常");
            tv.setTextColor(Color.parseColor("#0088CC"));
        } else if (low_pressure >= 90) {
            tv.setText("偏高");
            tv.setTextColor(Color.parseColor("#E65A5A"));
        } else {
            tv.setText("");
        }
    }

    private void heartType(TextView tv, int heart_rate) {
        if (heart_rate < 60) {
            tv.setText("偏低");
            tv.setTextColor(Color.parseColor("#E65A5A"));
        } else if (heart_rate >= 60 && heart_rate < 100) {
            tv.setText("正常");
            tv.setTextColor(Color.parseColor("#0088CC"));
        } else if (heart_rate >= 100) {
            tv.setText("偏高");
            tv.setTextColor(Color.parseColor("#E65A5A"));
        } else {
            tv.setText("");
        }
    }

    private void setViewStub(int type) {
        switch (type) {
            case 0:
                result_v.setVisibility(View.VISIBLE);
                result_bottom.setVisibility(View.VISIBLE);
                stub_error.setVisibility(View.GONE);
                stub_loading.setVisibility(View.VISIBLE);
                stub_msg.setVisibility(View.GONE);
                break;
            case 1:
                result_v.setVisibility(View.VISIBLE);
                result_bottom.setVisibility(View.VISIBLE);
                stub_error.setVisibility(View.GONE);
                stub_loading.setVisibility(View.GONE);
                stub_msg.setVisibility(View.VISIBLE);
                break;
            case 2:
                result_v.setVisibility(View.GONE);
                result_bottom.setVisibility(View.GONE);
                stub_error.setVisibility(View.VISIBLE);
                stub_loading.setVisibility(View.GONE);
                stub_msg.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        SVHApplication.getInstance().SvhClick();
        switch (v.getId()) {
            case R.id.exit_tstv:
                if (!isMeasure) {
                    startActivity(new Intent(PressureActivity.this, ADActivity.class));
                    finish();
                }
                break;
            case R.id.bt_remeasure:
            case R.id.error_remeasure:
                startMeasure();
                break;
            case R.id.bt_skip:
                if (!isMeasure) {
                    if (isUser){
                        Intent skip_intent = new Intent(PressureActivity.this, TongueActivity.class);
                        Bundle skip_bundle = new Bundle();
                        skip_bundle.putBoolean("isAll", isAll);
                        skip_bundle.putBoolean("isUser", isUser);
                        skip_intent.putExtras(skip_bundle);
                        startActivity(skip_intent);
                        finish();
                    }else {
                        Intent all_intent = new Intent(PressureActivity.this, TempActivity.class);
                        Bundle all_bundle = new Bundle();
                        all_bundle.putBoolean("isAll", isAll);
                        all_bundle.putBoolean("isUser", isUser);
                        all_intent.putExtras(all_bundle);
                        startActivity(all_intent);
                        finish();
                    }
                } else {
                    ToastUtils.showToast(PressureActivity.this, getString(R.string.measuring));
                }
                break;
            case R.id.bt_next:
                Physicals physicals = PhysicalsOperation.queryPhysicals();
                physicals.setHigh_pressure(high_pressure);
                physicals.setLow_pressure(low_pressure);
                physicals.setHeart_rate(heart_rate);
                PhysicalsOperation.insertPhysicals(physicals);
                if (isUser){
                    Intent next_intent = new Intent(PressureActivity.this, TongueActivity.class);
                    Bundle next_bundle = new Bundle();
                    next_bundle.putBoolean("isAll", isAll);
                    next_bundle.putBoolean("isUser", isUser);
                    next_intent.putExtras(next_bundle);
                    startActivity(next_intent);
                    finish();
                }else {
                    Intent all_intent = new Intent(PressureActivity.this, TempActivity.class);
                    Bundle all_bundle = new Bundle();
                    all_bundle.putBoolean("isAll", isAll);
                    all_bundle.putBoolean("isUser", isUser);
                    all_intent.putExtras(all_bundle);
                    startActivity(all_intent);
                    finish();
                }
                break;
        }
    }

    CountDownTimerSupport mTimer;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCountDownTask(CountDownTask count) {
        exit_tstv.setText(" 退出[" + count.second + "s] ");
    }

    private void CountDown() {
        mTimer = new CountDownTimerSupport(90 * 1000, 1000);
        mTimer.setOnCountDownTimerListener(new OnCountDownTimerListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                // 倒计时间隔
                int time = (int) (millisUntilFinished / 1000);
                EventBus.getDefault().postSticky(new CountDownTask(time));
            }

            @Override
            public void onFinish() {
                if (!isMeasure) {
                    SVHApplication.getInstance().SvhClick();
                    startActivity(new Intent(PressureActivity.this, ADActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancel() {
                // 倒计时手动停止
            }
        });
    }

    private void timeStart() {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mTimer.reset();
                mTimer.start();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mTimer.stop();
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

}