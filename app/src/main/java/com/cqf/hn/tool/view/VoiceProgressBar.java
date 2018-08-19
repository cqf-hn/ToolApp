package com.cqf.hn.tool.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;

import com.cqf.hn.tool.R;
import com.cqf.hn.tool.util.ImageUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Hezihao on 2016/11/28.
 */

public class VoiceProgressBar extends android.support.v7.widget.AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener {
    public static final int STROKE = 0;
    public static final int FILL = 1;
    ObjectAnimator animator = ObjectAnimator.ofInt(this, "degree", 0, 360);
    /**
     * 画笔对象的引用
     */
    private Paint paint;
    private Paint statePaint;
    private Path path;
    /**
     * 圆环的颜色
     */
    private int roundColor;
    /**
     * 圆环进度的颜色
     */
    private int roundProgressColor;
    /**
     * 圆环的宽度
     */
    private float roundWidth;
    /**
     * 最大进度
     */
    private int max = 100;
    /**
     * 当前进度
     */
    private int progress;
    /**
     * 进度的风格，实心或者空心
     */
    private int style;
    private RectF oval;
    private int state;
    private int lastState = -1;
    private float stateWidth;
    private float stateHeight;
    private float stateLineWidth;
    private int stateColor;
    private int progressBgColor;
    private Drawable startDrawable;
    private Drawable pauseDrawable;
    private Drawable loadingDrawable;
    private Drawable downLoadDrawable;
    private Drawable upLoadDrawable;
    private SweepGradient gradient;
    private int degree;
    private Bitmap startBitmap;
    private Bitmap pauseBitmap;
    private Bitmap loadingBitmap;
    private Bitmap downLoadBitmap;
    private Bitmap upLoadBitmap;

    public VoiceProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public VoiceProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public VoiceProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        statePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        oval = new RectF();

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.VoiceProgressBar);
        //获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.VoiceProgressBar_roundColor, Color.TRANSPARENT);
        roundProgressColor = mTypedArray.getColor(R.styleable.VoiceProgressBar_roundProgressColor, Color.GREEN);
        progressBgColor = mTypedArray.getColor(R.styleable.VoiceProgressBar_progressBgColor, Color.TRANSPARENT);
        roundWidth = mTypedArray.getDimension(R.styleable.VoiceProgressBar_roundWidth, 5);
        max = mTypedArray.getInteger(R.styleable.VoiceProgressBar_max, 100);
        style = mTypedArray.getInt(R.styleable.VoiceProgressBar_style, 0);
        state = mTypedArray.getInt(R.styleable.VoiceProgressBar_stateType, 0);
        stateWidth = mTypedArray.getDimension(R.styleable.VoiceProgressBar_stateWidth, 0);
        stateHeight = mTypedArray.getDimension(R.styleable.VoiceProgressBar_stateHeight, 0);
        stateLineWidth = mTypedArray.getDimension(R.styleable.VoiceProgressBar_stateLineWidth, 5);
        stateColor = mTypedArray.getColor(R.styleable.VoiceProgressBar_stateColor, 0);
        startDrawable = mTypedArray.getDrawable(R.styleable.VoiceProgressBar_start);
        pauseDrawable = mTypedArray.getDrawable(R.styleable.VoiceProgressBar_pause);
        loadingDrawable = mTypedArray.getDrawable(R.styleable.VoiceProgressBar_loading);
        downLoadDrawable = mTypedArray.getDrawable(R.styleable.VoiceProgressBar_downLoad);
        upLoadDrawable = mTypedArray.getDrawable(R.styleable.VoiceProgressBar_upLoad);
        mTypedArray.recycle();

        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 画最外层的大圆环
         */
        int centre = Math.min(getWidth(), getHeight()) / 2; //获取圆心的x坐标
        float stateCentre = Math.min(stateWidth, stateHeight) / 2;
        float radius = centre - roundWidth / 2; //圆环的半径
        paint.setColor(progressBgColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centre, centre, radius, paint);

        paint.setColor(roundColor); //设置圆环的颜色
        paint.setStyle(Paint.Style.STROKE); //设置空心
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawCircle(centre, centre, radius, paint); //画出圆环

        /**
         * 画圆弧 ，画圆环的进度
         */
        //设置进度是实心还是空心
        paint.setColor(roundProgressColor);  //设置进度的颜色
        //用于定义的圆弧的形状和大小的界限
        oval.set(centre - radius, centre - radius, centre + radius, centre + radius);

        switch (style) {
            case STROKE: {
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(oval, -90, 360f * progress / max, false, paint);  //根据进度画圆弧
                break;
            }
            case FILL: {
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (progress != 0)
                    canvas.drawArc(oval, -90, 360f * progress / max, true, paint);  //根据进度画圆弧
                break;
            }
        }
        statePaint.reset();
        statePaint.setStrokeCap(Paint.Cap.ROUND);
        switch (state) {
            case State.NONE:
                animator.cancel();
                break;
            case State.START:
                animator.cancel();
                if (startBitmap != null) {
                    canvas.drawBitmap(startBitmap, centre - startBitmap.getWidth() / 2, centre - startBitmap.getHeight() / 2, statePaint);
                } else {
                    //绘制默认的start
                    float lineWidth = stateLineWidth * 2;
                    if (stateWidth > lineWidth * 2) {
                        float spaceValue;
                        if (stateWidth > lineWidth * 3) {
                            spaceValue = lineWidth;
                        } else {
                            spaceValue = stateWidth - lineWidth * 2;
                        }
                        statePaint.setColor(stateColor);
                        statePaint.setStyle(Paint.Style.STROKE);
                        statePaint.setStrokeCap(Paint.Cap.BUTT);
                        statePaint.setStrokeWidth(lineWidth);
                        //centre = (int) (centre - lineWidth / 2);
                        float halfLineHeight = lineWidth + spaceValue / 2;
                        canvas.drawLine(centre - spaceValue, centre - halfLineHeight, centre - spaceValue, centre + halfLineHeight, statePaint);
                        canvas.drawLine(centre + spaceValue, centre - halfLineHeight, centre + spaceValue, centre + halfLineHeight, statePaint);
                    }
                }
                break;
            case State.PAUSE:
                animator.cancel();
                if (pauseBitmap != null) {
                    canvas.drawBitmap(pauseBitmap, centre - pauseBitmap.getWidth() / 2, centre - pauseBitmap.getHeight() / 2, statePaint);
                } else {
                    //绘制默认的pause
                    statePaint.setColor(stateColor);
                    statePaint.setStyle(Paint.Style.FILL);
                    statePaint.setStrokeWidth(stateLineWidth);
                    statePaint.setStrokeJoin(Paint.Join.ROUND);
//                    float dy = (float) (Math.sin(Math.toDegrees(60)) * stateCentre);//等边三角形
//                    float dx = (float) (Math.cos(Math.toDegrees(60)) * stateCentre);//等边三角形
                    float dy = stateHeight / 2;
                    float dx = stateWidth / 2;
                    path.moveTo(centre - dx, centre - dy);
                    path.lineTo(centre - dx, centre + dy);
                    path.lineTo(centre + stateCentre, centre);
                    path.close();

                    canvas.drawPath(path, statePaint);
                }
                break;
            case State.UP_LOAD:
                animator.cancel();
                if (upLoadBitmap != null) {
                    canvas.drawBitmap(upLoadBitmap, centre - upLoadBitmap.getWidth() / 2, centre - upLoadBitmap.getHeight() / 2, statePaint);
                } else {
                    //绘制默认的upLoad
                    if (stateHeight > 0) {
                        statePaint.setColor(stateColor);
                        statePaint.setStyle(Paint.Style.STROKE);
                        statePaint.setStrokeWidth(stateLineWidth);
                        float halfStateHeight = stateHeight / 2;
                        float dy = (float) (halfStateHeight * Math.sin(Math.toDegrees(60)));
                        float dx = (float) (halfStateHeight * Math.cos(Math.toDegrees(60)));
                        canvas.drawLine(centre, centre - halfStateHeight, centre, centre + halfStateHeight, statePaint);
                        canvas.drawLine(centre, centre - halfStateHeight, centre + dx, centre - halfStateHeight + dy, statePaint);
                        canvas.drawLine(centre, centre - halfStateHeight, centre - dx, centre - halfStateHeight + dy, statePaint);
                    }
                }
                break;
            case State.DOWN_LOAD:
                animator.cancel();
                if (downLoadBitmap != null) {
                    canvas.drawBitmap(downLoadBitmap, centre - downLoadBitmap.getWidth() / 2, centre - downLoadBitmap.getHeight() / 2, statePaint);
                } else {
                    //绘制默认的downLoad
                    if (stateHeight > 0) {
                        statePaint.setColor(stateColor);
                        statePaint.setStyle(Paint.Style.STROKE);
                        statePaint.setStrokeWidth(stateLineWidth);
                        float halfStateHeight = stateHeight / 2;
                        float dy = (float) (halfStateHeight * Math.sin(Math.toDegrees(60)));
                        float dx = (float) (halfStateHeight * Math.cos(Math.toDegrees(60)));
                        canvas.drawLine(centre, centre - halfStateHeight, centre, centre + halfStateHeight, statePaint);
                        canvas.drawLine(centre, centre + halfStateHeight, centre + dx, centre + halfStateHeight - dy, statePaint);
                        canvas.drawLine(centre, centre + halfStateHeight, centre - dx, centre + halfStateHeight - dy, statePaint);
                    }
                }
                break;
            case State.LOADING:
                if (!animator.isRunning()) {
                    animator.start();
                }
                canvas.save();
                canvas.rotate(degree, centre, centre);
                if (loadingBitmap != null) {
                    canvas.drawBitmap(loadingBitmap, centre - loadingBitmap.getWidth() / 2, centre - loadingBitmap.getHeight() / 2, statePaint);
                } else {
                    //绘制默认的loading  并开启动画
                    statePaint.setShader(gradient);
                    statePaint.setStyle(Paint.Style.STROKE);
                    statePaint.setStrokeWidth(stateLineWidth);
                    float stateRadius = stateCentre - stateLineWidth / 2 - 1;
                    if (stateRadius > 0) {
                        canvas.drawCircle(centre, centre, stateRadius, statePaint);
                    }

                }
                canvas.restore();
                break;
        }
    }


    public synchronized int getMax() {
        return max;
    }

    /**
     * 设置进度的最大值
     */
    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度.需要同步
     */
    public synchronized int getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     */
    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = progress;
            postInvalidate();
        }
    }

    public void reset() {
        setMax(100);
        setProgress(0);
    }

    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }

    @State
    public int getState() {
        return state;
    }

    public void setState(@State int state) {
        this.state = state;
        if (lastState != this.state) {
            lastState = this.state;
            invalidate();
        }
    }

    public void showNone() {
        setState(State.NONE);
    }

    public void showStart() {
        setState(State.START);
    }

    public void showPause() {
        setState(State.PAUSE);
    }

    public void showLoading() {
        setState(State.LOADING);
    }

    public void showUpLoad() {
        setState(State.UP_LOAD);
    }

    public void showDownLoad() {
        setState(State.DOWN_LOAD);
    }

    @Override
    public void onGlobalLayout() {
        if (getWidth() > 0 && getHeight() > 0) {
            int minValue = Math.min(getWidth(), getHeight());
            int centerValue = minValue / 2;
            if (minValue < stateWidth || minValue < stateHeight) {
                throw new IllegalArgumentException("layout_width or layout_height can not be less than stateWidth or stateHeight");
            }
            gradient = new SweepGradient(centerValue, centerValue, 0, stateColor);
            if (stateWidth != 0 && stateHeight != 0) {
                if (startDrawable != null) {
                    startBitmap = ImageUtils.zoomDrawableToBitmap(startDrawable, (int) stateWidth, (int) stateHeight);
                }
                if (pauseDrawable != null) {
                    pauseBitmap = ImageUtils.zoomDrawableToBitmap(pauseDrawable, (int) stateWidth, (int) stateHeight);
                }
                if (loadingDrawable != null) {
                    loadingBitmap = ImageUtils.zoomDrawableToBitmap(loadingDrawable, (int) stateWidth, (int) stateHeight);
                }
                if (downLoadDrawable != null) {
                    downLoadBitmap = ImageUtils.zoomDrawableToBitmap(downLoadDrawable, (int) stateWidth, (int) stateHeight);
                }
                if (upLoadDrawable != null) {
                    upLoadBitmap = ImageUtils.zoomDrawableToBitmap(upLoadDrawable, (int) stateWidth, (int) stateHeight);
                }
            }else {//默认状态的宽高
                stateWidth = minValue / 5;
                stateHeight = minValue / 5;
                if (startDrawable != null) {
                    startBitmap = ImageUtils.drawableToBitmap(startDrawable);
                }
                if (pauseDrawable != null) {
                    pauseBitmap = ImageUtils.drawableToBitmap(pauseDrawable);
                }
                if (loadingDrawable != null) {
                    loadingBitmap = ImageUtils.drawableToBitmap(loadingDrawable);
                }
                if (downLoadDrawable != null) {
                    downLoadBitmap = ImageUtils.drawableToBitmap(downLoadDrawable);
                }
                if (upLoadDrawable != null) {
                    upLoadBitmap = ImageUtils.drawableToBitmap(upLoadDrawable);
                }
            }
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    @IntDef({
            State.NONE,
            State.START,
            State.PAUSE,
            State.LOADING,
            State.UP_LOAD,
            State.DOWN_LOAD
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
        int NONE = 0;
        int START = 1;
        int PAUSE = 2;
        int LOADING = 3;
        int UP_LOAD = 4;
        int DOWN_LOAD = 5;
    }
}
