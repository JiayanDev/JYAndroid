package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.ProjectCategoryAdapter;
import com.jiayantech.jyandroid.manager.UserManger;
import com.jiayantech.jyandroid.model.Login;
import com.jiayantech.jyandroid.widget.FlowLayout;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.comm.ActivityResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by janseon on 2015/7/1.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class SelectProjectActivity extends BaseActivity implements BaseSimpleModelAdapter.OnItemClickListener<Login.Category>, FlowLayout.OnItemClickListener {
    public static final String KEY_TO_PICK = "toPick";
    public static final String KEY_categories = "categories";
    public static final int REQUEST_CODE_SELECT = 0x100;

    private FlowLayout layout_selected;
    private RecyclerView list_parents;
    private LinearLayout list_children;

    private boolean toPick;
    private ProjectCategoryAdapter mAdapter;

    private ArrayList<Login.Category> mSelecteds = new ArrayList<>();

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
        layout_selected.setViews(idSelectedSet, mSelecteds);
        list_parents.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProjectCategoryAdapter(UserManger.sLogin.projectCategory.data);
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
                intent.putParcelableArrayListExtra(KEY_categories, mSelecteds);
                finishToStartActivity(intent);
                return true;
            case R.id.action_finish:
                Intent finishIntent = new Intent(this, NewDiaryInfoActivity.class);
                finishIntent.putParcelableArrayListExtra(KEY_categories, mSelecteds);
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
    public void onItemClick(BaseSimpleModelAdapter<Login.Category> adapter, int position, Login.Category category) {
        list_children.removeAllViews();
        final List<Login.Category> list = category.sub;
        for (final Login.Category itemCategory : list) {
            final String name = itemCategory.name;

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
            layout_project.setViews(idSelectedSet, list);

            list_children.addView(view);
        }
    }


    private HashSet<Integer> idSelectedSet = new HashSet<>();

    private void onFlowItemClick(View v) {
        Login.Category category = (Login.Category) v.getTag();
        int id = v.getId();
        v.setSelected(!v.isSelected());
        if (v.isSelected()) {
            mSelecteds.add(category);
            idSelectedSet.add(id);
            layout_selected.addView(category, idSelectedSet.contains(id));
        } else {
            mSelecteds.remove(category);
            idSelectedSet.remove(id);
            layout_selected.removeView(layout_selected.findViewById(id));
        }
        View view = list_children.findViewById(id);
        if (view != null) {
            view.setSelected(v.isSelected());
        }
    }
}
