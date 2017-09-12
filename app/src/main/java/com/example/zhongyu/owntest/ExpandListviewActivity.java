package com.example.zhongyu.owntest;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpandListviewActivity extends AppCompatActivity {

    private ExpandableListView mListview;
    private ExpandListviewAdapter adapter;
    private Map<String,List<String>> parentMap;
    private boolean isOpen = false;
    private String[] parentData = new String[]{"那年秋天","黄昏未至","夕阳无限美","湖边景色","美得像你","这年冬天","寒窗白雪","心寒如我"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_listview);
        init();
        initData();
    }

    private void initData() {
        parentMap = new HashMap<>();
        int length = parentData.length;
        for (int i = 0; i < length; i++) {
            List<String> list = new ArrayList<>();
            list.add("Pugssss");
            list.add("AnAnn");
            list.add("UUU");
            parentMap.put(parentData[i],list);
        }
        adapter = new ExpandListviewAdapter(parentMap,this,parentData);
        mListview.setAdapter(adapter);
    }

    private void init() {
        mListview = (ExpandableListView) findViewById(R.id.elistview);
        mListview.setChildDivider(new ColorDrawable(Color.GRAY));
        mListview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                return false;
            }
        });
        mListview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

                return false;
            }
        });
        mListview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
    }
}
