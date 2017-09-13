package com.example.zhongyu.owntest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.math.BigDecimal;

/**
 * Created by zhongyu on 2017/9/11.
 * 日历
 */
public class CustomCalendarView extends View {

    private Paint todayPaint;
    private Paint clickPaint;
    private Paint paint;
    private Paint titlePaint;
    private int viewHeight;
    private int viewWidth;
    private float mDownX,mDownY;
    private int cardWidth;//每一个表格的宽度
    private String[] weeeks = new String[]{"日","一","二","三","四","五","六"};
    private int [][] dayNum = new int[6][7];
    private int touchSlop;
    private int selectedDayNum ;//默认
    private int x = 0;
    private int y = 0;
    private OnCalendarClickListener listener;
    private int year;
    private int month;
    private int day;
    private boolean isClick = false;


    public CustomCalendarView(Context context) {
        super(context);
        init(context);
    }

    public CustomCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){

        titlePaint = new Paint();
        titlePaint.setTextSize(40);
        titlePaint.setAntiAlias(true);
        titlePaint.setColor(Color.BLACK);
        todayPaint = new Paint();
        todayPaint.setColor(Color.BLUE);
        todayPaint.setAntiAlias(true);

        clickPaint = new Paint();
        clickPaint.setAntiAlias(true);
        clickPaint.setColor(Color.parseColor("#1EAAF1"));

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(33);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        year = DateUtil.getYear();
        month = DateUtil.getMonth();
        day = DateUtil.getCurrentMonthDay();
        selectedDayNum = day;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        cardWidth = viewWidth/7;
        viewHeight = (int) (cardWidth*8.5f);
        setMeasuredDimension(viewWidth + cardWidth * 2, viewHeight + cardWidth / 2);
    }

    public int getSelectedDayNum() {
        return selectedDayNum;
    }

    public void setListener(OnCalendarClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setDate();
        String date = year + "年" + month + "月" + day + "日";
        Paint.FontMetrics metrics = paint.getFontMetrics();
        float txtHeiht = (float) (Math.ceil(metrics.descent - metrics.top) / 2);
        float txtWith = paint.measureText(date);
        paint.setColor(Color.BLACK);
        drawLeftAndRightIcon(canvas,txtWith);
        canvas.drawText(date, viewWidth / 2 - txtWith/2, cardWidth, titlePaint);//画出标题
        drawWeek(canvas);
        drawMonthNum(canvas);
    }

    private void drawLeftAndRightIcon(Canvas canvas,float txtwidth) {
        Bitmap leftBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_left);
        Bitmap rightBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_right);
        canvas.drawBitmap(leftBitmap, 2 * cardWidth - txtwidth / 2, cardWidth / 2, paint);
        canvas.drawBitmap(rightBitmap, 6 * cardWidth - txtwidth / 6, cardWidth / 2, paint);
    }

    /**
     * 画日期
     * @param c
     */
    private void drawMonthNum(Canvas c) {
        int selectedDayNum = getSelectedDayNum();
        for (int i = 0; i < dayNum.length; i++) {
            for (int j = 0; j <7 ; j++) {
                float text = cardWidth -paint.measureText(dayNum[i][j] + "");
                if (i == 0 && dayNum[i][j] >20){//上个月的
                    paint.setColor(Color.GRAY);
                    c.drawText(dayNum[i][j] + "", (float) cardWidth * j + text / 2, (float) cardWidth * (i + 3), paint);
                }else if (i == 5|| i == 4 && dayNum[i][j] < 20){//下个月的
                    paint.setColor(Color.GRAY);
                    c.drawText(dayNum[i][j] + "", (float) cardWidth * j + text / 2, (float) cardWidth * (i + 3), paint);
                }else {//本月的
                    paint.setColor(Color.BLACK);
                    c.drawText(dayNum[i][j]+"",(float)cardWidth*j+text/2,(float)cardWidth*(i+3),paint);
                }
                if (dayNum[i][j] == selectedDayNum&& !isClick&& i<3){//第一次打开日历默认选中
                    paint.setColor(Color.WHITE);
                    c.drawCircle((float) cardWidth * j + cardWidth/2, (float) cardWidth * (3+i)-cardWidth/12, cardWidth / 2, clickPaint);
                    c.drawText(dayNum[i][j] + "", (float) cardWidth * j + text / 2, (float) cardWidth * (i + 3), paint);
                }else if (x == j && y == i && dayNum[i][j] == selectedDayNum ){
                    paint.setColor(Color.WHITE);
                    c.drawCircle((float) cardWidth * j + cardWidth/2, (float) cardWidth * (3+i)-cardWidth/12, cardWidth / 2, clickPaint);
                    c.drawText(dayNum[i][j] + "", (float) cardWidth * j + text / 2, (float) cardWidth * (i + 3), paint);
                }
            }
        }
    }

    /**
     * 画周一 * 周日
     * @param canvas
     */
    private void drawWeek(Canvas canvas) {
        for (int i = 0; i < 7; i++) {
            float measureText =cardWidth - paint.measureText(weeeks[i]);
            paint.setColor(Color.parseColor("#1EAAF1"));
            canvas.drawText(weeeks[i],(float) cardWidth*i+measureText/2, (float) cardWidth*2, paint);
        }
    }
    public void setDate(){
        dayNum = DateUtil.getMonthNumFromDate(year,month);
        invalidate();
    }

    /**
     * 设置日期
     * @param year
     * @param month
     */
    public void setYearAndMonth(int year,int month){
        this.month = month;
        this.year = year;
        invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                setSelectedDay(mDownX,mDownY);
                int ySwitch = (int) ((mDownY - cardWidth)/cardWidth);
                if (ySwitch <= 1){
                    if (x >= 1 && x <= 2){
                        isClick = false;
                        onPreChoosed();
                    }else if ( x >= 5 && x <= 6){
                        isClick = false;
                        onNextChoosed();
                    }
                }else {
                    isClick = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 选择日期画点
     * @param mDownX
     * @param mDownY
     */
    private void setSelectedDay(float mDownX, float mDownY) {
        x = (int) (mDownX / cardWidth);
        y = (int) ((mDownY-3*cardWidth))/cardWidth;
        x = new BigDecimal(x+"").setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        y = new BigDecimal(y+"").setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
        if (y >= 6||x >= 7 ||x < 0 || y < 0){
            return;
        }else if (y == 0&&dayNum[y][x] > 20){//上个月
            onPreChoosed();
            return;
        }else if (y == 5 || y == 4 && dayNum[y][x] < 20){//下个月
            onNextChoosed();
            return;
        }else {
            setselectedDayNum(dayNum[y][x]);
            day = dayNum[y][x];
            listener.calendarClick(year,month,day);
            isClick = true;
            invalidate();
        }
    }

    /**
     * 设置选中的日期
     * @param selectedDayNum
     */
    public void setselectedDayNum(int selectedDayNum) {
        this.selectedDayNum = selectedDayNum;
    }

    /**
     * 上个月
     */
    public void onPreChoosed(){
        if (month == 1){
            year = year-1;
            month = 12;
        }else {
            month = month -1;
        }
        day = 1;
        selectedDayNum = day;
        invalidate();
    }

    /**
     * 下个月
     */
    public void onNextChoosed(){
        if (month == 12){
            month = 1;
            year = year+1;
        }else {
            month = month+1;
        }
        day = 1;
        selectedDayNum = day;
        invalidate();
    }

    /**
     * 前一天
     */
    public void onDaypre(){
        if (day == 1){
            onPreChoosed();
        }else {
            day = day -1;
        }
        listener.calendarClick(year, month, day);
    }

    /**
     * 后一天
     */
    public void onDayNext(){
        if (day == DateUtil.getMonthDaysNum(year,month)){
            onNextChoosed();
        }else {
            day = day+1;
        }
        listener.calendarClick(year, month, day);
    }
    /**
     * 获取日期接口
     */
    public interface OnCalendarClickListener{
        void calendarClick(int year, int month, int day);
    }
}
