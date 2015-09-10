package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.CategoryAdapter;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.comm.ActivityResult;

import java.util.ArrayList;

/**
 * Created by janseon on 2015/7/9.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class SearchCategoryActivity extends BaseActivity implements BaseSimpleModelAdapter.OnItemClickListener<AppInit.Category> {
    public static final String KEY_categories = "categories";

    private final int spanCount = 3;
    private RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;
    private ArrayList<AppInit.Category> mSelectedList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = new RecyclerView(this);
        setContentView(mRecyclerView);
        setViewsContent();
        setViewsListener();
    }

    protected void setViewsContent() {
        setTitle(R.string.title_select_category);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new CategoryAdapter(true);
        //mRecyclerView.setAdapter(mAdapter);
        mAdapter.resetGridHeight(mRecyclerView, spanCount);
    }

    protected void setViewsListener() {
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.finish_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_finish:
                Intent finishIntent = new Intent(this, NewDiaryInfoActivity.class);
                finishIntent.putParcelableArrayListExtra(KEY_categories, mSelectedList);
                ActivityResult.onFinishResult(this, finishIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(BaseSimpleModelAdapter<AppInit.Category> adapter, int position, AppInit.Category category) {
        boolean selected = mAdapter.select(position);
        if (selected) {
            mSelectedList.add(category);
        } else {
            mSelectedList.remove(category);
        }
    }
}
