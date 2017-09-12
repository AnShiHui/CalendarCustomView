package com.example.zhongyu.owntest;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by zhongyu on 2017/8/29.
 */
public class OwnProgressBar extends View {

    private Paint textPaint;
    private Paint rfPaint;
    private Paint bigPaint;
    private String textContent;//字体内容
    private int textColor;//字体颜色
    private float circleSize;//圆形条的粗细
    private int circleColor;//圆形条的实体颜色
    private static final float defaultSize = 20;
    private int circleProgress;
    private int floatx,floaty;
    private float radius;//半径
    private int defaultColor;
    private int maxProgress;//最大进度
    private int txtWidth,txtHeight;//文本宽高
    private int currentProgress = 1;

    public OwnProgressBar(Context context) {
        super(context);
    }

    public OwnProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public OwnProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 获取属性和画笔
     * @param arr：属性数组
     */
    public void init(AttributeSet arr){
        TypedArray typedArray = getContext().obtainStyledAttributes(arr, R.styleable.OwnProgressBar);
        textContent = typedArray.getString(R.styleable.OwnProgressBar_circle_text);
        textColor = typedArray.getColor(R.styleable.OwnProgressBar_text_color, Color.BLACK);//默认字体是黑色
        circleColor = typedArray.getColor(R.styleable.OwnProgressBar_circle_color, Color.BLUE);//默认进度条是蓝色
        circleSize = typedArray.getDimension(R.styleable.OwnProgressBar_circle_size, defaultSize);
        circleProgress = typedArray.getInt(R.styleable.OwnProgressBar_circle_progress, 0);
        radius = typedArray.getDimension(R.styleable.OwnProgressBar_circle_radius, 0);
        defaultColor = typedArray.getColor(R.styleable.OwnProgressBar_circle_default_corlor,Color.GRAY);//默认圆形条是灰色
        maxProgress = typedArray.getInt(R.styleable.OwnProgressBar_max_progress,100);//默认最大进度是100
        typedArray.recycle();//释放
        initPaint();
    }

    public void initPaint(){
        bigPaint = new Paint();//圆形条画笔
        bigPaint.setColor(defaultColor);
        bigPaint.setAntiAlias(true);
        bigPaint.setStyle(Paint.Style.STROKE);
        bigPaint.setStrokeWidth(circleSize);
        bigPaint.setStrokeCap(Paint.Cap.ROUND);


        textPaint = new Paint();//字体画笔
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textColor);
        textPaint.setTextSize(radius/2);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        txtHeight = (int) Math.ceil(fontMetrics.descent - fontMetrics.ascent);//获取文本的高度

        rfPaint = new Paint();//进度条画笔
        rfPaint.setAntiAlias(true);
        rfPaint.setStyle(Paint.Style.STROKE);
        rfPaint.setColor(circleColor);
        rfPaint.setStrokeWidth(circleSize);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
       // super.onDraw(canvas);
        floatx = getWidth()/2;
        floaty = getHeight()/2;
        if (radius == 0){
            radius = Math.max(floatx,floaty) - circleSize/2;//默认半径
        }
       // canvas.drawCircle(floatx, floaty, radius, bigPaint);
        drawOvalProgress(currentProgress, canvas);
        if (currentProgress < circleProgress){
            currentProgress++;
            postInvalidate();
        }
    }

    /**
     * 画进度
     * @param progress：int
     */
    public void drawOvalProgress(int progress,Canvas canvas){
        if (progress > 0 ){
            RectF rf = new RectF();
            rf.left =floatx -radius;
            rf.right = floatx+radius;
            rf.bottom =floaty+radius;
            rf.top =floaty -radius;
            float degress = 5f;
            float defaults = -90;
            for (int i = 0; i <36; i++) {
                canvas.drawArc(rf,defaults,degress,false,rfPaint);
                defaults = defaults+2*degress;
            }
            canvas.drawArc(rf,-90,(float)currentProgress/100*360,false,bigPaint);
        }else if (progress < 0){
            throw new IllegalArgumentException("Progress is wrong");
        }
        if (!TextUtils.isEmpty(textContent)){
            txtWidth = (int) textPaint.measureText(textContent,0,textContent.length());
            canvas.drawText(textContent,floatx -txtWidth/2,floaty+txtHeight/4,textPaint);
        }
    }

    /**
     * 设置文本内容
     * @param textContent ：string
     */
    public void setTextContent(String textContent) {
        this.textContent = textContent;
        postInvalidate();
    }

    /**
     * 设置文本颜色
     * @param textColor :color
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
       invalidate();
    }

    /**
     * 设置圆形条粗细
     * @param circleSize：int
     */
    public void setCircleSize(int circleSize) {
        this.circleSize = circleSize;
        invalidate();
    }

    public int getCircleProgress() {
        return circleProgress;
    }

    /**
     * 设置圆形条颜色
     * @param color:color
     */
    public void setCircleColor(int color) {
        circleColor = color;
       invalidate();
    }

    public void setDefaultColor(int color){
        defaultColor = color;
        invalidate();
    }
    /**
     * 设置进度
     * @param circleProgress: int
     */
    public synchronized void setCircleProgress(int circleProgress) {
        if (circleProgress > maxProgress){
            circleProgress = maxProgress;
        }
        if (circleProgress < 0){
            throw new IllegalArgumentException("Progress not less than 0");
        }
        this.circleProgress = circleProgress;
        textContent = circleProgress+"%";
        if (currentProgress < circleProgress){
            currentProgress++;
        }else {
            currentProgress = circleProgress;
        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        floatx = w/2;
        floaty = h/2;
    }

    @Override
    public void invalidate() {
        initPaint();
        super.invalidate();
    }
}
