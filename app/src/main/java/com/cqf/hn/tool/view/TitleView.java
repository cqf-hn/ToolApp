package com.cqf.hn.tool.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqf.hn.tool.R;
import com.cqf.hn.tool.util.TDevice;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Admin on 2018/3/28.
 */

public class TitleView extends FrameLayout implements View.OnAttachStateChangeListener {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivTitleLeftIcon)
    ImageView ivTitleLeftIcon;
    @BindView(R.id.ivTitleRightIcon)
    ImageView ivTitleRightIcon;
    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.ivSecondLeft)
    ImageView ivSecondLeft;
    @BindView(R.id.ivRight)
    ImageView ivRight;
    @BindView(R.id.ivSecondRight)
    ImageView ivSecondRight;
    @BindView(R.id.tvLeft)
    TextView tvLeft;
    @BindView(R.id.tvSecondLeft)
    TextView tvSecondLeft;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.tvSecondRight)
    TextView tvSecondRight;
    @BindView(R.id.root)
    RelativeLayout root;
    private Unbinder unbinder;

    public TitleView(@NonNull Context context) {
        this(context, null);
    }

    public TitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        View view = LayoutInflater.from(context).inflate(R.layout.title_view, this, true);
        unbinder = ButterKnife.bind(view);
        view.addOnAttachStateChangeListener(this);
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LayoutParams rootParams = (LayoutParams) root.getLayoutParams();
            rootParams.gravity = Gravity.BOTTOM;
            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = TDevice.getStatusBarHeight() + getResources().getDimensionPixelSize(R.dimen.title_view_height);
            requestLayout();
        }
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        unbinder.unbind();
    }

    private void setTxt(TextView tv, String title) {
        tv.setVisibility(VISIBLE);
        tv.setText(title);
    }

    private void setTxt(TextView tv, String title, int textColor) {
        tv.setVisibility(VISIBLE);
        tv.setText(title);
        tv.setTextColor(textColor);
    }

    private void setImage(ImageView iv, int resId) {
        if (resId == 0) {
            iv.setVisibility(GONE);
            return;
        }
        iv.setVisibility(VISIBLE);
        iv.setImageResource(resId);
    }

    private void setImage(ImageView iv, int resId, OnClickListener listener) {
        if (resId == 0) {
            iv.setVisibility(GONE);
            return;
        }
        iv.setVisibility(VISIBLE);
        iv.setImageResource(resId);
        iv.setOnClickListener(listener);
    }

    private void showState(View view, boolean isShow) {
        view.setVisibility(isShow ? VISIBLE : GONE);
    }

    private void enableState(View view, boolean enable) {
        view.setEnabled(enable);
    }

    public TitleView setTitle(String title) {
        setTxt(tvTitle, title);
        return this;
    }

    public TitleView setTitle(String title, int textColor) {
        setTxt(tvTitle, title, textColor);
        return this;
    }

    public TitleView setTitle(String title, boolean isDefaultBack) {
        setTitle(title);
        if (isDefaultBack) {
            setDefaultLeft("返回", R.mipmap.ic_arrow_back);
        }
        return this;
    }

    public TitleView setTitle(String title, boolean isDefaultBack, boolean isDefaultBackText) {
        setTitle(title);
        if (isDefaultBack) {
            if (isDefaultBackText) {
                setDefaultLeft("返回", R.mipmap.ic_arrow_back);
            } else {
                setImage(ivLeft, R.mipmap.ic_arrow_back, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Activity) getContext()).finish();
                    }
                });
            }
        }
        return this;
    }

    public TitleView setTitle(String title, int textColor, boolean isDefaultBack) {
        setTitle(title, textColor);
        if (isDefaultBack) {
            setDefaultLeft("返回", R.mipmap.ic_arrow_back);
        }
        return this;
    }

    public TitleView setLeftTxt(String txt) {
        setTxt(tvLeft, txt);
        return this;
    }

    public TitleView setLeftTxt(String txt, int drawableLeft) {
        setTxt(tvLeft, txt);
        tvLeft.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, 0, 0, 0);
        return this;
    }

    public TitleView setLeftTxtClick(OnClickListener listener) {
        tvLeft.setOnClickListener(listener);
        return this;
    }

    public TitleView setDefaultLeft(String txt, int drawableLeft) {
        setLeftTxt(txt, drawableLeft);
        setLeftTxtClick(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
        return this;
    }

    public TitleView setSecondLeftTxt(String txt) {
        if (tvLeft.getVisibility() == VISIBLE) {
            setTxt(tvSecondLeft, txt);
        }
        return this;
    }

    public TitleView setSecondLeftTxt(String txt, OnClickListener listener) {
        if (tvLeft.getVisibility() == VISIBLE) {
            setTxt(tvSecondLeft, txt);
            tvSecondLeft.setOnClickListener(listener);
        }
        return this;
    }

    public TitleView setRightTxt(String txt) {
        setTxt(tvRight, txt);
        return this;
    }

    public TitleView setRightTxt(String txt, int drawableRight) {
        setTxt(tvRight, txt);
        tvRight.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableRight, 0);
        return this;
    }

    public TitleView setRightTxtClick(OnClickListener listener) {
        tvRight.setOnClickListener(listener);
        return this;
    }

    public TitleView setDefaultRight(String txt, int drawableRight, OnClickListener listener) {
        setRightTxt(txt, drawableRight);
        setRightTxtClick(listener);
        return this;
    }

    public TitleView setSecondRightTxt(String txt) {
        if (tvRight.getVisibility() == VISIBLE) {
            setTxt(tvSecondRight, txt);
        }
        return this;
    }

    public TitleView setSecondRightTxt(String txt, OnClickListener listener) {
        if (tvRight.getVisibility() == VISIBLE) {
            setTxt(tvSecondRight, txt);
            tvSecondRight.setOnClickListener(listener);
        }
        return this;
    }

    public TitleView setTitleLeftIcon(int leftIcon) {
        if (tvTitle.getVisibility() == VISIBLE) {
            setImage(ivTitleLeftIcon, leftIcon);
        }
        return this;
    }

    public TitleView setTitleRightIcon(int rightIcon) {
        if (tvTitle.getVisibility() == VISIBLE) {
            setImage(ivTitleRightIcon, rightIcon);
        }
        return this;
    }

    public TitleView setIvLeft(int leftIcon) {
        setImage(ivLeft, leftIcon);
        return this;
    }

    public TitleView setIvLeft(int leftIcon, OnClickListener listener) {
        setImage(ivLeft, leftIcon, listener);
        return this;
    }

    public TitleView setIvSecondLeft(int secondLeftIcon) {
        if (ivLeft.getVisibility() == VISIBLE) {
            setImage(ivSecondLeft, secondLeftIcon);
        }
        return this;
    }

    public TitleView setIvSecondLeft(int secondLeftIcon, OnClickListener listener) {
        if (ivLeft.getVisibility() == VISIBLE) {
            setImage(ivSecondLeft, secondLeftIcon, listener);
        }
        return this;
    }

    public TitleView setIvRight(int rightIcon) {
        setImage(ivRight, rightIcon);
        return this;
    }

    public TitleView setIvRight(int rightIcon, OnClickListener listener) {
        setImage(ivRight, rightIcon, listener);
        return this;
    }

    public TitleView setIvSecondRight(int secondRightIcon) {
        if (ivRight.getVisibility() == VISIBLE) {
            setImage(ivSecondRight, secondRightIcon);
        }
        return this;
    }

    public TitleView setIvSecondRight(int secondRightIcon, OnClickListener listener) {
        if (ivRight.getVisibility() == VISIBLE) {
            setImage(ivSecondRight, secondRightIcon, listener);
        }
        return this;
    }

    public void toggleVisibleState(@ViewType int viewType, boolean isShow) {
        showState(getView(viewType), isShow);
    }

    public void toggleEnableState(@ViewType int viewType, boolean isShow) {
        enableState(getView(viewType), isShow);
    }

    public void setBgColor(int color) {
        root.setBackgroundColor(color);
    }

    public void setBgRes(int resId) {
        root.setBackgroundResource(resId);
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public ImageView getIvTitleLeftIcon() {
        return ivTitleLeftIcon;
    }

    public ImageView getIvTitleRightIcon() {
        return ivTitleRightIcon;
    }

    public ImageView getIvLeft() {
        return ivLeft;
    }

    public ImageView getIvSecondLeft() {
        return ivSecondLeft;
    }

    public ImageView getIvRight() {
        return ivRight;
    }

    public ImageView getIvSecondRight() {
        return ivSecondRight;
    }

    public TextView getTvLeft() {
        return tvLeft;
    }

    public TextView getTvSecondLeft() {
        return tvSecondLeft;
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public TextView getTvSecondRight() {
        return tvSecondRight;
    }

    private View getView(int viewType) {
        switch (viewType) {
            case ViewType.TITLE:
                return tvTitle;
            case ViewType.TITLE_LEFT_ICON:
                return ivTitleLeftIcon;
            case ViewType.TITLE_RIGHT_ICON:
                return ivTitleRightIcon;
            case ViewType.IV_LEFT:
                return ivLeft;
            case ViewType.IV_RIGHT:
                return ivRight;
            case ViewType.IV_SECOND_LEFT:
                return ivSecondLeft;
            case ViewType.IV_SECOND_RIGHT:
                return ivSecondRight;
            case ViewType.TV_LEFT:
                return tvLeft;
            case ViewType.TV_RIGHT:
                return tvRight;
            case ViewType.TV_SECOND_LEFT:
                return tvSecondLeft;
            case ViewType.TV_SECOND_RIGHT:
                return tvSecondRight;
        }
        return root;
    }

    @IntDef({ViewType.TITLE,
            ViewType.TITLE_LEFT_ICON,
            ViewType.TITLE_RIGHT_ICON,
            ViewType.IV_LEFT,
            ViewType.IV_RIGHT,
            ViewType.IV_SECOND_LEFT,
            ViewType.IV_SECOND_RIGHT,
            ViewType.TV_LEFT,
            ViewType.TV_RIGHT,
            ViewType.TV_SECOND_LEFT,
            ViewType.TV_SECOND_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
        int TITLE = 0;
        int TITLE_LEFT_ICON = 1;
        int TITLE_RIGHT_ICON = 2;
        int IV_LEFT = 3;
        int IV_RIGHT = 4;
        int IV_SECOND_LEFT = 5;
        int IV_SECOND_RIGHT = 6;
        int TV_LEFT = 7;
        int TV_RIGHT = 8;
        int TV_SECOND_LEFT = 9;
        int TV_SECOND_RIGHT = 10;
    }
}
