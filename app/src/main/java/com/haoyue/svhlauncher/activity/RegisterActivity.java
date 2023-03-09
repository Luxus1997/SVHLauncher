package com.haoyue.svhlauncher.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.haoyue.svhlauncher.R;
import com.haoyue.svhlauncher.SVHApplication;
import com.haoyue.svhlauncher.activity.base.IActivity;
import com.haoyue.svhlauncher.bean.RandomBean;
import com.haoyue.svhlauncher.bean.UploadBean;
import com.haoyue.svhlauncher.daobean.Register;
import com.haoyue.svhlauncher.dialog.CustomProgressDialog;
import com.haoyue.svhlauncher.eventtask.CountDownTask;
import com.haoyue.svhlauncher.gnop.GNopUtils;
import com.haoyue.svhlauncher.keybordview.LabelKeyBordView;
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
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.haoyue.svhlauncher.utils.MsgUtils.APP_KEY;
import static com.haoyue.svhlauncher.utils.MsgUtils.APP_SECRET;
import static com.haoyue.svhlauncher.utils.MsgUtils.tongue_bottom;
import static com.haoyue.svhlauncher.utils.MsgUtils.tongue_top;
import static java.lang.String.valueOf;

public class RegisterActivity extends IActivity implements View.OnClickListener {

    //退出
    TextView exit_tstv;
    //输入
    EditText register_et;
    //无号体检
    Button register_nobt;
    //键盘
    LabelKeyBordView label_keybord_view;
    //开始体检
    Button register_bt;

    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FindView();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        CountDown();
        label_keybord_view.setEt(register_et);
    }

    private void FindView() {
        exit_tstv = findViewById(R.id.exit_tstv);
        register_et = findViewById(R.id.register_et);
        register_nobt = findViewById(R.id.register_nobt);
        label_keybord_view = findViewById(R.id.label_keybord_view);
        register_bt = findViewById(R.id.register_bt);
        exit_tstv.setOnClickListener(this);
        register_nobt.setOnClickListener(this);
        register_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SVHApplication.getInstance().SvhClick();
        switch (v.getId()) {
            case R.id.exit_tstv:
                startActivity(new Intent(RegisterActivity.this, ADActivity.class));
                finish();
                break;
            case R.id.register_nobt:
                PhysicalsOperation.deleteAll();
                RegisterOperation.deleteAll();
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.register_bt:
                if (StringUtils.isEmpty(register_et.getText().toString().trim())) {
                    ToastUtils.showToast(RegisterActivity.this, getString(R.string.register_pls_code));
                    return;
                }
                if (mDialog == null) {
                    mDialog = CustomProgressDialog.createLoadingDialog(RegisterActivity.this, "用户信息查询中...");
                }
                if (!mDialog.isShowing()) {
                    DialogUtils.focusNotAle(mDialog.getWindow());
                    mDialog.show();
                    DialogUtils.hideNavigationBar(mDialog.getWindow());
                    DialogUtils.clearFocusNotAle(mDialog.getWindow());
                }
                Map<String, String> map = new HashMap<>();
                List<String> list = new ArrayList<>();
                list.add("sign");
                map.put("v", "1.0");//系统参数
                map.put("method", "device.random");//device.upload  系统参数
                map.put("appKey", APP_KEY);//系统参数
                map.put("random", register_et.getText().toString().trim());//业务参数
                String newSign = GNopUtils.sign(map, list, APP_SECRET);
                map.put("sign", newSign);//计算后签名参数

                OkHttpClient client = new OkHttpClient();
                MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
                if (map != null) {
                    for (Map.Entry entry : map.entrySet()) {
                        requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
                    }
                }
                Request request = new Request.Builder().url(MsgUtils.Http).post(requestBody.build()).tag(RegisterActivity.this).build();
                client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        UiThread(getString(R.string.network_request_failed));
                        if (mDialog.isShowing()){
                            mDialog.dismiss();
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        RandomBean randomBean = GsonUtils.getPerson(response.body().string(), RandomBean.class);
                        if (randomBean != null) {
                            if (randomBean.getSuccess()) {
                                PhysicalsOperation.deleteAll();
                                RegisterOperation.deleteAll();
                                deleteSingleFile(tongue_top);
                                deleteSingleFile(tongue_bottom);
                                //-
                                Register r = new Register();
                                r.setRecord_id(randomBean.getData().getRecord().getId());
                                r.setDevicernd(randomBean.getData().getRecord().getDeviceRnd());
                                r.setName(randomBean.getData().getPeople().getName());
                                r.setGender(randomBean.getData().getPeople().getGender() + "");
                                r.setAge(randomBean.getData().getPeople().getAge() + "");
                                r.setFormname(randomBean.getData().getRecord().getFormName());
                                //-
                                RegisterOperation.insertRegister(r);
                                Intent intent = new Intent(RegisterActivity.this, MessageActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("isFinal", false);
                                bundle.putString("height", randomBean.getData().getPeople().getHeight() + "");
                                bundle.putString("weight", randomBean.getData().getPeople().getWeight() + "");
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            } else {
                                if (StringUtils.isEmpty(randomBean.getMsg())) {
                                    UiThread(getString(R.string.network_request_failed));
                                } else {
                                    UiThread(randomBean.getMsg());
                                }
                            }
                        }
                        if (mDialog.isShowing()){
                            mDialog.dismiss();
                        }
                    }
                });
                break;
        }
    }

    /**
     * 删除单个文件
     *
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.e("--Method--", "删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {
                Log.e("--Method--", "删除单个文件" + filePath$Name + "失败！");
                return false;
            }
        } else {
            Log.e("--Method--", "删除单个文件" + filePath$Name + "不存在！");
            return false;
        }
    }

    private void UiThread(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(RegisterActivity.this, msg);
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
            startActivity(new Intent(RegisterActivity.this, ADActivity.class));
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
        countTimerView = new CountTimer(90 * 1000, 1000, RegisterActivity.this);
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