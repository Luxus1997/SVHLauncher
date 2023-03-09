package com.haoyue.svhlauncher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoyue.svhlauncher.R;
import com.haoyue.svhlauncher.SVHApplication;
import com.haoyue.svhlauncher.activity.base.IActivity;
import com.haoyue.svhlauncher.adapter.PhysicalsAdapter;
import com.haoyue.svhlauncher.adapter.PhysicalsItemAdapter;
import com.haoyue.svhlauncher.bean.PhysicalsBean;
import com.haoyue.svhlauncher.countdowntimer.CountDownTimerSupport;
import com.haoyue.svhlauncher.countdowntimer.OnCountDownTimerListener;
import com.haoyue.svhlauncher.daobean.Physicals;
import com.haoyue.svhlauncher.eventtask.CountDownTask;
import com.haoyue.svhlauncher.operation.PhysicalsOperation;
import com.haoyue.svhlauncher.utils.DialogUtils;
import com.haoyue.svhlauncher.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.haoyue.svhlauncher.bean.PhysicalsBean.PTYPE.Pressure;
import static com.haoyue.svhlauncher.bean.PhysicalsBean.PTYPE.Stature;
import static com.haoyue.svhlauncher.bean.PhysicalsBean.PTYPE.Temp;
import static com.haoyue.svhlauncher.bean.PhysicalsBean.PhysicalsData.TYPE.HIGH;
import static com.haoyue.svhlauncher.bean.PhysicalsBean.PhysicalsData.TYPE.LOW;
import static com.haoyue.svhlauncher.bean.PhysicalsBean.PhysicalsData.TYPE.NORMAL;

public class PhysicalsActivity extends IActivity implements View.OnClickListener {

    //测量结果
    //退出
    TextView exit_tstv;
    //查看报告
    LinearLayout reload_linear;
    Button reload_msg;
    boolean isUser = false;
    //列表
    RecyclerView rvDr;
    private PhysicalsAdapter physicalsAdapter;
    //数据组
    private List<PhysicalsBean> mList = new ArrayList<>();
    //数据
    double heightCm = 0;
    double weightKg = 0;
    double htBMI = 0;
    int high_pressure = 0;
    int low_pressure = 0;
    int heart_rate = 0;
    double temp7 = 0;
    double temp8 = 0;
    double temp9 = 0;
    double temp10 = 0;
    double temp11 = 0;
    double temp12 = 0;
    //弹窗
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physicals);

        Bundle data = getIntent().getExtras();
        isUser = data.getBoolean("isUser");

        FindView();
        readData();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        CountDown();

        rvDr.setLayoutManager(new LinearLayoutManager(PhysicalsActivity.this));
        rvDr.setItemAnimator(new DefaultItemAnimator());
        (physicalsAdapter = new PhysicalsAdapter(mList)).setOnItemClickListener(new PhysicalsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int n) {
                showPhysicalsDialog(mList.get(n));
            }

            @Override
            public void onItemLongClick(View view, int n) {
            }
        });
        rvDr.setAdapter(physicalsAdapter);
    }

    private void showPhysicalsDialog(PhysicalsBean physicals) {
        AlertDialog.Builder Builder = new AlertDialog.Builder(PhysicalsActivity.this, R.style.AlertDialogCustom);
        View inflate = View.inflate(PhysicalsActivity.this, R.layout.dialog_physicals, null);

        if (StringUtils.isEmpty(physicals.getDescribe())) {
            ((TextView) inflate.findViewById(R.id.item_describe)).setText("");
        } else {
            ((TextView) inflate.findViewById(R.id.item_describe)).setText(physicals.getDescribe());
        }

        RecyclerView item_rv = inflate.findViewById(R.id.item_rv);
        item_rv.setLayoutManager(new LinearLayoutManager(PhysicalsActivity.this));
        item_rv.setItemAnimator(new DefaultItemAnimator());
        PhysicalsItemAdapter itemAdapter = new PhysicalsItemAdapter(physicals.getPhysicalsData());
        item_rv.setAdapter(itemAdapter);

        dialog = Builder.create();
        DialogUtils.focusNotAle(dialog.getWindow());
        dialog.show();
        DialogUtils.hideNavigationBar(dialog.getWindow());
        DialogUtils.clearFocusNotAle(dialog.getWindow());
        dialog.getWindow().setContentView(inflate);
        dialog.setCancelable(true);
    }

    private void readData() {
        Physicals physicals = PhysicalsOperation.queryPhysicals();
        heightCm = physicals.getHeightCm();
        weightKg = physicals.getWeightKg();
        htBMI = physicals.getHtBMI();
        high_pressure = (int) physicals.getHigh_pressure();
        low_pressure = (int) physicals.getLow_pressure();
        heart_rate = (int) physicals.getHeart_rate();
        temp7 = physicals.getTemp7();

        if (heightCm != 0 && weightKg != 0 && htBMI != 0) {
            PhysicalsBean bean = new PhysicalsBean();
            bean.setDescribe("身高体重");
            List<PhysicalsBean.PhysicalsData> physicalsData = new ArrayList<>();
            physicalsData.add(new PhysicalsBean.PhysicalsData("身高", heightCm, "-", NORMAL));
            physicalsData.add(new PhysicalsBean.PhysicalsData("体重", weightKg, (double) (IdealWeight() - 10) + "-" + (double) (IdealWeight() + 10) + "kg", BMIType(htBMI)));
            physicalsData.add(new PhysicalsBean.PhysicalsData("体质指数(BMI)", htBMI, "18.5-24.9", BMIType(htBMI)));
            physicalsData.add(new PhysicalsBean.PhysicalsData("理想体重", IdealWeight(), "-", NORMAL));
            if (IdealWeight() > weightKg) {
                double kg = IdealWeight() - weightKg;
                BigDecimal bd = new BigDecimal(kg);
                double value = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                physicalsData.add(new PhysicalsBean.PhysicalsData("体重调节", value, "-", NORMAL));
            } else {
                double kg = weightKg - IdealWeight();
                BigDecimal bd = new BigDecimal(kg);
                double value = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                physicalsData.add(new PhysicalsBean.PhysicalsData("体重调节", -value, "-", NORMAL));
            }
            bean.setPhysicalsData(physicalsData);
            bean.setProject_type(Stature);
            mList.add(bean);
        }
        if (high_pressure != 0 && low_pressure != 0 && heart_rate != 0) {
            PhysicalsBean bean = new PhysicalsBean();
            bean.setDescribe("血压心率");
            List<PhysicalsBean.PhysicalsData> physicalsData = new ArrayList<>();
            physicalsData.add(new PhysicalsBean.PhysicalsData("高压", high_pressure, "90.0-140.0mmHg", hPressureType(high_pressure)));
            physicalsData.add(new PhysicalsBean.PhysicalsData("低压", low_pressure, "60.0-90.0mmHg", lPressureType(low_pressure)));
            physicalsData.add(new PhysicalsBean.PhysicalsData("心率", heart_rate, "60.0-100.0次/分钟", heartType(heart_rate)));
            bean.setPhysicalsData(physicalsData);
            bean.setProject_type(Pressure);
            mList.add(bean);
        }
        if (temp7 != 0) {
            PhysicalsBean bean = new PhysicalsBean();
            bean.setDescribe("体温");
            List<PhysicalsBean.PhysicalsData> physicalsData = new ArrayList<>();
            physicalsData.add(new PhysicalsBean.PhysicalsData("体温", temp7, "36.0-37.3℃", TempType(temp7)));
            bean.setPhysicalsData(physicalsData);
            bean.setProject_type(Temp);
            mList.add(bean);
        }
    }

    private double IdealWeight() {
        double m = heightCm / 100;
        BigDecimal bd = new BigDecimal(21.7 * (m * m));
        double value = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return value;
    }

    private PhysicalsBean.PhysicalsData.TYPE BMIType(double bmi) {
        if (bmi <= 18.5) {
            return LOW;
        } else if (bmi > 18.5 && bmi <= 24.9) {
            return NORMAL;
        } else {
            return HIGH;
        }
    }

    private PhysicalsBean.PhysicalsData.TYPE hPressureType(int high_pressure) {
        if (high_pressure < 90) {
            return LOW;
        } else if (high_pressure >= 90 && high_pressure < 140) {
            return NORMAL;
        } else {
            return HIGH;
        }
    }

    private PhysicalsBean.PhysicalsData.TYPE lPressureType(int low_pressure) {
        if (low_pressure < 60) {
            return LOW;
        } else if (low_pressure >= 60 && low_pressure < 90) {
            return NORMAL;
        } else {
            return HIGH;
        }
    }

    private PhysicalsBean.PhysicalsData.TYPE heartType(int heart_rate) {
        if (heart_rate < 60) {
            return LOW;
        } else if (heart_rate >= 60 && heart_rate < 100) {
            return NORMAL;
        } else {
            return HIGH;
        }
    }

    private PhysicalsBean.PhysicalsData.TYPE TempType(double temp) {
        if (temp < 36.0) {
            return LOW;
        } else if (temp >= 36.0 && temp < 37.3) {
            return NORMAL;
        } else {
            return HIGH;
        }
    }

    private void FindView() {
        exit_tstv = findViewById(R.id.exit_tstv);
        rvDr = findViewById(R.id.rvDr);
        reload_linear = findViewById(R.id.reload_linear);
        reload_msg = findViewById(R.id.reload_msg);

        if (isUser) {
            reload_linear.setVisibility(View.VISIBLE);
        } else {
            reload_linear.setVisibility(View.GONE);
        }
        exit_tstv.setOnClickListener(this);
        reload_msg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SVHApplication.getInstance().SvhClick();
        switch (v.getId()) {
            case R.id.exit_tstv:
                startActivity(new Intent(PhysicalsActivity.this, ADActivity.class));
                finish();
                break;
            case R.id.reload_msg:
                Intent intent = new Intent(PhysicalsActivity.this, MessageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isFinal", true);
                bundle.putString("height", null);
                bundle.putString("weight", null);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
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
                SVHApplication.getInstance().SvhClick();
                startActivity(new Intent(PhysicalsActivity.this, ADActivity.class));
                finish();
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
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}