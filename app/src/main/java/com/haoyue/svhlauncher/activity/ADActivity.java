package com.haoyue.svhlauncher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;

import com.haoyue.svhlauncher.R;
import com.haoyue.svhlauncher.SVHApplication;
import com.haoyue.svhlauncher.activity.base.IActivity;
import com.haoyue.svhlauncher.utils.SvhVolume;
import com.haoyue.svhlauncher.utils.ToastUtils;

import java.util.Locale;

public class ADActivity extends IActivity implements TextToSpeech.OnInitListener{

    ImageView ad_imgv;
    //wifi状态
    ImageView iv_wifi_state;

    private SvhVolume svhVolume;
    // TTS对象
    private TextToSpeech mTextToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        FindView();
        initTextToSpeech();


        (svhVolume = SVHApplication.getInstance().getSvhVolume()).setTouchVolume(true);
        svhVolume.setSystemVolume(1.0f);

        ad_imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SVHApplication.getInstance().SvhClick();
                startActivity(new Intent(ADActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    private void FindView() {
        ad_imgv = findViewById(R.id.ad_imgv);
        iv_wifi_state = findViewById(R.id.iv_wifi_state);
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
            if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
                mTextToSpeech.speak(getString(R.string.welcome), TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 不管是否正在朗读TTS都被打断
        mTextToSpeech.stop();
        // 关闭，释放资源
        mTextToSpeech.shutdown();
    }

    @Override
    protected void onDestroy() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
            mTextToSpeech = null;
        }
        super.onDestroy();
    }

}