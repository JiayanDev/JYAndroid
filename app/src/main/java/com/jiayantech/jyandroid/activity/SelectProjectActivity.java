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
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.comm.ActivityResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by janseon on 2015/7/1.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class SelectProjectActivity extends BaseActivity implements BaseSimpleModelAdapter.OnItemClickListener<String>, FlowLayout.OnItemClickListener {
    public static final String KEY_TO_PICK = "toPick";
    public static final String KEY_categoryIds = "categoryIds";
    public static final String KEY_categoryNames = "categoryNames";
    public static final int REQUEST_CODE_SELECT = 0x100;

    private FlowLayout layout_selected;
    private RecyclerView list_parents;
    private LinearLayout list_children;

    private boolean toPick;
    private ProjectCategoryAdapter mAdapter;

    private ArrayList<String> mSelectedIds = new ArrayList<>();
    private ArrayList<String> mSelectedList = new ArrayList<>();


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
        setTitle(R.string.title_my_diaries);
        toPick = getIntent().getBooleanExtra(KEY_TO_PICK, false);
        layout_selected.setViews(idSelectedSet, mSelectedIds, mSelectedList);
        list_parents.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProjectCategoryAdapter(UserManger.sProjectCategoryData, UserManger.sProjectCategoryTopLevels);
        list_parents.setAdapter(mAdapter);
    }

    protected void setViewsListener() {
        mAdapter.setOnItemClickListener(this);
        layout_selected.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(toPick ? R.menu.finish_action : R.menu.next_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        onFlowItemClick(v);
    }

    @Override
    public void onItemClick(BaseSimpleModelAdapter<String> adapter, int position, String id) {
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
                    onFlowItemClick(v);
                }
            });
            addRandomFlowViews(itemId, name, layout_project);

            list_children.addView(view);
        }
    }

    private HashSet<String> idSelectedSet = new HashSet<>();

    private void onFlowItemClick(View v) {
        String name = ((TextView) v).getText().toString();
        String id = String.valueOf(v.getId());
        int intId = Integer.valueOf(id);
        v.setSelected(!v.isSelected());
        if (v.isSelected()) {
            mSelectedIds.add(id);
            mSelectedList.add(name);
            idSelectedSet.add(id);
            layout_selected.addView(id, name, idSelectedSet.contains(id));
        } else {
            mSelectedIds.remove(id);
            mSelectedList.remove(name);
            idSelectedSet.remove(id);
            layout_selected.removeView(layout_selected.findViewById(intId));
        }
        View view = list_children.findViewById(intId);
        if (view != null) {
            view.setSelected(v.isSelected());
        }
    }

    private void addRandomFlowViews(String id, String name, FlowLayout flowLayout) {
        List<String> idList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        int count = Math.abs(new Random().nextInt()) % 10 + 1;
        for (int i = 0; i < count; i++) {
            idList.add(id + i);
            nameList.add(name + i);
        }
        flowLayout.setViews(idSelectedSet, idList, nameList);
    }
}
