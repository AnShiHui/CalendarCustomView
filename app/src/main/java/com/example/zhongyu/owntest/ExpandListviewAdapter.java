package com.example.zhongyu.owntest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

/**
 * Created by zhongyu on 2017/8/25.
 */
public class ExpandListviewAdapter extends BaseExpandableListAdapter {

    private Map<String,List<String>> listMap;
    private String[]parentString;
    private Context mContext;
    private int[] resId = new int[]{R.mipmap.image,R.mipmap.imagegirl,R.mipmap.imagethree};

    public ExpandListviewAdapter(Map<String, List<String>> listMap, Context mContext,String[] parentString) {
        this.listMap = listMap;
        this.mContext = mContext;
        this.parentString = parentString;
    }

    @Override
    public int getGroupCount() {
        int size = listMap.size();
        if (size > 0){
            return size;
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int i) {
        int size = listMap.get(parentString[i]).size();
        if (size > 0){
            return size;
        }
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return listMap.get(parentString[i]);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listMap.get(parentString[i]).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.parent_item_layout,null);
        }
        view.setTag(R.layout.parent_item_layout,i);
        view.setTag(R.layout.child_item_layout,-1);
        TextView textView = (TextView) view.findViewById(R.id.parent_tv);
        ImageView imageView = (ImageView) view.findViewById(R.id.parent_iv);
        textView.setText(parentString[i]);
        view.setTag(-1,imageView);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.child_item_layout,null);
        }
        view.setTag(R.layout.parent_item_layout,i);
        view.setTag(R.layout.child_item_layout,i1);
        TextView textView = (TextView) view.findViewById(R.id.child_tv);
        ImageView imageView = (ImageView) view.findViewById(R.id.child_iv);
        Glide.with(mContext)
                .load(resId[i1])
                .transform(new GlideCircleTransform(mContext))
                .into(imageView);
        textView.setText(listMap.get(parentString[i]).get(i1));
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
}
