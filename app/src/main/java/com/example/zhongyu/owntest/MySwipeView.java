package com.example.zhongyu.owntest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by zhongyu on 2017/8/23.
 * 自定义下拉刷新
 */
public class MySwipeView extends ListView implements AbsListView.OnScrollListener {

    private int state = 0;
    private static final int FRESH_SUCCESS = 1;
    private static final int FRESH_FAIL = 2;
    private static final int ISREFRESHING = 3;
    private static final int PULL = 4;
    private boolean isEnd = true;
    private boolean isFreshing = false;
    private static final int FRESHOK = 5;
    private onRefreshListener refreshListener;
    private float startY;
    private View headerView;
    private ImageView iv_glide;
    private TextView tv_state;
    private int headerHeigth = 50;
    private Context context;
    private boolean canRefresh;
    private float mY;
    private int mFirstVisibleItem;
    private  float preY = 0;//记录上一次的Y坐标
    private boolean isFlipTop = false;//手指上滑

    public MySwipeView(Context context) {
        super(context);
        init(context);
    }

    public MySwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MySwipeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setOnScrollListener(this);
        setFriction(ViewConfiguration.getScrollFriction()*2);
        headerView = LayoutInflater.from(context).inflate(R.layout.swipeview_header_layout,null);
        iv_glide = (ImageView) headerView.findViewById(R.id.swipe_iv);
        tv_state = (TextView) headerView.findViewById(R.id.swipe_tv);
        headerHeigth = headerView.getMeasuredHeight();
        headerView.setPadding(0,-headerHeigth,0,0);
        isFreshing = false;
        isEnd = false;
        addHeaderView(headerView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN://记录按下手指的坐标位置
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mY = event.getY();
                float distance = mY - startY;
                if (mFirstVisibleItem == 0 &&!isFreshing){
                        if (distance > 200){
                            setImageviewSate(ISREFRESHING);
                            isFreshing = true;
                        }else {
                            setImageviewSate(PULL);
                        }
                        refreshListener.onFreshing();
                }
                headerView.setPadding(0, (int)distance, 0, 0);
                preY = mY;
                break;
            case MotionEvent.ACTION_UP:
                if (isFreshing){
                    smoothScrollToPosition(0);
                    headerView.setPadding(0,headerHeigth,0,0);
                    refreshListener.onFreshComplete();
                }else {
                    headerView.setPadding(0, -headerHeigth,0,0);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        mFirstVisibleItem = i;
    }
    public interface onRefreshListener{
        void onFreshing();
        void onFreshComplete();
    }

    public void setRefreshListener(onRefreshListener istener){
        refreshListener = istener;
    }

    public void setRefreshComplete(){
        setImageviewSate(FRESHOK);
        smoothScrollToPosition(0);
        headerView.setPadding(0, (int) -(mY - startY), 0, 0);
        //isFreshing = false;
    }

    public void setImageviewSate(int state){
        switch (state){
            case ISREFRESHING://正在刷新
                tv_state.setText(R.string.refreshing_now);
                Glide.with(context).load(R.mipmap.fresh).asGif().into(iv_glide);
                break;
            case PULL://下拉刷新
                tv_state.setText(R.string.pull_refresh);
                Glide.with(context).load(R.mipmap.ic_launcher).into(iv_glide);
                break;
            case FRESHOK:
                tv_state.setText(R.string.refrehs_compelete);
                break;
            default:
        }
    }
}
