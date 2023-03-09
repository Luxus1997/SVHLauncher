package com.haoyue.svhlauncher.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoyue.svhlauncher.R;

public class CustomProgressDialog {

    public static Dialog createLoadingDialog(Context context, String text) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.dialog_view);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.img);
        TextView textView = (TextView) inflate.findViewById(R.id.tipTextView);
        imageView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.load_animation));
        textView.setText(text);
        Dialog dialog = new Dialog(context, R.style.loading_dialog);
        dialog.setCancelable(false);
        WindowManager.LayoutParams LayoutParams = new WindowManager.LayoutParams(-1, -1);
        LayoutParams.type = 2002;
        dialog.setContentView(linearLayout, LayoutParams);
        return dialog;
    }

}