package com.example.zhongyu.owntest;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhongyu on 2017/9/4.
 * 心跳测试进度条
 * 模仿心跳动画+计时转圈
 */
public class HeartCircleProgressBar extends View implements OnSwitchLisener,View.OnClickListener{

    private float radius;//半径
    private Paint circlePaint;//圆环画笔
    private Paint pointPaint;//圆点画笔
    private float widthStoke;//圆环粗细
    private int circleColor;//圆环颜色
    private float pointRadius;//圆点半径
    private int pointColor;//圆点颜色
    private float  xCentre,yCentre;
    private ObjectAnimator animator;
    private OnSwitchLisener switchLisener;
    private OnTimerListener timerListener;
    private boolean isStart = false;

    public HeartCircleProgressBar(Context context) {
        super(context);
    }

    public HeartCircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public HeartCircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 获取属性列表
     * @param attributeSet
     */
    public void init(AttributeSet attributeSet){
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.HeartCircleProgressBar);
        radius = typedArray.getDimension(R.styleable.HeartCircleProgressBar_heart_radius, 0);
        widthStoke = typedArray.getDimension(R.styleable.HeartCircleProgressBar_heart_stoke, 0);
        circleColor = typedArray.getColor(R.styleable.HeartCircleProgressBar_heart_circle_color, Color.GRAY);
        pointRadius = typedArray.getDimension(R.styleable.HeartCircleProgressBar_heart_point_radius, 10);
        pointColor = typedArray.getColor(R.styleable.HeartCircleProgressBar_heart_point_color, Color.BLACK);
        animator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f);
        // 动画作用的对象的属性是旋转rotation
        // 动画效果是:0 - 360
        animator.setDuration(1000);
        animator.setRepeatCount(-1);
        setSwitchLisener(this);
        setOnClickListener(this);
        typedArray.recycle();
        initPaint();
    }

    /**
     * 画笔
     */
    public void initPaint(){
        circlePaint = new Paint();
        circlePaint.setStrokeWidth(widthStoke);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(circleColor);
        circlePaint.setStyle(Paint.Style.STROKE);

        pointPaint = new Paint();
        pointPaint.setColor(pointColor);
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        xCentre = getWidth()/2;
        yCentre = getHeight()/2;
        if (radius == 0){
            radius = Math.max(xCentre,yCentre) - widthStoke/2;
        }
        canvas.drawCircle(xCentre, yCentre, radius, circlePaint);
        drawPoint(canvas);
        canvas.restore();
    }

    /**
     * @param canvas
     */
    public void drawPoint(Canvas canvas){
        canvas.drawCircle(xCentre, yCentre - radius, pointRadius, pointPaint);
    }

    @Override
    public void invalidate() {
        initPaint();
        super.invalidate();
    }

    @Override
    public void onStartTimer() {
        animator.start();
    }

    @Override
    public void onEndTimer() {
        animator.cancel();
    }

    @Override
    public void onClick(View view) {
        if (isStart){
            switchLisener.onEndTimer();
            timerListener.onTimerEnded();
            isStart = false;
        }else {
            switchLisener.onStartTimer();
            timerListener.onTimerStart();
            isStart = true;
        }
    }

    public void setSwitchLisener(OnSwitchLisener lisener){
        switchLisener = lisener;
    }

    public void setTimerListener(OnTimerListener timerListener) {
        this.timerListener = timerListener;
    }

    //计时器接口
    public interface OnTimerListener{
        void onTimerStart();
        void onTimerEnded();
    }
}
