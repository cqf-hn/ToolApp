package com.cqf.hn.tool.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.ButterKnife;

public abstract class BaseHolder implements LayoutCallback, Serializable, View.OnAttachStateChangeListener {
    private InstanceStateCallback instanceStateCallback;
    protected BaseActivity _activity;
    protected Intent _intent;
    protected Bundle _Bundle;
    protected BaseApplication ac;
    protected View root;
    private ArrayList<String> apiTags = new ArrayList<>();
    private boolean hasShowLoadDialog = true;
    private boolean defaultHandleApiCallBack = true;

    public BaseHolder(Context context) {
        this._activity = (BaseActivity) context;
        this._intent = _activity.getIntent();
        if (this._intent != null) {
            this._Bundle = _intent.getExtras();
        }
        ac = (BaseApplication) context.getApplicationContext();
        initView();
    }

    public BaseHolder(Context context, ViewGroup parent) {
        this(context);
        parent.addView(getRoot(), onGetAddViewIndex(parent));
    }

    @Override
    public void onViewAttachedToWindow(View v) {

    }

    @Override
    public void onViewDetachedFromWindow(View v) {

    }


    protected void toggleHasShowLoadDialog(boolean hasShowLoadDialog) {
        this.hasShowLoadDialog = hasShowLoadDialog;
    }

    protected void toggleDefaultHandleApiCallBack(boolean defaultHandleApiCallBack) {
        this.defaultHandleApiCallBack = defaultHandleApiCallBack;
    }

    private void initView() {
        onLayoutBefore();
        root = View.inflate(_activity, onLayoutId(), null);
        root.addOnAttachStateChangeListener(this);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            addOnWindowFocusChangeListener();
        }
        ButterKnife.bind(this, root);
        root.setLayoutParams(onGetLayoutParams());
        onLayoutAfter();
    }

    public <E extends View> E findView(int id) {
        return (E) root.findViewById(id);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void addOnWindowFocusChangeListener() {
        root.getViewTreeObserver().addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
            @Override
            public void onWindowFocusChanged(boolean hasFocus) {
                BaseHolder.this.onWindowFocusChanged(hasFocus);
            }
        });
    }

    protected int onGetAddViewIndex(ViewGroup parent) {
        return parent.getChildCount();
    }

    public View getRoot() {
        return root;
    }

    public final void removeSelf() {
        if (root != null) {
            ViewParent parent = root.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parent;
                viewGroup.removeView(root);
            }
        }
    }

    @Override
    public abstract int onLayoutId();

    @Override
    public void onLayoutBefore() {

    }

    @Override
    public void onLayoutAfter() {

    }

    protected ViewGroup.LayoutParams onGetLayoutParams() {
        return new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    public void onWindowFocusChanged(boolean hasFocus) {

    }
}
