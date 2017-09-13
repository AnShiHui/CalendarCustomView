package com.example.zhongyu.owntest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,HeartCircleProgressBar.OnTimerListener,CustomCalendarView.OnCalendarClickListener {

    private MySwipeView swipeView;
    private ArrayList<String> mDatas;
    private ArrayAdapter<String> mAdapter;
    private HeartCircleProgressBar heartCircleProgressBar;
    private Chronometer chronometer;
    private ImageView iv;
    private Button btn,btn_txt,btn_size,btn_circolor,btn_pbcolor;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                swipeView.setRefreshComplete();
                startActivity(new Intent(MainActivity.this,ExpandListviewActivity.class));
            }
        }
    };
    int i = 20;
    private ObjectAnimator objectAnimator;
    private AnimatorSet animatorSet;
    private CustomCalendarView customCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        //heartCircleProgressBar = (HeartCircleProgressBar) findViewById(R.id.heart_pb);
       // chronometer = (Chronometer) findViewById(R.id.timer);
       // iv = (ImageView) findViewById(R.id.iv_heart);
       // btn_size = (Button) findViewById(R.id.btn_size);
        customCalendarView = (CustomCalendarView) findViewById(R.id.ccv);
        //btn_size.setOnClickListener(this);
        customCalendarView.setListener(this);
       // heartCircleProgressBar.setTimerListener(this);
       // animatorSet = new AnimatorSet();//组合动画
       // ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv, "scaleX", 1f, 1.5f,1f);
       // ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv, "scaleY", 1f, 1.5f,1f);
       // scaleX.setRepeatCount(-1);
        //scaleY.setRepeatCount(-1);
       // animatorSet.setDuration(1000);
        //animatorSet.setInterpolator(new DecelerateInterpolator());
        //animatorSet.play(scaleX).with(scaleY);//两个动画同时开始
        String[] data = new String[]{"hello world0","hello world1","hello world2","hello world3",
                "hello world4", "hello world5", "hello world6", "hello world7", "hello world8",
                "hello world9", "hello world10", "hello world11", "hello world12", "hello world13",};
        mDatas = new ArrayList<String>(Arrays.asList(data));
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
       // swipeView = (MySwipeView) findViewById(R.id.swipe);
       // swipeView.setAdapter(mAdapter);
//        swipeView.setRefreshListener(new MySwipeView.onRefreshListener() {
//            @Override
//            public void onFreshing() {
//            }
//
//            @Override
//            public void onFreshComplete() {
//                mHandler.sendEmptyMessageDelayed(0,3000);
//            }
//        });
       // btn.setOnClickListener(this);

   }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.btn_txt:
//                progressBar.setTextContent("哈哈哈");
//                break;
//            case R.id.btn_circolor:
//                progressBar.setCircleColor(Color.RED);
//                break;
//            case R.id.btn_pbcolor:
//                progressBar.setDefaultColor(Color.GREEN);
//                break;
            //case R.id.btn_size:
               // Log.d("TAG", " settings");
               //customCalendarView.setYearAndMonth(2017,10);
              //  startActivity(new Intent(MainActivity.this,ExpandListviewActivity.class));
             //   break;
//            case R.id.btn:
//                progressBar.setCircleProgress(i++);
//                break;
        }
    }

    @Override
    public void onTimerStart() {
        chronometer.start();
        animatorSet.start();
        chronometer.setBase(SystemClock.elapsedRealtime());
    }

    @Override
    public void onTimerEnded() {
        chronometer.stop();
        animatorSet.cancel();
    }

    @Override
    public void calendarClick(int year, int month, int day) {

    }
}
