package com.jiayantech.jyandroid.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.TopicBiz;
import com.jiayantech.jyandroid.utils.BitmapUtil;
import com.jiayantech.library.base.BaseModel;
import com.jiayantech.jyandroid.model.Post;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.FileUtil;
import com.jiayantech.library.utils.ToastUtil;

import java.io.File;

/**
 * Created by janseon on 2015/6/30.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class PublishPostActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 信息编辑请求码
     */
    static final int REQUEST_CODE_CAMERA = 1;
    /**
     * 信息编辑请求码
     */
    static final int REQUEST_CODE_LOCAL_IMG = 2;
    /**
     * 信息编辑请求码
     */
    static final int REQUEST_CODE_CROP_IMG = 3;

    protected View layout_photo;
    protected ImageView img_photo;
    protected EditText edit_content;
    protected TextView txt_category;

    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_post);
        findViews();
        setViewsContent();
        setViewsListener();
    }


    protected void findViews() {
        layout_photo = findViewById(R.id.layout_photo);
        img_photo = (ImageView) findViewById(R.id.img_photo);
        edit_content = (EditText) findViewById(R.id.edit_content);
        txt_category = (TextView) findViewById(R.id.txt_category);
    }

    protected void setViewsContent() {
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
                finish();
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

    protected void setViewsListener() {
        setDisplayHomeAsUpEnabled();
        layout_photo.setOnClickListener(this);
        txt_category.setOnClickListener(this);
    }

    protected void onPost(String content) {
        showProgressDialog();
        String categoryIds = null;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_photo:
                showUploadDialog(this, false);
                break;
            case R.id.txt_category:
                new AlertDialog.Builder(this)
                        .setTitle("选择分类")
                        .setItems(R.array.tab_title, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                break;
        }
    }

    private String tempFilePath;

    private void showUploadDialog(final Activity activity, boolean isTextShow) {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
                switch (v.getId()) {
                    case R.id.camera_button:
                        tempFilePath = FileUtil.getCachePath("trip", "trip.jpg");
                        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(tempFilePath));
                        startActivityForResult(cameraintent, REQUEST_CODE_CAMERA);
                        break;
                    case R.id.local_button:
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST_CODE_LOCAL_IMG);
                        break;
                }
            }
        };
        View view = LayoutInflater.from(activity).inflate(R.layout.view_upload_menu, null);
        if (isTextShow) {
            view.findViewById(R.id.title_text).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.title_text).setVisibility(View.GONE);
        }
        view.findViewById(R.id.camera_button).setOnClickListener(onClickListener);
        view.findViewById(R.id.local_button).setOnClickListener(onClickListener);
        view.findViewById(R.id.cancel_button).setOnClickListener(onClickListener);
        mDialog = showViewDialog(view, true);
    }


    private static Dialog showViewDialog(View view, boolean isBottom) {
        Dialog dialog = new Dialog(view.getContext());
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // dialog.getWindow().setSoftInputMode(4);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams localLayoutParams = window.getAttributes();
        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        localLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        // localLayoutParams.x = 0;
        // localLayoutParams.y = 0;
        // localLayoutParams.width = -1;
        // localLayoutParams.windowAnimations = R.anim.zoom_in;
        if (isBottom) {
            localLayoutParams.gravity = Gravity.BOTTOM;
        }
        // window.setWindowAnimations(R.style.animation_dialog_style); // 添加动画
        // window.setBackgroundDrawable(new ColorDrawable());
        window.setAttributes(localLayoutParams);
        // dialog.setOnDismissListener(onDismissListener);
        dialog.show();
        return dialog;
    }

    Dialog mDialog;

    public void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA:
                    upload();
                    break;
                case REQUEST_CODE_LOCAL_IMG:
                    //tempFilePath = BitmapUtil.getUriPath(this, data);
                    upload();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void upload() {
        if (tempFilePath == null || !(new File(tempFilePath)).exists()) {
            ToastUtil.showMessage("pic_not_exist");
            return;
        }
        img_photo.setVisibility(View.VISIBLE);
        Bitmap bmp = BitmapUtil.decodeSampledBitmapFromFilePath(tempFilePath, img_photo.getWidth());
        // Bitmap bmp = BitmapFactory.decodeFile(tempFile.getAbsolutePath());
        img_photo.setImageBitmap(bmp);
        img_photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
        showProgressDialog();
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                return BitmapUtil.compressBitmapFile(tempFilePath);
            }

            @Override
            protected void onPostExecute(String result) {
//                HttpReq.uploadImage(result, new ResponseListener() {
//                    @Override
//                    public void onResponse(Object response) {
//
//                    }
//                });
                dismissProgressDialog();
            }
        }.execute("");
    }
}
