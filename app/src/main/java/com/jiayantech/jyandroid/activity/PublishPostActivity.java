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
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.ImageAdapter;
import com.jiayantech.jyandroid.biz.TopicBiz;
import com.jiayantech.jyandroid.manager.UserManger;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseApplication;
import com.jiayantech.library.base.BaseModel;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.comm.PicGetter;
import com.jiayantech.library.helper.ActivityResultHelper;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.RequestMap;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.http.UploadReq;
import com.jiayantech.library.utils.DialogUtils;
import com.jiayantech.library.utils.ToastUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
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
    protected RecyclerView recycler_view;
    protected ImageView img_photo;
    protected EditText edit_content;
    protected TextView txt_category;

    private ImageAdapter mImageAdapter;
    private List<String> urlList = new ArrayList<>();
    private List<String> idlList;

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
        mImageAdapter = new ImageAdapter(null);
        recycler_view.setLayoutManager(new GridLayoutManager(this, spanCount));
    }

    protected void setViewsListener() {
        setDisplayHomeAsUpEnabled();
        mImageAdapter.setOnItemClickListener(this);
        txt_category.setOnClickListener(this);
        recycler_view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recycler_view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                recycler_view.setAdapter(mImageAdapter);
                mImageAdapter.setItemHeight(recycler_view.getWidth() / spanCount);
                mImageAdapter.resetViewHeight(recycler_view, spanCount);
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
//                String[] items = new String[UserManger.sProjectCategoryTopLevels.size()];
//                for (int i = 0; i < items.length; i++) {
//                    items[i] = UserManger.sProjectCategoryData.get(UserManger.sProjectCategoryTopLevels.get(i));
//                }
//                new AlertDialog.Builder(this)
//                        .setTitle("选择分类")
//                        .setItems(items, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                String id = UserManger.sProjectCategoryTopLevels.get(which);
//                                idlList.add(id);
//                                txt_category.setText(getCategoryText());
//                            }
//                        })
//                        .setNegativeButton("取消", null)
//                        .show();
                startActivityForResult(new Intent(this, SelectCategoryActivity.class), ActivityResult.REQUEST_CODE_DEFAUTE);
                mActivityResultHelper.addActivityResult(new ActivityResult() {
                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                        idlList = data.getStringArrayListExtra(SelectCategoryActivity.KEY_categoryIds);
                        List<String> categoryNames = data.getStringArrayListExtra(SelectCategoryActivity.KEY_categoryNames);
                        txt_category.setText(categoryNames.toString());
                    }
                });
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
        mImageAdapter.addImage(bitmap);
        mImageAdapter.resetViewHeight(recycler_view, spanCount);
//        HttpReq.uploadImage(path, new ResponseListener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//            }
//        });
        RequestMap requestMap = new RequestMap();
        requestMap.put("image", new File(path));
        requestMap.put("policy","eyJidWNrZXQiOiAiamlheWFuaW1nIiwgImV4cGlyYXRpb24iOiAxNDM2NTE3NjI1MDM5LCAic2F2ZS1rZXkiOiAidGVzdC9hdmF0YXIvOC9hdmF0YXIucG5nIn0=");
        requestMap.put("signature", "aaefb280754ca70b34684d3d368e506c");
        UploadReq.request(requestMap, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse networkResponse) {
                Toast.makeText(BaseApplication.getContext(), networkResponse.toString(), Toast.LENGTH_SHORT).show();
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