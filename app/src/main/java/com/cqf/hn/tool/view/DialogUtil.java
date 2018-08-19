package com.cqf.hn.tool.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cqf.hn.tool.R;


/**
 * Created by：su on 2016/8/7 10:32
 * 619389279@qq.com
 */
public class DialogUtil {
    /**
     * @param context
     * @param
     * @param view    底部弹出dialog
     * @return
     */
    public static Dialog ActionSheetDialog(Context context, boolean cancelable, View view) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        view.setMinimumWidth(display.getWidth());
        Dialog alertDialog = new Dialog(context, android.support.design.R.style.Theme_Design_Light_BottomSheetDialog);
        alertDialog.setContentView(view);
        Window dialogWindow = alertDialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM); //设置dialog的显示位置在屏幕底部
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.windowAnimations = R.style.DialogAnimation;
        lp.x = 50;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        alertDialog.setCancelable(cancelable);
        alertDialog.show();
        //alertDialog.setCanceledOnTouchOutside(cancelable);
        return alertDialog;
    }

    public static BottomSheetDialog bottomSheetDialog(Activity context, boolean Cancelable, View view) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCancelable(Cancelable);
        bottomSheetDialog.setCanceledOnTouchOutside(Cancelable);
        bottomSheetDialog.show();
        return bottomSheetDialog;
    }

    public static AlertDialog centerDialog(Activity mContext, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        if (!TextUtils.isEmpty(title)) {
            alertDialog.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            alertDialog.setMessage(message);
        }
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
        return alertDialog;
    }

}
