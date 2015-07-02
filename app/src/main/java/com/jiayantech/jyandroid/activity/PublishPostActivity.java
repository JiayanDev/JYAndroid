package com.jiayantech.jyandroid.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.TopicBiz;
import com.jiayantech.jyandroid.model.BaseModel;
import com.jiayantech.jyandroid.model.Post;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;

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

    private View layout_photo;
    private ImageView img_photo;
    private EditText edit_content;
    private TextView txt_category;

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
//                String time = txt_time.getText().toString();
//                if (TextUtils.isEmpty(time)) {
//                    ToastUtil.showMessage("time null");
//                    return true;
//                }

                showProgressDialog();
                String categoryId = "11";
                String content = "11";
                String photoUrls = "[]";
                TopicBiz.create(categoryId, content, photoUrls, new ResponseListener<AppResponse<BaseModel>>() {
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setViewsListener() {
        setDisplayHomeAsUpEnabled();
        layout_photo.setOnClickListener(this);
        txt_category.setOnClickListener(this);
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
//                DialogUtil.dismissDialog();
//                switch (v.getId()) {
//                    case R.id.camera_button:
//                        tempFilePath = FileUtil.getCachePath("trip", "trip.jpg");
//                        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(tempFilePath));
//                        startActivityForResult(cameraintent, REQUEST_CODE_CAMERA);
//                        break;
//                    case R.id.local_button:
//                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                        intent.addCategory(Intent.CATEGORY_OPENABLE);
//                        intent.setType("image/*");
//                        startActivityForResult(intent, REQUEST_CODE_LOCAL_IMG);
//                        break;
//                }
            }
        };
//        View view = LayoutInflater.from(activity).inflate(R.layout.view_upload_menu, null);
//        if (isTextShow) {
//            view.findViewById(R.id.title_text).setVisibility(View.VISIBLE);
//        } else {
//            view.findViewById(R.id.title_text).setVisibility(View.GONE);
//        }
//        view.findViewById(R.id.camera_button).setOnClickListener(onClickListener);
//        view.findViewById(R.id.local_button).setOnClickListener(onClickListener);
//        view.findViewById(R.id.cancel_button).setOnClickListener(onClickListener);
//        DialogUtil.showBottomViewDialog(view);
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
//        if (tempFilePath == null || !(new File(tempFilePath)).exists()) {
//            ToastUtil.showMessage(getString(R.string.pic_not_exist));
//            return;
//        }
//        img_photo.setVisibility(View.VISIBLE);
//        Bitmap bmp = BitmapUtil.decodeSampledBitmapFromFilePath(tempFilePath, img_photo.getWidth());
//        // Bitmap bmp = BitmapFactory.decodeFile(tempFile.getAbsolutePath());
//        img_photo.setImageBitmap(bmp);
//        img_photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        DialogUtil.showUploadProgressDialog(this);
//        new AsyncTask<String, Integer, String>() {
//            @Override
//            protected String doInBackground(String... params) {
//                return BitmapUtil.compressBitmapFile(tempFilePath);
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                HttpBiz.upload(new File(result), new AsyncCallBack() {
//                    @Override
//                    public void onSuccessResponse(Response t) {
//                        DialogUtil.dismissDialog();
//                        if (t.files != null && t.files.size() > 0) {
//                            Img img = new Img();
//                            img.setCPath(t.files.get(0));
//                            tripNoteDetail.addImg(img);
//                            img.setCType("1");
//                        }
//                    }
//                });
//            }
//        }.execute("");
    }
}
