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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.ImageAdapter;
import com.jiayantech.jyandroid.biz.TopicBiz;
import com.jiayantech.jyandroid.biz.UploadImageBiz;
import com.jiayantech.jyandroid.commons.Broadcasts;
import com.jiayantech.jyandroid.model.ImageUploadResp;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseModel;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.comm.PicGetter;
import com.jiayantech.library.helper.BroadcastHelper;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpConfig;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.DialogUtils;
import com.jiayantech.library.utils.ToastUtil;

import java.io.File;
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
    private final int spanCount = 4;
    private final String UPLOAD_TYPE_TOPIC = "topic";
    protected String UPLOAD_TYPE = UPLOAD_TYPE_TOPIC;

    protected RecyclerView recycler_view;
    protected ImageView img_photo;
    protected EditText edit_content;
    protected TextView txt_category;

    protected ImageAdapter mImageAdapter;
    private List<AppInit.Category> categoryList;

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
                uploadImage(0);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void post() {
        String content = edit_content.getText().toString();
        onPost(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_category:
                startActivityForResult(new Intent(this, SelectCategoryActivity.class), new ActivityResult() {
                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                        categoryList = data.getParcelableArrayListExtra(SelectCategoryActivity.KEY_categories);
                        txt_category.setText(AppInit.Category.toNamesString(categoryList));
                    }
                });
                break;
        }
    }

    protected void onPost(String content) {
        if (categoryList == null || categoryList.size() <= 0) {
            ToastUtil.showMessage("categoryIds null");
            return;
        }
        showProgressDialog();
        String categoryIds = categoryList.toString();
        String photoUrls = toString(mImageAdapter.urlList);
        TopicBiz.create(categoryIds, content, photoUrls, new ResponseListener<AppResponse<BaseModel>>() {
            @Override
            public void onResponse(AppResponse<BaseModel> response) {
                //dismissProgressDialog();
                BroadcastHelper.send(Broadcasts.ACTION_PUBLISH_TOPIC);
                //finish();
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
        UploadImageBiz.uploadImage(UPLOAD_TYPE, bitmap, new File(path).getName(),
                new ResponseListener<ImageUploadResp>() {
                    @Override
                    public void onResponse(ImageUploadResp o) {
                        dismissProgressDialog();
                        mImageAdapter.urlList.add(HttpConfig.IMAGE_SHOW_URL + o.url);
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
            mImageAdapter.urlList.remove(position);
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
                int maxNum = mImageAdapter.getCurMaxCount();
                if (maxNum <= 0) {
                    ToastUtil.showMessage(R.string.msg_amount_limit);
                    return;
                }
                switch (v.getId()) {
                    case R.id.camera_button:
                        new PicGetter(_this(), getActivityResultHelper(), _this()).startCamera();
                        break;
                    case R.id.local_button:
                        //new PicGetter(_this(), getActivityResultHelper(), _this()).startImage();
                        selectMultiImage(maxNum);
                        break;
                }
            }
        };
        view.findViewById(R.id.title_text).setVisibility(View.GONE);
        view.findViewById(R.id.camera_button).setOnClickListener(onClickListener);
        view.findViewById(R.id.local_button).setOnClickListener(onClickListener);
        view.findViewById(R.id.cancel_button).setOnClickListener(onClickListener);
    }

    private PublishPostActivity _this() {
        return this;
    }

    private ArrayList<String> mSelectPath;

    private void selectMultiImage(int maxNum) {
        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
        boolean showCamera = false;

        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, showCamera);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
        if (mSelectPath != null && mSelectPath.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        startActivityForResult(intent, new ActivityResult() {
            @Override
            public void onActivityResult(Intent data) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                onPicGets();
            }
        });
    }

    /**
     * 选择照片后的回调接口的实现
     */
    public void onPicGets() {
        ArrayList<Bitmap> mSelectBitmap = new ArrayList<>();
        for (String p : mSelectPath) {
            Bitmap bitmap = PicGetter.decodeBitmapFromPath(p);
            mSelectBitmap.add(bitmap);
            mImageAdapter.addImage(bitmap);
        }
        mImageAdapter.resetViewHeight(recycler_view, spanCount);
        //showProgressDialog();
        //finish();
        //uploadImage(0);
    }

    public void uploadImage(final int index) {
        if (mSelectPath.size() <= 0) {
            //dismissProgressDialog();
            //ToastUtil.showMessage(mImageAdapter.urlList.toString());
            post();
            return;
        }
        Bitmap bitmap = mImageAdapter.getItem(index);
        String path = mSelectPath.get(0);
        UploadImageBiz.uploadImage(UPLOAD_TYPE, bitmap, new File(path).getName(),
                new ResponseListener<ImageUploadResp>() {
                    @Override
                    public void onResponse(ImageUploadResp o) {
                        mImageAdapter.urlList.add(HttpConfig.IMAGE_SHOW_URL + o.url);
                        mSelectPath.remove(0);
                        uploadImage(index + 1);
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //dismissProgressDialog();
                        super.onErrorResponse(error);
                    }
                });
    }
}