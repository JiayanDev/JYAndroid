package com.jiayantech.jyandroid.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.ImageAdapter;
import com.jiayantech.jyandroid.biz.TopicBiz;
import com.jiayantech.jyandroid.manager.UserManger;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseModel;
import com.jiayantech.library.comm.PicGetter;
import com.jiayantech.library.helper.ActivityResultHelper;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.DialogUtils;
import com.jiayantech.library.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janseon on 2015/6/30.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class PublishPostActivity extends BaseActivity implements View.OnClickListener, PicGetter.PicGetListener, AdapterView.OnItemClickListener {
    protected GridView grid_image;
    protected ImageView img_photo;
    protected EditText edit_content;
    protected TextView txt_category;

    private ImageAdapter mImageAdapter;
    private List<String> urlList = new ArrayList<>();
    private List<String> idlList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_post);
        findViews();
        setViewsContent();
        setViewsListener();
    }

    protected void findViews() {
        grid_image = (GridView) findViewById(R.id.grid_image);
        img_photo = (ImageView) findViewById(R.id.img_photo);
        edit_content = (EditText) findViewById(R.id.edit_content);
        txt_category = (TextView) findViewById(R.id.txt_category);
    }

    protected void setViewsContent() {
        mImageAdapter = new ImageAdapter(this, null);
    }

    protected void setViewsListener() {
        setDisplayHomeAsUpEnabled();
        grid_image.setOnItemClickListener(this);
        txt_category.setOnClickListener(this);
        grid_image.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                grid_image.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mImageAdapter.setItemHeight(grid_image.getWidth() / 4);
                grid_image.setAdapter(mImageAdapter);
            }
        });
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
                String[] items = new String[UserManger.sProjectCategoryTopLevels.size()];
                for (int i = 0; i < items.length; i++) {
                    items[i] = UserManger.sProjectCategoryData.get(UserManger.sProjectCategoryTopLevels.get(i));
                }
                new AlertDialog.Builder(this)
                        .setTitle("选择分类")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = UserManger.sProjectCategoryTopLevels.get(which);
                                idlList.add(id);
                                txt_category.setText(getCategoryText());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }
    }


    protected void onPost(String content) {
        if (idlList.size() <= 0) {
            ToastUtil.showMessage("categoryIds null");
            return;
        }
        showProgressDialog();
        String categoryIds = idlList.toString();
        String photoUrls = null;
        TopicBiz.create(categoryIds, content, photoUrls, new ResponseListener<AppResponse<BaseModel>>() {
            @Override
            public void onResponse(AppResponse<BaseModel> response) {
                BaseModel model = response.data;
                dismissProgressDialog();
                ToastUtil.showMessage("id=" + model.id);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
                dismissProgressDialog();
            }
        });
    }

    /**
     * 选择照片后的回调接口的实现
     */
    @Override
    public void onPicGet(String path, Bitmap bitmap) {
        urlList.add(path);
        mImageAdapter.add(bitmap);
        mImageAdapter.resetGridViewHeight(grid_image);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == mImageAdapter.getCount() - 1) {
            showUploadDialog();
        } else {
            urlList.remove(position);
            mImageAdapter.remove(position);
            mImageAdapter.resetGridViewHeight(grid_image);
        }
    }

    private String getCategoryText() {
        String items = "";
        for (String id : idlList) {
            items += UserManger.sProjectCategoryData.get(id) + " ";
        }
        return items;
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

    private ActivityResultHelper mActivityResultHelper = new ActivityResultHelper();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mActivityResultHelper.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private PublishPostActivity _this() {
        return this;
    }
}