package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.ProjectCategoryAdapter;
import com.jiayantech.jyandroid.manager.UserManger;
import com.jiayantech.jyandroid.widget.FlowLayout;
import com.jiayantech.library.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by janseon on 2015/7/1.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class SelectProjectActivity extends BaseActivity implements ProjectCategoryAdapter.OnItemClickListener, FlowLayout.OnItemClickListener {
    private FlowLayout layout_selected;
    private RecyclerView list_parents;
    private LinearLayout list_children;

    private ArrayList<String> mSelectedList = new ArrayList<>();
    private ArrayList<String> mSelectedIds = new ArrayList<>();
    private ProjectCategoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_project);
        findViews();
        setViewsContent();
        setViewsListener();
    }


    protected void findViews() {
        layout_selected = (FlowLayout) findViewById(R.id.layout_selected);
        list_parents = (RecyclerView) findViewById(R.id.list_parents);
        list_children = (LinearLayout) findViewById(R.id.list_children);
    }

    protected void setViewsContent() {
        layout_selected.setViews(mSelectedList);
        list_parents.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProjectCategoryAdapter(UserManger.sProjectCategoryData, UserManger.sProjectCategoryTopLevels);
        list_parents.setAdapter(mAdapter);
    }

    protected void setViewsListener() {
        setDisplayHomeAsUpEnabled();
        mAdapter.setOnItemClickListener(this);
        layout_selected.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_next:
                Intent intent = new Intent(this, NewDiaryInfoActivity.class);
                intent.putStringArrayListExtra(NewDiaryInfoActivity.KEY_categoryName, mSelectedList);
                intent.putStringArrayListExtra(NewDiaryInfoActivity.KEY_categoryIds, mSelectedIds);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View v, int index) {
        mSelectedList.remove(index);
        layout_selected.setViews(mSelectedList);
    }

    @Override
    public void onItemClick(int position, String id) {
        list_children.removeAllViews();
        final List<String> list = UserManger.sProjectCategoryLevels.get(id);
        for (final String itemId : list) {
            final String name = UserManger.sProjectCategoryData.get(itemId);

            View view = getLayoutInflater().inflate(R.layout.item_select_project, list_children, false);
            TextView txt_category = (TextView) view.findViewById(R.id.txt_category);
            FlowLayout layout_project = (FlowLayout) view.findViewById(R.id.layout_project);

            txt_category.setText(name);

            layout_project.setOnItemClickListener(new FlowLayout.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int index) {
                    //String item = list.get(index);
                    layout_selected.addView(name);
                    mSelectedList.add(name);
                    mSelectedIds.add(itemId);
                }
            });
            addRandomFlowViews(name, layout_project);

            list_children.addView(view);
        }
    }

    private static void addRandomFlowViews(String name, FlowLayout flowLayout) {
        List<String> list = new ArrayList<>();
        int count = Math.abs(new Random().nextInt()) % 10 + 1;
        for (int k = 0; k < count; k++) {
            list.add(name + k);
        }
        flowLayout.setViews(list);
    }
}
