package com.jiayantech.jyandroid.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.ImageAdapter;
import com.jiayantech.jyandroid.biz.TopicBiz;
import com.jiayantech.jyandroid.biz.UploadImageBiz;
import com.jiayantech.jyandroid.commons.Broadcasts;
import com.jiayantech.jyandroid.model.ImageUploadCallback;
import com.jiayantech.jyandroid.model.Login;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseModel;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.comm.PicGetter;
import com.jiayantech.library.helper.ActivityResultHelper;
import com.jiayantech.library.helper.BroadcastHelper;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpConfig;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.DialogUtils;
import com.jiayantech.library.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by janseon on 2015/6/30.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class PublishPostActivity extends BaseActivity implements View.OnClickListener, PicGetter.PicGetListener, BaseSimpleModelAdapter.OnItemClickListener<Bitmap> {
    private final int spanCount = 3;
    protected String UPLOAD_TYPE = "topic";

    protected RecyclerView recycler_view;
    protected ImageView img_photo;
    protected EditText edit_content;
    protected TextView txt_category;

    private ImageAdapter mImageAdapter;
    protected List<String> urlList = new ArrayList<>();
    private List<Login.Category> categorylList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_post);
        findViews();
        setViewsContent();
        setViewsListener();
    }

    protected void findViews() {
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        img_photo = (ImageView) findViewById(R.id.img_photo);
        edit_content = (EditText) findViewById(R.id.edit_content);
        txt_category = (TextView) findViewById(R.id.txt_category);
    }

    protected void setViewsContent() {
        setTitle(R.string.title_publish_topic);
        mImageAdapter = new ImageAdapter(null);
        recycler_view.setLayoutManager(new GridLayoutManager(this, spanCount));
    }

    protected void setViewsListener() {
        mImageAdapter.setOnItemClickListener(this);
        txt_category.setOnClickListener(this);
        mImageAdapter.resetGridHeight(recycler_view, spanCount);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.publish_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_publish:
                String content = edit_content.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showMessage("input content");
                    return true;
                }
                onPost(content);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_category:
                startActivityForResult(new Intent(this, SelectCategoryActivity.class), ActivityResult.REQUEST_CODE_DEFAUTE);
                mActivityResultHelper.addActivityResult(new ActivityResult() {
                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                        categorylList = data.getParcelableArrayListExtra(SelectCategoryActivity.KEY_categories);
                        txt_category.setText(Login.Category.toNamesString(categorylList));
                    }
                });
                break;
        }
    }

    protected void onPost(String content) {
        if (categorylList == null || categorylList.size() <= 0) {
            ToastUtil.showMessage("categoryIds null");
            return;
        }
        showProgressDialog();
        String categoryIds = Login.Category.toIdsString(categorylList);
        String photoUrls = toString(urlList);
        TopicBiz.create(categoryIds, content, photoUrls, new ResponseListener<AppResponse<BaseModel>>() {
            @Override
            public void onResponse(AppResponse<BaseModel> response) {
                dismissProgressDialog();
                BroadcastHelper.send(Broadcasts.ACTION_PUBLISH_TOPIC);
                finish();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                super.onErrorResponse(error);
            }
        });
    }


    protected String toString(List<String> urlList) {
        if (urlList.isEmpty()) {
            return "[]";
        }

        StringBuilder buffer = new StringBuilder(urlList.size() * 16);
        buffer.append('[');
        Iterator<?> it = urlList.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next != this) {
                buffer.append("\"").append(next).append("\"");
            } else {
                buffer.append("(this Collection)");
            }
            if (it.hasNext()) {
                buffer.append(", ");
            }
        }
        buffer.append(']');
        return buffer.toString();
    }

    /**
     * 选择照片后的回调接口的实现
     */
    @Override
    public void onPicGet(String path, Bitmap bitmap) {
        mImageAdapter.addImage(bitmap);
        mImageAdapter.resetViewHeight(recycler_view, spanCount);
        showProgressDialog();
        UploadImageBiz.uploadImage(UPLOAD_TYPE, path,
                new ResponseListener<ImageUploadCallback>() {
                    @Override
                    public void onResponse(ImageUploadCallback o) {
                        dismissProgressDialog();
                        urlList.add(HttpConfig.IMAGE_SHOW_URL + o.url);
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dismissProgressDialog();
                        super.onErrorResponse(error);
                    }
                });
    }

    @Override
    public void onItemClick(BaseSimpleModelAdapter<Bitmap> adapter, int position, Bitmap item) {
        if (position == mImageAdapter.getItemCount() - 1) {
            showUploadDialog();
        } else {
            urlList.remove(position);
            mImageAdapter.remove(position);
            mImageAdapter.resetViewHeight(recycler_view, spanCount);
        }
    }

    private void showUploadDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.view_upload_menu, null);
        final Dialog dialog = DialogUtils.showViewDialog(view, true);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                switch (v.getId()) {
                    case R.id.camera_button:
                        new PicGetter(_this(), mActivityResultHelper, _this()).startCamera();
                        break;
                    case R.id.local_button:
                        new PicGetter(_this(), mActivityResultHelper, _this()).startImage();
                        break;
                }
            }
        };
        view.findViewById(R.id.title_text).setVisibility(View.GONE);
        view.findViewById(R.id.camera_button).setOnClickListener(onClickListener);
        view.findViewById(R.id.local_button).setOnClickListener(onClickListener);
        view.findViewById(R.id.cancel_button).setOnClickListener(onClickListener);
    }

    protected ActivityResultHelper mActivityResultHelper = new ActivityResultHelper();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mActivityResultHelper.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private PublishPostActivity _this() {
        return this;
    }


}