package com.haoyue.svhlauncher.keybordview;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.haoyue.svhlauncher.R;
import com.haoyue.svhlauncher.SVHApplication;

import java.lang.reflect.Method;

public class LabelKeyBordView extends RelativeLayout implements View.OnClickListener{

    private EditText et = null;
    private Context mContext = null;

    public LabelKeyBordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LabelKeyBordView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.mContext = getContext();
        this.inflate(mContext, R.layout.lable_item_keybord, this);

        findViewById(R.id.label_keybord_btn_0).setOnClickListener(this);
        findViewById(R.id.label_keybord_btn_1).setOnClickListener(this);
        findViewById(R.id.label_keybord_btn_2).setOnClickListener(this);
        findViewById(R.id.label_keybord_btn_3).setOnClickListener(this);
        findViewById(R.id.label_keybord_btn_4).setOnClickListener(this);
        findViewById(R.id.label_keybord_btn_5).setOnClickListener(this);
        findViewById(R.id.label_keybord_btn_6).setOnClickListener(this);
        findViewById(R.id.label_keybord_btn_7).setOnClickListener(this);
        findViewById(R.id.label_keybord_btn_8).setOnClickListener(this);
        findViewById(R.id.label_keybord_btn_9).setOnClickListener(this);
        findViewById(R.id.label_keybord_btn_delete).setOnClickListener(this);
        findViewById(R.id.label_keybord_btn_clear).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SVHApplication.getInstance().SvhClick();
        switch (v.getId()) {
            case R.id.label_keybord_btn_0:
                setStringBufferValue("0");
                break;
            case R.id.label_keybord_btn_1:
                setStringBufferValue("1");
                break;
            case R.id.label_keybord_btn_2:
                setStringBufferValue("2");
                break;
            case R.id.label_keybord_btn_3:
                setStringBufferValue("3");
                break;
            case R.id.label_keybord_btn_4:
                setStringBufferValue("4");
                break;
            case R.id.label_keybord_btn_5:
                setStringBufferValue("5");
                break;
            case R.id.label_keybord_btn_6:
                setStringBufferValue("6");
                break;
            case R.id.label_keybord_btn_7:
                setStringBufferValue("7");
                break;
            case R.id.label_keybord_btn_8:
                setStringBufferValue("8");
                break;
            case R.id.label_keybord_btn_9:
                setStringBufferValue("9");
                break;
            case R.id.label_keybord_btn_delete:
                deleteBufferText();
                break;
            case R.id.label_keybord_btn_clear:
                et.setText("");
                break;
            default:
                break;
        }
    }

    public void setEt(EditText et) {
        this.et = et;
        String str = et.getText().toString();
        et.setSelection(TextUtils.isEmpty(str) ? 0 : str.length());
        disableShowInput(et);
    }

    //值得录入
    private void setStringBufferValue(String value) {
        String buffer = et.getText().toString().trim();
        if (buffer.length() < 20) {
            et.getText().insert(et.getSelectionStart(), value);
        }
    }
    private void setEtText(String txt) {
        this.et.setText(txt);
    }
    //删除
    private void deleteBufferText() {
        Editable editable = et.getText();
        int start = et.getSelectionStart();
        if (!TextUtils.isEmpty(editable)) {
            if (start > 0) {
                editable.delete(start - 1, start);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {//取消注册
        super.onDetachedFromWindow();
        mContext = null;
    }

    /**
     * 禁用系统键盘
     */
    private void disableShowInput(EditText editText) {
        if (Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }
}