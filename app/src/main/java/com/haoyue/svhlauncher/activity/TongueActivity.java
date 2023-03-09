package com.haoyue.svhlauncher.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.haoyue.svhlauncher.R;
import com.haoyue.svhlauncher.SVHApplication;
import com.haoyue.svhlauncher.activity.base.IActivity;
import com.haoyue.svhlauncher.camera.Camera1Preview;
import com.haoyue.svhlauncher.camera.CameraView;
import com.haoyue.svhlauncher.eventtask.CountDownTask;
import com.haoyue.svhlauncher.utils.DeviceUtils;
import com.haoyue.svhlauncher.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import static com.haoyue.svhlauncher.utils.MsgUtils.tongue_bottom;
import static com.haoyue.svhlauncher.utils.MsgUtils.tongue_top;

public class TongueActivity extends IActivity implements View.OnClickListener {

    //退出
    TextView exit_tstv;
    //预览
    private CameraView mCameraPreview;
    private FrameLayout parentView;
    //舌苔正面
    ImageView tongue_top_img;
    Button tongue_top_btn;

    //舌苔底部
    ImageView tongue_bottom_img;
    Button tongue_bottom_btn;

    //下一项
    Button bt_next;
    //-
    boolean isTop = false;
    boolean isBottom = false;
    //是否全部测量
    boolean isAll = false;
    boolean isUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongue);

        Bundle data = getIntent().getExtras();
        isAll = data.getBoolean("isAll");
        isUser = data.getBoolean("isUser");
        if (!DeviceUtils.checkCameraHardware(this)) {
            ToastUtils.showToast(this, "当前设备无法支持相机！");
            startActivity(new Intent(TongueActivity.this, ADActivity.class));
            finish();
        }

        deleteSingleFile(tongue_top);
        deleteSingleFile(tongue_bottom);
        isTop = false;
        isBottom = false;
        FindView();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        CountDown();
    }

    private void FindView() {
        exit_tstv = findViewById(R.id.exit_tstv);
        parentView = findViewById(R.id.camera_preview);
        tongue_top_img = findViewById(R.id.tongue_top_img);
        tongue_top_btn = findViewById(R.id.tongue_top_btn);
        tongue_bottom_img = findViewById(R.id.tongue_bottom_img);
        tongue_bottom_btn = findViewById(R.id.tongue_bottom_btn);
        bt_next = findViewById(R.id.bt_next);

        exit_tstv.setOnClickListener(this);
        tongue_top_btn.setOnClickListener(this);
        tongue_bottom_btn.setOnClickListener(this);
        bt_next.setOnClickListener(this);

        mCameraPreview = new Camera1Preview(this);
        parentView.addView((View) mCameraPreview);
        mCameraPreview.setPictureSavePath(tongue_top);
    }

    @Override
    public void onClick(View v) {
        SVHApplication.getInstance().SvhClick();
        switch (v.getId()) {
            case R.id.exit_tstv:
                startActivity(new Intent(TongueActivity.this, ADActivity.class));
                finish();
                break;
            case R.id.tongue_top_btn:
                deleteSingleFile(tongue_top);
                mCameraPreview.setPictureSavePath(tongue_top);
                mCameraPreview.takePicture(new CameraView.TakePictureCallback() {
                    @Override
                    public void success(String picturePath) {
                        //ToastUtils.showToast(TongueActivity.this, "图片保存地址：" + picturePath);
                        Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                        RequestOptions options = new RequestOptions();
                        options.skipMemoryCache(true);//跳过内存缓存
                        options.diskCacheStrategy(DiskCacheStrategy.NONE);//不缓冲disk硬盘中
                        Glide.with(TongueActivity.this).load(bitmap).apply(options).into(tongue_top_img);
                        tongue_top_btn.setText("重新拍照");
                        isTop = true;
                        if (isTop && isBottom){
                            bt_next.setVisibility(View.VISIBLE);
                        }
                        ToastUtils.showToast(TongueActivity.this, "舌苔正面拍照成功");
                    }

                    @Override
                    public void error(String error) {
                        ToastUtils.showToast(TongueActivity.this, "错误：" + error);
                    }
                });
                break;
            case R.id.tongue_bottom_btn:
                deleteSingleFile(tongue_bottom);
                mCameraPreview.setPictureSavePath(tongue_bottom);
                mCameraPreview.takePicture(new CameraView.TakePictureCallback() {
                    @Override
                    public void success(String picturePath) {
                        //ToastUtils.showToast(TongueActivity.this, "图片保存地址：" + picturePath);
                        Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                        RequestOptions options = new RequestOptions();
                        options.skipMemoryCache(true);//跳过内存缓存
                        options.diskCacheStrategy(DiskCacheStrategy.NONE);//不缓冲disk硬盘中
                        Glide.with(TongueActivity.this).load(bitmap).apply(options).into(tongue_bottom_img);
                        tongue_bottom_btn.setText("重新拍照");
                        isBottom = true;
                        if (isTop && isBottom){
                            bt_next.setVisibility(View.VISIBLE);
                        }
                        ToastUtils.showToast(TongueActivity.this, "舌苔底部拍照成功");
                    }

                    @Override
                    public void error(String error) {
                        ToastUtils.showToast(TongueActivity.this, "错误：" + error);
                    }
                });
                break;
            case R.id.bt_next:
                Intent all_intent = new Intent(TongueActivity.this, TempActivity.class);
                Bundle all_bundle = new Bundle();
                all_bundle.putBoolean("isAll", isAll);
                all_bundle.putBoolean("isUser", isUser);
                all_intent.putExtras(all_bundle);
                startActivity(all_intent);
                finish();
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

    private CountTimer countTimerView;

    public class CountTimer extends CountDownTimer {

        public CountTimer(long millisInFuture, long countDownInterval, Context context) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            SVHApplication.getInstance().SvhClick();
            startActivity(new Intent(TongueActivity.this, ADActivity.class));
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
        countTimerView = new CountTimer(90 * 1000, 1000, TongueActivity.this);
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
        if (mCameraPreview != null) {
            mCameraPreview.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        timeStart();
        if (mCameraPreview != null) {
            mCameraPreview.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}