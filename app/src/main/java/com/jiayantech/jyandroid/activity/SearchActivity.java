package com.jiayantech.jyandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.EventAdapter;
import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.jyandroid.model.User;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.comm.ActivityResult;
import com.quinny898.library.persistentsearch.SearchBox;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by janseon on 2015/7/3.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class SearchActivity extends BaseActivity implements SearchBox.SearchListener {
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final int REQUEST_CODE_SELECT = 0x100;

    private SearchBox mSearchBox;
    private EditText search;
    private RecyclerView mRecyclerView;
    private EventAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
    }

    protected void initViews() {
        getSupportActionBar().hide();
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mSearchBox = (SearchBox) findViewById(R.id.searchBox);
        search = (EditText) mSearchBox.findViewById(R.id.search);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        search.setHint("search_hint_company");
        mSearchBox.setLogoText("");
        //mSearchBox.setShouldOpenKeyboard(false);
        mSearchBox.postDelayed(new Runnable() {
            @Override
            public void run() {
                openSearchBox();
            }
        }, 600);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void openSearchBox() {
        mSearchBox.revealFromMenuItem(R.id.action_search, this);
        mSearchBox.setSearchListener(this);
    }

    @Override
    public void onSearchOpened() {
        new SearchTask().execute();
    }

    @Override
    public void onSearchCleared() {
    }

    @Override
    public void onSearchClosed() {
        close();
        onBackPressed();
    }

    public void close() {
        mSearchBox.hideCircularly(this);
        hideSoftKeyboard();
    }

    @Override
    public void onSearchTermChanged() {
        new SearchTask().execute(mSearchBox.getSearchText());
    }

    @Override
    public void onSearch(String result) {
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
    }

    public class SearchTask extends AsyncTask<String, Void, ArrayList<Event>> {
        @Override
        protected ArrayList<Event> doInBackground(String... params) {
            SystemClock.sleep(100);
            ArrayList<Event> list = new ArrayList<>();
            int count = Math.abs(new Random().nextInt()) % 30;
            for (int i = 0; i < count; i++) {
                Event event = new Event();
                event.categoryName = "firstName" + new Random().nextInt();
                list.add(event);
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Event> result) {
            if (result != null) {
                mAdapter = new EventAdapter(SearchActivity.this, result);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new BaseSimpleModelAdapter.OnItemClickListener<Event>() {
                    @Override
                    public void onItemClick(BaseSimpleModelAdapter<Event> adapter, int position, Event event) {
                        close();
                        Intent intent = new Intent();
                        intent.putExtra(KEY_ID, event.id);
                        intent.putExtra(KEY_NAME, event.categoryName);
                        ActivityResult.onFinishResult(SearchActivity.this, intent);
                    }
                });
            }
        }
    }

    public static void launchActivity(Fragment fragment) {
        Intent intent = new Intent(fragment.getActivity(), SearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        fragment.startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    public static void launchActivity(Activity activity) {
        Intent intent = new Intent(activity, SearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        activity.startActivityForResult(intent, REQUEST_CODE_SELECT);
    }
}

