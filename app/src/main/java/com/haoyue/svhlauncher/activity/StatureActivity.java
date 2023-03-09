package com.haoyue.svhlauncher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoyue.svhlauncher.R;
import com.haoyue.svhlauncher.SVHApplication;
import com.haoyue.svhlauncher.activity.base.IActivity;
import com.haoyue.svhlauncher.countdowntimer.CountDownTimerSupport;
import com.haoyue.svhlauncher.countdowntimer.OnCountDownTimerListener;
import com.haoyue.svhlauncher.daobean.Physicals;
import com.haoyue.svhlauncher.eventtask.CountDownTask;
import com.haoyue.svhlauncher.eventtask.S1Task;
import com.haoyue.svhlauncher.eventtask.StatureTask;
import com.haoyue.svhlauncher.eventtask.UpTask;
import com.haoyue.svhlauncher.operation.PhysicalsOperation;
import com.haoyue.svhlauncher.utils.DataUtils;
import com.haoyue.svhlauncher.utils.ToastUtils;
import com.holtek.libHTBodyfat.HTBodyBasicInfo;
import com.holtek.libHTBodyfat.HTBodyResultAllBody;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.Locale;

public class StatureActivity extends IActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    //退出
    TextView exit_tstv;
    //Loading页
    ViewStub stub_loading;
    View loading_view;
    //测量结果
    ViewStub stub_msg;
    View msg_view;

    //身高值
    TextView value1_tstv;
    //身高单位
    TextView value1_dw_tstv;
    //体重值
    TextView value2_tstv;
    //体重单位
    TextView value2_dw_tstv;
    //BMI值
    TextView value3_tstv;
    //BMI单位
    TextView value3_dw_tstv;

    //测量提示
    TextView stature_tip;
    //理想体重
    TextView ideal_weight;
    //体重调节
    TextView weight_regulation;
    //BMI图
    ImageView stature_photo;
    //体重描述
    TextView stature_tv;

    double heightCm;
    double weightKg;
    double htBMI;
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
        setContentView(R.layout.activity_stature);

        Bundle data = getIntent().getExtras();
        isAll = data.getBoolean("isAll");
        isUser = data.getBoolean("isUser");

        FindView();
        initTextToSpeech();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        CountDown();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpTask(UpTask upTask) {
        if (!isMeasure) {
            isMeasure = true;
            setViewStub(0);
            SVHApplication.getInstance().getSerialPortUtil().sendSerialPort(DataUtils.convertStringToHex("S1$\r\n"));
            mTimer.pause();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onS1Task(S1Task s1Task) {
        stature_tip.setText(getString(R.string.measure_bmi_ts1));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStatureTask(StatureTask statureTask) {
        heightCm = Double.parseDouble(statureTask.height);
        weightKg = Double.parseDouble(statureTask.weight);

        HTBodyResultAllBody body = new HTBodyResultAllBody();
        HTBodyBasicInfo info = new HTBodyBasicInfo(0, (int) heightCm, weightKg, 0);
        body.getBodyfatWithBasicInfo(info);
        htBMI = body.htBMI;

        DecimalFormat df = new DecimalFormat("#.00");
        value1_tstv.setText(df.format(heightCm));
        value1_dw_tstv.setText("cm");
        value2_tstv.setText(df.format(weightKg));
        value2_dw_tstv.setText("kg");
        value3_tstv.setText(df.format(htBMI));
        value3_dw_tstv.setText(BMIType(htBMI));

        if (statureTask.FinalData) {
            setViewStub(1);
            DecimalFormat d = new DecimalFormat("#.0");
            ideal_weight.setText("理想体重：" + d.format(IdealWeight()) + "kg");
            if (IdealWeight() > weightKg) {
                double kg = IdealWeight() - weightKg;
                weight_regulation.setText("体重调节：+" + d.format(kg) + "kg");
            } else {
                double kg = weightKg - IdealWeight();
                weight_regulation.setText("体重调节：-" + d.format(kg) + "kg");
            }
            BMIPhoto(htBMI);
            stature_tv.setText(BMIType(htBMI));
            if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
                mTextToSpeech.speak("您的身高:" + (int) heightCm + "厘米，" + "体重:" + (int) weightKg + "公斤，" + "体型" + BMIType(htBMI), TextToSpeech.QUEUE_FLUSH, null);
            }
        }

        isMeasure = false;
        mTimer.reset();
        mTimer.start();
    }

    private void FindView() {
        exit_tstv = findViewById(R.id.exit_tstv);
        stub_loading = findViewById(R.id.stub_loading);
        stub_msg = findViewById(R.id.stub_msg);
        loading_view = stub_loading.inflate();
        msg_view = stub_msg.inflate();

        value1_tstv = findViewById(R.id.value1_tstv);
        value1_dw_tstv = findViewById(R.id.value1_dw_tstv);
        value2_tstv = findViewById(R.id.value2_tstv);
        value2_dw_tstv = findViewById(R.id.value2_dw_tstv);
        value3_tstv = findViewById(R.id.value3_tstv);
        value3_dw_tstv = findViewById(R.id.value3_dw_tstv);

        stature_tip = loading_view.findViewById(R.id.stature_tip);
        bt_skip = loading_view.findViewById(R.id.bt_skip);

        ideal_weight = msg_view.findViewById(R.id.ideal_weight);
        weight_regulation = msg_view.findViewById(R.id.weight_regulation);
        stature_photo = msg_view.findViewById(R.id.stature_photo);
        stature_tv = msg_view.findViewById(R.id.stature_tv);
        bt_next = msg_view.findViewById(R.id.bt_next);

        if (isAll) {
            bt_skip.setVisibility(View.VISIBLE);
            bt_next.setVisibility(View.VISIBLE);
        } else {
            bt_skip.setVisibility(View.GONE);
            bt_next.setVisibility(View.GONE);
        }
        exit_tstv.setOnClickListener(this);
        bt_skip.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        setViewStub(0);
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

    private void setViewStub(int type) {
        switch (type) {
            case 0:
                stub_loading.setVisibility(View.VISIBLE);
                stub_msg.setVisibility(View.GONE);
                break;
            case 1:
                stub_msg.setVisibility(View.VISIBLE);
                stub_loading.setVisibility(View.GONE);
                break;
        }
    }

    private double IdealWeight() {
        double m = heightCm / 100;
        return 21.7 * (m * m);
    }

    private String BMIType(double bmi) {
        if (bmi <= 18.5) {
            return "偏瘦";
        } else if (bmi > 18.5 && bmi <= 24.0) {
            return "正常";
        } else if (bmi > 24.0 && bmi <= 28.0) {
            return "过重";
        } else if (bmi > 28.0) {
            return "肥胖";
        } else {
            return "";
        }
    }

    private void BMIPhoto(double bmi) {
        if (bmi <= 18.5) {
            stature_photo.setImageResource(R.drawable.stature_photo_lean);
        } else if (bmi > 18.5 && bmi <= 24.0) {
            stature_photo.setImageResource(R.drawable.stature_photo_normal);
        } else if (bmi > 24.0 && bmi <= 28.0) {
            stature_photo.setImageResource(R.drawable.stature_photo_fat);
        } else if (bmi > 28.0) {
            stature_photo.setImageResource(R.drawable.stature_photo_fat);
        } else {
            stature_photo.setImageResource(R.drawable.stature_photo_normal);
        }
    }

    CountDownTimerSupport mTimer;

    @Override
    public void onClick(View v) {
        SVHApplication.getInstance().SvhClick();
        switch (v.getId()) {
            case R.id.exit_tstv:
                if (!isMeasure) {
                    startActivity(new Intent(StatureActivity.this, ADActivity.class));
                    finish();
                } else {
                    ToastUtils.showToast(StatureActivity.this, getString(R.string.measuring));
                }
                break;
            case R.id.bt_skip:
                if (!isMeasure) {
                    Intent skip_intent = new Intent(StatureActivity.this, PressureActivity.class);
                    Bundle skip_bundle = new Bundle();
                    skip_bundle.putBoolean("isAll", isAll);
                    skip_bundle.putBoolean("isUser", isUser);
                    skip_intent.putExtras(skip_bundle);
                    startActivity(skip_intent);
                    finish();
                } else {
                    ToastUtils.showToast(StatureActivity.this, getString(R.string.measuring));
                }
                break;
            case R.id.bt_next:
                Physicals physicals = PhysicalsOperation.queryPhysicals();
                physicals.setHeightCm(heightCm);
                physicals.setWeightKg(weightKg);
                DecimalFormat df = new DecimalFormat("#.00");
                physicals.setHtBMI(Double.parseDouble(df.format(htBMI)));
                PhysicalsOperation.insertPhysicals(physicals);

                Intent next_intent = new Intent(StatureActivity.this, PressureActivity.class);
                Bundle next_bundle = new Bundle();
                next_bundle.putBoolean("isAll", isAll);
                next_bundle.putBoolean("isUser", isUser);
                next_intent.putExtras(next_bundle);
                startActivity(next_intent);
                finish();
                break;
        }
    }

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
                    startActivity(new Intent(StatureActivity.this, ADActivity.class));
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