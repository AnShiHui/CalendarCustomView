package com.example.zhongyu.owntest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by zhongyu on 2017/9/6.
 */
public class ContainerView extends ViewGroup {
    public ContainerView(Context context) {
        super(context);
    }

    public ContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int count = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        for (int j = 0; j < count; j++) {
            View child = getChildAt(j);
                switch (j){
                case 0:
                    cWidth = child.getWidth();
                    cHeight = child.getHeight();
                    break;
                case 1:
                    i = cWidth/2-50;
                    i1 = cHeight/2-50;
                    i2 =cWidth/2+child.getMeasuredWidth()/2;
                    i3 = cHeight+child.getMeasuredHeight()/2;
                    break;
            }
            child.layout(i,i1,i2,i3);
        }
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthSpec = MeasureSpec.getMode(widthMeasureSpec);
//        int sizewidth = MeasureSpec.getSize(widthSpec);
//        int heightSpec = MeasureSpec.getMode(heightMeasureSpec);
//        int sizeheight = MeasureSpec.getSize(heightSpec);
//        measureChildren(widthSpec, heightSpec);
//        int childCount = getChildCount();
//        int width = 0 ;
//        int height = 0;
//        for (int i = 0; i < childCount; i++) {
//            View child = getChildAt(i);
//            MarginLayoutParams childLayoutParams = (MarginLayoutParams) child.getLayoutParams();
//            switch (i){
//                case 0:
//                    width = child.getMeasuredWidth()+childLayoutParams.leftMargin+childLayoutParams.rightMargin;
//                    height = child.getMeasuredHeight()+childLayoutParams.bottomMargin+childLayoutParams.topMargin;
//                    break;
//                case 1:
//                    break;
//            }
//        }
//        setMeasuredDimension(widthSpec == MeasureSpec.EXACTLY ?sizewidth:width,heightSpec == MeasureSpec.EXACTLY?sizeheight:height);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return super.generateLayoutParams(attrs);
    }
}
