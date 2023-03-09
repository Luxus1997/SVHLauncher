package com.haoyue.svhlauncher.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haoyue.svhlauncher.R;
import com.haoyue.svhlauncher.SVHApplication;
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

public class TempActivity extends IActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    //退出
    TextView exit_tstv;

    ImageView temp_type;
    RadioButton radio_tw7;
    RadioButton radio_tw8;
    RadioButton radio_tw9;
    RadioButton radio_tw10;
    RadioButton radio_tw11;
    RadioButton radio_tw12;

    //开启测量
    boolean isMeasure = false;

    //是否全部测量
    boolean isAll = false;
    boolean isUser = false;
    //下一项
    Button bt_next;
    //-
    double temp7 = 0;
    double temp8 = 0;
    double temp9 = 0;
    double temp10 = 0;
    double temp11 = 0;
    double temp12 = 0;
    int checkTW = 7;
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
        temp_type = findViewById(R.id.temp_type);

        radio_tw7 = findViewById(R.id.radio_tw7);
        radio_tw8 = findViewById(R.id.radio_tw8);
        radio_tw9 = findViewById(R.id.radio_tw9);
        radio_tw10 = findViewById(R.id.radio_tw10);
        radio_tw11 = findViewById(R.id.radio_tw11);
        radio_tw12 = findViewById(R.id.radio_tw12);

        bt_next = findViewById(R.id.bt_next);

        exit_tstv.setOnClickListener(this);
        radio_tw7.setOnClickListener(this);
        radio_tw8.setOnClickListener(this);
        radio_tw9.setOnClickListener(this);
        radio_tw10.setOnClickListener(this);
        radio_tw11.setOnClickListener(this);
        radio_tw12.setOnClickListener(this);
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
        setRDChecked(7);
        setViewStub(1);
        isMeasure = true;
    }

    private void setRDChecked(int type) {
        switch (type) {
            case 7:
                radio_tw7.setChecked(true);
                radio_tw8.setChecked(false);
                radio_tw9.setChecked(false);
                radio_tw10.setChecked(false);
                radio_tw11.setChecked(false);
                radio_tw12.setChecked(false);
                Glide.with(TempActivity.this).load(R.drawable.temp_tw7).into(temp_type);
                checkTW = 7;
                break;
            case 8:
                radio_tw7.setChecked(false);
                radio_tw8.setChecked(true);
                radio_tw9.setChecked(false);
                radio_tw10.setChecked(false);
                radio_tw11.setChecked(false);
                radio_tw12.setChecked(false);
                Glide.with(TempActivity.this).load(R.drawable.temp_tw8).into(temp_type);
                checkTW = 8;
                break;
            case 9:
                radio_tw7.setChecked(false);
                radio_tw8.setChecked(false);
                radio_tw9.setChecked(true);
                radio_tw10.setChecked(false);
                radio_tw11.setChecked(false);
                radio_tw12.setChecked(false);
                Glide.with(TempActivity.this).load(R.drawable.temp_tw9).into(temp_type);
                checkTW = 9;
                break;
            case 10:
                radio_tw7.setChecked(false);
                radio_tw8.setChecked(false);
                radio_tw9.setChecked(false);
                radio_tw10.setChecked(true);
                radio_tw11.setChecked(false);
                radio_tw12.setChecked(false);
                Glide.with(TempActivity.this).load(R.drawable.temp_tw10).into(temp_type);
                checkTW = 10;
                break;
            case 11:
                radio_tw7.setChecked(false);
                radio_tw8.setChecked(false);
                radio_tw9.setChecked(false);
                radio_tw10.setChecked(false);
                radio_tw11.setChecked(true);
                radio_tw12.setChecked(false);
                Glide.with(TempActivity.this).load(R.drawable.temp_tw11).into(temp_type);
                checkTW = 11;
                break;
            case 12:
                radio_tw7.setChecked(false);
                radio_tw8.setChecked(false);
                radio_tw9.setChecked(false);
                radio_tw10.setChecked(false);
                radio_tw11.setChecked(false);
                radio_tw12.setChecked(true);
                Glide.with(TempActivity.this).load(R.drawable.temp_tw12).into(temp_type);
                checkTW = 12;
                break;
        }
    }

    private void setViewStub(int type) {
        switch (type) {
            case 0:
                if (isAll) {
                    bt_next.setVisibility(View.VISIBLE);
                } else {
                    bt_next.setVisibility(View.GONE);
                }
                break;
            case 1:
                bt_next.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        SVHApplication.getInstance().SvhClick();
        switch (v.getId()) {
            case R.id.exit_tstv:
                startActivity(new Intent(TempActivity.this, ADActivity.class));
                finish();
                break;
            case R.id.radio_tw7:
                setRDChecked(7);
                break;
            case R.id.radio_tw8:
                setRDChecked(8);
                break;
            case R.id.radio_tw9:
                setRDChecked(9);
                break;
            case R.id.radio_tw10:
                setRDChecked(10);
                break;
            case R.id.radio_tw11:
                setRDChecked(11);
                break;
            case R.id.radio_tw12:
                setRDChecked(12);
                break;
            case R.id.bt_next:
                Physicals physicals = PhysicalsOperation.queryPhysicals();
                physicals.setTemp7(temp7);
                physicals.setTemp8(temp8);
                physicals.setTemp9(temp9);
                physicals.setTemp10(temp10);
                physicals.setTemp11(temp11);
                physicals.setTemp12(temp12);
                PhysicalsOperation.insertPhysicals(physicals);

                Intent next_intent = new Intent(TempActivity.this, PhysicalsActivity.class);
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
            SVHApplication.getInstance().SvhClick();
            startActivity(new Intent(TempActivity.this, ADActivity.class));
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
        switch (checkTW) {
            case 7:
                temp7 = count.temp;
                radio_tw7.setText("额温：" + temp7 + " ℃");
                if (isZero()){
                    setViewStub(1);
                }else {
                    setViewStub(0);
                }
                if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
                    mTextToSpeech.speak("您的额温：" + count.temp + "摄氏度", TextToSpeech.QUEUE_FLUSH, null);
                }
                setRDChecked(8);
                break;
            case 8:
                temp8 = count.temp;
                radio_tw8.setText("手心：" + temp8 + " ℃");
                if (isZero()){
                    setViewStub(1);
                }else {
                    setViewStub(0);
                }
                if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
                    mTextToSpeech.speak("您的手心：" + count.temp + "摄氏度", TextToSpeech.QUEUE_FLUSH, null);
                }
                setRDChecked(9);
                break;
            case 9:
                temp9 = count.temp;
                radio_tw9.setText("手背：" + temp9 + " ℃");
                if (isZero()){
                    setViewStub(1);
                }else {
                    setViewStub(0);
                }
                if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
                    mTextToSpeech.speak("您的手背：" + count.temp + "摄氏度", TextToSpeech.QUEUE_FLUSH, null);
                }
                setRDChecked(10);
                break;
            case 10:
                temp10 = count.temp;
                radio_tw10.setText("胸口：" + temp10 + " ℃");
                if (isZero()){
                    setViewStub(1);
                }else {
                    setViewStub(0);
                }
                if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
                    mTextToSpeech.speak("您的胸口：" + count.temp + "摄氏度", TextToSpeech.QUEUE_FLUSH, null);
                }
                setRDChecked(11);
                break;
            case 11:
                temp11 = count.temp;
                radio_tw11.setText("胃脘：" + temp11 + " ℃");
                if (isZero()){
                    setViewStub(1);
                }else {
                    setViewStub(0);
                }
                if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
                    mTextToSpeech.speak("您的胃脘：" + count.temp + "摄氏度", TextToSpeech.QUEUE_FLUSH, null);
                }
                setRDChecked(12);
                break;
            case 12:
                temp12 = count.temp;
                radio_tw12.setText("腹部：" + temp12 + " ℃");
                if (isZero()){
                    setViewStub(1);
                }else {
                    setViewStub(0);
                }
                if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
                    mTextToSpeech.speak("您的腹部：" + count.temp + "摄氏度", TextToSpeech.QUEUE_FLUSH, null);
                }
                setRDChecked(12);
                break;
        }
    }

    private boolean isZero() {
        if (temp7 == 0 || temp8 == 0 || temp9 == 0 || temp10 == 0 || temp11 == 0 || temp12 == 0) {
            return true;
        } else {
            return false;
        }
    }

    private void CountDown() {
        countTimerView = new CountTimer(300 * 1000, 1000, TempActivity.this);
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
}