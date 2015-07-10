package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.ProjectCategoryAdapter;
import com.jiayantech.jyandroid.adapter.ProjectChildCategoryAdapter;
import com.jiayantech.jyandroid.manager.UserManger;
import com.jiayantech.jyandroid.widget.FlowLayout;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.comm.ActivityResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janseon on 2015/7/1.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. Al
 */
public class SelectProjectActivity extends BaseActivity implements FlowLayout.OnItemClickListener {
    public static final String KEY_TO_PICK = "to_pick";
    public static final String KEY_categoryIds = "categoryIds";
    public static final String KEY_categoryNames = "categoryNames";
    public static final int REQUEST_CODE_SELECT = 0x100;

    private FlowLayout layout_selected;
    private RecyclerView list_parents;
    private RecyclerView list_children;

    private boolean to_pick;
    private ProjectCategoryAdapter mAdapter;

    private ArrayList<String> mSelectedList = new ArrayList<>();
    private ArrayList<String> mSelectedIds = new ArrayList<>();


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
        list_children = (RecyclerView) findViewById(R.id.list_children);
    }

    protected void setViewsContent() {
        to_pick = getIntent().getBooleanExtra(KEY_TO_PICK, false);
        layout_selected.setViews(mSelectedList);
        list_parents.setLayoutManager(new LinearLayoutManager(this));
        list_children.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProjectCategoryAdapter(UserManger.sProjectCategoryData, UserManger.sProjectCategoryTopLevels);
        list_parents.setAdapter(mAdapter);
    }

    protected void setViewsListener() {
        mAdapter.setOnItemClickListener(new BaseSimpleModelAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(BaseSimpleModelAdapter<String> adapter, int position, String item) {
                final List<String> list = UserManger.sProjectCategoryLevels.get(item);
                ProjectChildCategoryAdapter childCategoryAdapter = new ProjectChildCategoryAdapter(UserManger.sProjectCategoryData, list);
                list_children.setAdapter(childCategoryAdapter);
                childCategoryAdapter.setOnItemClickListener(new BaseSimpleModelAdapter.OnItemClickListener<String>() {
                    @Override
                    public void onItemClick(BaseSimpleModelAdapter<String> adapter, int position, String item) {
                        boolean selected = ((ProjectChildCategoryAdapter) adapter).select(position);
                        if (selected) {
                            addSelected(item);
                        } else {
                            removeSelected(position);
                        }
                    }
                });
            }
        });
        layout_selected.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(to_pick ? R.menu.finish_action : R.menu.next_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_next:
                Intent intent = new Intent(this, NewDiaryInfoActivity.class);
                intent.putStringArrayListExtra(KEY_categoryNames, mSelectedList);
                intent.putStringArrayListExtra(KEY_categoryIds, mSelectedIds);
                finishToStartActivity(intent);
                return true;
            case R.id.action_finish:
                Intent finishIntent = new Intent(this, NewDiaryInfoActivity.class);
                finishIntent.putStringArrayListExtra(KEY_categoryNames, mSelectedList);
                finishIntent.putStringArrayListExtra(KEY_categoryIds, mSelectedIds);
                ActivityResult.onFinishResult(this, finishIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View v, int index) {
        removeSelected(index);
    }

    private void removeSelected(int index) {
        layout_selected.removeViewAt(index);
        mSelectedList.remove(index);
        mSelectedIds.remove(index);
    }

    private void addSelected(String id) {
        String name = UserManger.sProjectCategoryData.get(id);
        layout_selected.addView(name);
        mSelectedList.add(name);
        mSelectedIds.add(id);
    }
}
