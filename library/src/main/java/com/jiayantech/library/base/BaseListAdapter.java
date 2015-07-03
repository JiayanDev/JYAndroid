package com.jiayantech.library.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by janseon on 2015/7/3.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected ArrayList<T> mList = new ArrayList<T>();

    public BaseListAdapter(Context context, ArrayList<T> list) {
        super();
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list == null ? new ArrayList<T>() : list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public ArrayList<T> getList() {
        return mList;
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    /**
     * 描述：添加最新的数据
     *
     * @param list
     * @version 1.0
     * @createTime 2014-4-29 上午11:16:37
     * @createAuthor 健兴
     * @updateTime 2014-4-29 上午11:16:37
     * @updateAuthor 健兴
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public void addNew(ArrayList<T> list) {
        mList.addAll(0, list);
        notifyDataSetChanged();
    }

    /**
     * 描述：添加更多数据
     *
     * @param list
     * @version 1.0
     * @createTime 2014-4-29 上午11:17:05
     * @createAuthor 健兴
     * @updateTime 2014-4-29 上午11:17:05
     * @updateAuthor 健兴
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public void addMore(ArrayList<T> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void add(T value) {
        mList.add(value);
        notifyDataSetChanged();
    }

    public void add(int index, T value) {
        mList.add(index, value);
        notifyDataSetChanged();
    }

    public boolean remove(T value) {
        boolean removed = mList.remove(value);
        notifyDataSetChanged();
        return removed;
    }

    public T remove(int index) {
        T value = mList.remove(index);
        notifyDataSetChanged();
        return value;
    }

    public void findRemove(T value) {
        T finadValue = find(value);
        remove(finadValue);
        notifyDataSetChanged();
    }

    public void findUpdate(T value) {
        int index = indexOf(value);
        update(index, value);
    }

    public void update(int index, T value) {
        mList.set(index, value);
        notifyDataSetChanged();
    }

    public T find(T another) {
        if (another == null) {
            return null;
        }
        for (T obj : mList) {
            if (another.equals(obj)) {
                return obj;
            }
        }
        return null;
    }

    // public int indexOf(T another) {
    // return mList.indexOf(another);
    // }

    public int indexOf(T another) {
        if (another == null) {
            return -1;
        }
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            if (another.equals(mList.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public static void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public static void setGridViewHeightBasedOnChildren(GridView gridView) {
        // 获取GridView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int rows;
        int columns = 0;
        int horizontalBorderHeight = 0;
        Class<?> clazz = gridView.getClass();
        try {
            //利用反射，取得每行显示的个数
            Field column = clazz.getDeclaredField("mRequestedNumColumns");
            column.setAccessible(true);
            columns = (Integer) column.get(gridView);
            //利用反射，取得横向分割线高度
            Field horizontalSpacing = clazz.getDeclaredField("mRequestedHorizontalSpacing");
            horizontalSpacing.setAccessible(true);
            horizontalBorderHeight = (Integer) horizontalSpacing.get(gridView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
        if (listAdapter.getCount() % columns > 0) {
            rows = listAdapter.getCount() / columns + 1;
        } else {
            rows = listAdapter.getCount() / columns;
        }
        int totalHeight = 0;
        for (int i = 0; i < rows; i++) { //只计算每项高度*行数
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + horizontalBorderHeight * (rows - 1);//最后加上分割线总高度
        gridView.setLayoutParams(params);
    }

    protected static ArrayList<String> toStringList(int num) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("");
        for (int i = 0; i < num; i++) {
            list.addAll(list);
        }
        return list;
    }

}
