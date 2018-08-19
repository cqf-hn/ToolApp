package com.cqf.hn.tool.base;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.cqf.hn.tool.R;
import com.cqf.hn.tool.util.ActivityManager;
import com.cqf.hn.tool.util.SystemBarTintManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author cqf
 * @time 2018/5/15 0015  上午 9:24
 * @desc ${TODD}
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;
    protected BaseActivity _activity;
    private AlertDialog loadDialog;

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getAppManager().addActivity(this);
        _activity = this;
        onLayoutBefore();
        setContentView(onLayoutId());
        unbinder = ButterKnife.bind(this);
        initStatusBar();
        onLayoutAfter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getAppManager().removeActivity(this);
        unbinder.unbind();
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.white_half);
            setTranslucentStatus(true);
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    protected void onLayoutBefore() {
    }

    @LayoutRes
    protected abstract int onLayoutId();

    protected void onLayoutAfter() {
    }

    /**
     * @param cancelable 点击返回或屏幕是否取消 true为可取消，false为不可取消
     *                   显示加载
     */
    public void showLoading(final boolean cancelable, final DialogOnKeyListener listener) {
        if (loadDialog == null) {
            loadDialog = new AlertDialog.Builder(this, R.style.mydialog).setView(R.layout.view_loading_dialog).create();
        }
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        loadDialog.setCancelable(false);
        loadDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (listener != null) {
                    listener.onKey(dialog, keyCode, event, cancelable);
                }
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (cancelable) {
                        dialog.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
        loadDialog.show();
    }

    /**
     * 关闭加载
     */
    public void hideLoading() {
        if (loadDialog != null) {
            loadDialog.dismiss();
        }
    }

    public interface DialogOnKeyListener {
        void onKey(DialogInterface dialog, int keyCode, KeyEvent event, boolean cancelable);
    }
}
