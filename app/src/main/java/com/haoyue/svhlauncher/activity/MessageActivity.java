package com.haoyue.svhlauncher.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.haoyue.svhlauncher.R;
import com.haoyue.svhlauncher.SVHApplication;
import com.haoyue.svhlauncher.activity.base.IActivity;
import com.haoyue.svhlauncher.bean.UploadBean;
import com.haoyue.svhlauncher.daobean.Physicals;
import com.haoyue.svhlauncher.daobean.Register;
import com.haoyue.svhlauncher.dialog.CustomProgressDialog;
import com.haoyue.svhlauncher.eventtask.CountDownTask;
import com.haoyue.svhlauncher.gnop.GNopUtils;
import com.haoyue.svhlauncher.operation.PhysicalsOperation;
import com.haoyue.svhlauncher.operation.RegisterOperation;
import com.haoyue.svhlauncher.utils.DialogUtils;
import com.haoyue.svhlauncher.utils.GsonUtils;
import com.haoyue.svhlauncher.utils.MsgUtils;
import com.haoyue.svhlauncher.utils.StringUtils;
import com.haoyue.svhlauncher.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.haoyue.svhlauncher.utils.MsgUtils.APP_KEY;
import static com.haoyue.svhlauncher.utils.MsgUtils.APP_SECRET;
import static com.haoyue.svhlauncher.utils.MsgUtils.tongue_bottom;
import static com.haoyue.svhlauncher.utils.MsgUtils.tongue_top;
import static java.lang.String.valueOf;

public class MessageActivity extends IActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    //??????
    TextView exit_tstv;
    //?????????
    TextView message_tv_tz;
    //??????-??????-??????-??????
    TextView message_tv_name;
    TextView message_tv_sex;
    TextView message_tv_age;
    TextView message_tv_illness;
    //??????-??????-BMI
    TextView message_tv_height;
    TextView message_tv_width;
    TextView message_tv_bmi;
    //??????-??????-??????
    TextView message_tv_high;
    TextView message_tv_low;
    TextView message_tv_heart;
    //??????
    TextView message_tv_tw7;
    TextView message_tv_tw8;
    TextView message_tv_tw9;
    TextView message_tv_tw10;
    TextView message_tv_tw11;
    TextView message_tv_tw12;
    //??????
    ImageView message_ttongue;
    ImageView message_btongue;
    //??????-??????
    Button upload_report;
    Button start_measure;
    //-
    boolean isFinal = false;
    String height = "";
    String weight = "";
    // TTS??????
    private TextToSpeech mTextToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Bundle data = getIntent().getExtras();
        isFinal = data.getBoolean("isFinal");
        height = data.getString("height");
        weight = data.getString("weight");

        FindView();
        initTextToSpeech();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        CountDown();
        Message();
    }

    private void FindView() {
        exit_tstv = findViewById(R.id.exit_tstv);
        message_tv_tz = findViewById(R.id.message_tv_tz);
        message_tv_name = findViewById(R.id.message_tv_name);
        message_tv_sex = findViewById(R.id.message_tv_sex);
        message_tv_age = findViewById(R.id.message_tv_age);
        message_tv_illness = findViewById(R.id.message_tv_illness);
        message_tv_height = findViewById(R.id.message_tv_height);
        message_tv_width = findViewById(R.id.message_tv_width);
        message_tv_bmi = findViewById(R.id.message_tv_bmi);
        message_tv_high = findViewById(R.id.message_tv_high);
        message_tv_low = findViewById(R.id.message_tv_low);
        message_tv_heart = findViewById(R.id.message_tv_heart);

        message_tv_tw7 = findViewById(R.id.message_tv_tw7);
        message_tv_tw8 = findViewById(R.id.message_tv_tw8);
        message_tv_tw9 = findViewById(R.id.message_tv_tw9);
        message_tv_tw10 = findViewById(R.id.message_tv_tw10);
        message_tv_tw11 = findViewById(R.id.message_tv_tw11);
        message_tv_tw12 = findViewById(R.id.message_tv_tw12);

        message_ttongue = findViewById(R.id.message_ttongue);
        message_btongue = findViewById(R.id.message_btongue);

        upload_report = findViewById(R.id.upload_report);
        start_measure = findViewById(R.id.start_measure);

        exit_tstv.setOnClickListener(this);
        upload_report.setOnClickListener(this);
        start_measure.setOnClickListener(this);
    }

    private void initTextToSpeech() {
        // ??????Context,TextToSpeech.OnInitListener
        mTextToSpeech = new TextToSpeech(this, this);
        // ???????????????????????????????????????????????????????????????????????????,1.0?????????
        mTextToSpeech.setPitch(1.0f);
        // ????????????
        mTextToSpeech.setSpeechRate(0.5f);
    }

    /**
     * ???????????????TextToSpeech??????
     *
     * @param status SUCCESS???ERROR???2??????
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // setLanguage????????????
            int result = mTextToSpeech.setLanguage(Locale.CHINA);
            // TextToSpeech.LANG_MISSING_DATA??????????????????????????????
            // TextToSpeech.LANG_NOT_SUPPORTED????????????
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                ToastUtils.showToast(this, "?????????????????????????????????");
            }
        }
    }

    private void Message() {
        if (isFinal) {
            upload_report.setVisibility(View.VISIBLE);
            start_measure.setText(getString(R.string.again_measure));
        } else {
            upload_report.setVisibility(View.GONE);
            start_measure.setText(getString(R.string.start_measure));
        }
        Register r = RegisterOperation.queryRegister();
        if (StringUtils.isEmpty(r.getDevicernd())) {
            message_tv_tz.setText("????????????-");
        } else {
            message_tv_tz.setText("????????????" + r.getDevicernd());
        }
        if (StringUtils.isEmpty(r.getName())) {
            message_tv_name.setText("-");
        } else {
            message_tv_name.setText(r.getName());
        }
        if (StringUtils.isEmpty(r.getGender())) {
            message_tv_sex.setText("-");
        } else {
            if (r.getGender().equals("1")) {
                message_tv_sex.setText("???");
            } else {
                message_tv_sex.setText("???");
            }
        }
        if (StringUtils.isEmpty(r.getAge())) {
            message_tv_age.setText("-");
        } else {
            message_tv_age.setText(r.getAge());
        }
        if (StringUtils.isEmpty(r.getFormname())) {
            message_tv_illness.setText("-");
        } else {
            message_tv_illness.setText(r.getFormname());
        }

        Physicals p = PhysicalsOperation.queryPhysicals();
        if (StringUtils.isEmpty(height)) {
            if (p.getHeightCm() == 0) {
                message_tv_height.setText("?????????- cm");
            } else {
                message_tv_height.setText("?????????" + p.getHeightCm() + " cm");
            }
        } else {
            message_tv_height.setText("?????????" + height + " cm");
        }
        if (StringUtils.isEmpty(weight)) {
            if (p.getWeightKg() == 0) {
                message_tv_width.setText("?????????- kg");
            } else {
                message_tv_width.setText("?????????" + p.getWeightKg() + " kg");
            }
        } else {
            message_tv_width.setText("?????????" + weight + " kg");
        }
        if (p.getHtBMI() == 0) {
            message_tv_bmi.setText("???????????????BMI??????-");
        } else {
            message_tv_bmi.setText("???????????????BMI??????" + p.getHtBMI());
        }

        if (p.getHigh_pressure() == 0) {
            message_tv_high.setText("?????????- mmHg");
        } else {
            message_tv_high.setText("?????????" + p.getHigh_pressure() + " mmHg");
        }
        if (p.getLow_pressure() == 0) {
            message_tv_low.setText("?????????- mmHg");
        } else {
            message_tv_low.setText("?????????" + p.getLow_pressure() + " mmHg");
        }
        if (p.getHeart_rate() == 0) {
            message_tv_heart.setText("?????????- ???/??????");
        } else {
            message_tv_heart.setText("?????????" + p.getHeart_rate() + " ???/??????");
        }

        if (p.getTemp7() == 0) {
            message_tv_tw7.setText("?????????- ???");
        } else {
            message_tv_tw7.setText("?????????" + p.getTemp7() + " ???");
        }
        if (p.getTemp8() == 0) {
            message_tv_tw8.setText("?????????- ???");
        } else {
            message_tv_tw8.setText("?????????" + p.getTemp8() + " ???");
        }
        if (p.getTemp9() == 0) {
            message_tv_tw9.setText("?????????- ???");
        } else {
            message_tv_tw9.setText("?????????" + p.getTemp9() + " ???");
        }
        if (p.getTemp10() == 0) {
            message_tv_tw10.setText("?????????- ???");
        } else {
            message_tv_tw10.setText("?????????" + p.getTemp10() + " ???");
        }
        if (p.getTemp11() == 0) {
            message_tv_tw11.setText("?????????- ???");
        } else {
            message_tv_tw11.setText("?????????" + p.getTemp11() + " ???");
        }
        if (p.getTemp12() == 0) {
            message_tv_tw12.setText("?????????- ???");
        } else {
            message_tv_tw12.setText("?????????" + p.getTemp12() + " ???");
        }

        if (!file.exists() || file.length() == 0) {
            //ToastUtils.showToast(MessageActivity.this, "???????????????????????????");
            Glide.with(MessageActivity.this).load(R.drawable.default_tongue).into(message_ttongue);
        } else {
            RequestOptions options = new RequestOptions();
            options.skipMemoryCache(true);//??????????????????
            options.diskCacheStrategy(DiskCacheStrategy.NONE);//?????????disk?????????
            Glide.with(MessageActivity.this).load(file).apply(options).into(message_ttongue);
        }
        if (!file1.exists() || file1.length() == 0) {
            //ToastUtils.showToast(MessageActivity.this, "???????????????????????????");
            Glide.with(MessageActivity.this).load(R.drawable.default_tongue).into(message_btongue);
        } else {
            RequestOptions options = new RequestOptions();
            options.skipMemoryCache(true);//??????????????????
            options.diskCacheStrategy(DiskCacheStrategy.NONE);//?????????disk?????????
            Glide.with(MessageActivity.this).load(file1).apply(options).into(message_btongue);
        }
    }

    private Dialog mDialog;
    File file = new File(tongue_top);
    File file1 = new File(tongue_bottom);

    @Override
    public void onClick(View v) {
        SVHApplication.getInstance().SvhClick();
        switch (v.getId()) {
            case R.id.exit_tstv:
                startActivity(new Intent(MessageActivity.this, ADActivity.class));
                finish();
                break;
            case R.id.upload_report:
                Physicals p = PhysicalsOperation.queryPhysicals();
                if (p.isZero()) {
                    ToastUtils.showToast(MessageActivity.this, getString(R.string.not_all));
                    return;
                }
                Register r = RegisterOperation.queryRegister();
                if (StringUtils.isEmpty(r.getRecord_id())) {
                    ToastUtils.showToast(MessageActivity.this, "????????????record_id");
                    return;
                }
                if (mDialog == null) {
                    mDialog = CustomProgressDialog.createLoadingDialog(MessageActivity.this, "???????????????...");
                }
                if (!mDialog.isShowing()) {
                    DialogUtils.focusNotAle(mDialog.getWindow());
                    mDialog.show();
                    DialogUtils.hideNavigationBar(mDialog.getWindow());
                    DialogUtils.clearFocusNotAle(mDialog.getWindow());
                }

                if (!file.exists() || file.length() == 0) {
                    ToastUtils.showToast(MessageActivity.this, "???????????????????????????");
                    return;
                }
                if (!file1.exists() || file1.length() == 0) {
                    ToastUtils.showToast(MessageActivity.this, "???????????????????????????");
                    return;
                }

                Map<String, String> map = new HashMap<>();
                List<String> list = new ArrayList<>();
                list.add("sign");
                map.put("v", "1.0");//????????????
                map.put("method", "device.upload");//device.upload ????????????
                map.put("appKey", APP_KEY);//????????????
                //paramValues.put("random", r.getDevicernd());
                map.put("id", r.getRecord_id());
                map.put("DV1", p.getHeightCm() + "");
                map.put("DV2", p.getWeightKg() + "");
                map.put("DV3", p.getHtBMI() + "");
                map.put("DV4", p.getHigh_pressure() + "");
                map.put("DV5", p.getLow_pressure() + "");
                map.put("DV6", p.getHeart_rate() + "");
                map.put("DV7", p.getTemp7() + "");
                map.put("DV8", p.getTemp8() + "");
                map.put("DV9", p.getTemp9() + "");
                map.put("DV10", p.getTemp10() + "");
                map.put("DV11", p.getTemp11() + "");
                map.put("DV12", p.getTemp12() + "");
                String newSign = GNopUtils.sign(map, list, APP_SECRET);
                map.put("sign", newSign);//?????????????????????
                Log.e("sign-", newSign);

                OkHttpClient client = new OkHttpClient();
                MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
                if (file != null) {
                    RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
                    RequestBody body1 = RequestBody.create(MediaType.parse("image/*"), file1);
                    requestBody.addFormDataPart("tongueFrontImg", file.getName(), body);
                    requestBody.addFormDataPart("tongueBottomImg", file1.getName(), body1);
                }
                if (map != null) {
                    for (Map.Entry entry : map.entrySet()) {
                        requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
                    }
                }
                Request request = new Request.Builder().url(MsgUtils.Http).post(requestBody.build()).tag(MessageActivity.this).build();
                client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        UiThread(getString(R.string.network_request_failed));
                        if (mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //{"code":"success","data":"140722","error":false,"msg":"??????","success":true,"systemSwitch":0}n
                        UploadBean uploadBean = GsonUtils.getPerson(response.body().string(), UploadBean.class);
                        if (uploadBean != null) {
                            if (uploadBean.getSuccess()) {
                                UiThread("????????????" + uploadBean.getData() + " ????????????");
                                if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
                                    StringBuffer b = new StringBuffer();
                                    for (int i = 0; i < uploadBean.getData().length(); i++) {
                                        String c = String.valueOf(uploadBean.getData().charAt(i));
                                        boolean digitsOnly = TextUtils.isDigitsOnly(c);
                                        if (digitsOnly) {
                                            String s1 = c + " ";
                                            b.append(s1);
                                        } else {
                                            b.append(c);
                                        }
                                    }
                                    mTextToSpeech.speak("????????????" + b + " ????????????", TextToSpeech.QUEUE_FLUSH, null);
                                }
                            } else {
                                if (StringUtils.isEmpty(uploadBean.getMsg())) {
                                    UiThread(getString(R.string.network_request_failed));
                                } else {
                                    UiThread(uploadBean.getMsg());
                                }
                            }
                        }
                        if (mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                    }
                });
                break;
            case R.id.start_measure:
                Intent all_intent = new Intent(MessageActivity.this, StatureActivity.class);
                Bundle all_bundle = new Bundle();
                all_bundle.putBoolean("isAll", true);
                all_bundle.putBoolean("isUser", true);
                all_intent.putExtras(all_bundle);
                startActivity(all_intent);
                finish();
                break;
        }
    }

    private void UiThread(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(MessageActivity.this, msg);
            }
        });
    }

    private CountTimer countTimerView;

    public class CountTimer extends CountDownTimer {

        public CountTimer(long millisInFuture, long countDownInterval, Context context) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            SVHApplication.getInstance().SvhClick();
            startActivity(new Intent(MessageActivity.this, ADActivity.class));
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
        exit_tstv.setText(" ??????[" + count.second + "s] ");
    }

    private void CountDown() {
        countTimerView = new CountTimer(90 * 1000, 1000, MessageActivity.this);
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
        // ????????????????????????TTS????????????
        mTextToSpeech.stop();
        // ?????????????????????
        mTextToSpeech.shutdown();
    }
}