package com.cqf.hn.tool.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cqf.hn.tool.R;
import com.cqf.hn.tool.base.BaseApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Admin on 2018/3/17.
 */

public class ConfirmDialog extends Dialog {
    @BindView(R.id.tvTip)
    TextView tvTip;
    @BindView(R.id.tvSecondTip)
    TextView tvSecondTip;
    @BindView(R.id.tvCancel)
    TextView tvCancel;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;

    private View.OnClickListener cancelListener;
    private View.OnClickListener confirmListener;
    private boolean cancelable = true;
    private boolean isDismiss = true;

    public ConfirmDialog(@NonNull Context context) {
        super(context, R.style.ConfirmDialog);
        init(context);
    }

    private void init(Context context) {
        BaseApplication ac = (BaseApplication) context.getApplicationContext();
        Window w = this.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        w.setWindowAnimations(R.style.DialogAnimation);
        lp.gravity = Gravity.CENTER;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        w.setAttributes(lp);
        this.setCanceledOnTouchOutside(true);
        View contentView = View.inflate(context, R.layout.dialog_confirm, null);
        this.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        contentView.setLayoutParams(params);
        ButterKnife.bind(this, contentView);
    }

    @OnClick({R.id.tvCancel, R.id.tvConfirm})
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                if (cancelable || isDismiss) {
                    dismiss();
                }
                if (cancelListener != null) {
                    cancelListener.onClick(view);
                }
                break;
            case R.id.tvConfirm:
                if (cancelable || isDismiss) {
                    dismiss();
                }
                if (confirmListener != null) {
                    confirmListener.onClick(view);
                }
                break;
        }
    }

    public ConfirmDialog render(CharSequence tip, CharSequence confirmText, CharSequence cancelText, View.OnClickListener confirmListener, View.OnClickListener cancelListener) {
        tvConfirm.setText(confirmText);
        tvCancel.setText(cancelText);
        tvTip.setText(tip);
        this.confirmListener = confirmListener;
        this.cancelListener = cancelListener;
        return this;
    }

    public ConfirmDialog render(CharSequence messageText, View.OnClickListener confirmListener) {
        tvConfirm.setText("确认");
        tvCancel.setText("取消");
        tvTip.setText(messageText);
        this.confirmListener = confirmListener;
        this.cancelListener = null;
        return this;
    }

    public ConfirmDialog render(CharSequence messageText, CharSequence confirmText, View.OnClickListener confirmListener) {
        tvConfirm.setText(confirmText);
        tvCancel.setText("取消");
        tvTip.setText(messageText);
        this.confirmListener = confirmListener;
        this.cancelListener = null;
        return this;
    }

    public ConfirmDialog setSecondTip(String secondTip) {
        tvSecondTip.setVisibility(View.VISIBLE);
        tvSecondTip.setText(secondTip);
        return this;
    }

    public ConfirmDialog setSingleBtn() {
        tvCancel.setVisibility(View.GONE);
        tvConfirm.setBackgroundResource(R.drawable.bg_btn_dialog_single);
        return this;
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
        this.cancelable = flag;
    }

    /**
     * 禁止返回键取消Dialog，但是点击弹窗按钮可Dismiss
     *
     * @param isDismiss 是否可以Dismiss
     */
    public void setCancelableWithBtnDismiss(boolean isDismiss) {
        setCancelable(false);
        this.isDismiss = isDismiss;
    }

    public TextView getTvTip() {
        return tvTip;
    }

    public void setTvTip(TextView tvTip) {
        this.tvTip = tvTip;
    }

    public TextView getTvSecondTip() {
        return tvSecondTip;
    }

    public void setTvSecondTip(TextView tvSecondTip) {
        this.tvSecondTip = tvSecondTip;
    }

    public TextView getTvCancel() {
        return tvCancel;
    }

    public void setTvCancel(TextView tvCancel) {
        this.tvCancel = tvCancel;
    }

    public TextView getTvConfirm() {
        return tvConfirm;
    }

    public void setTvConfirm(TextView tvConfirm) {
        this.tvConfirm = tvConfirm;
    }
}
