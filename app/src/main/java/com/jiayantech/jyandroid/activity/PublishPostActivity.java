package com.jiayantech.jyandroid.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.model.Post;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.http.BitmapBiz;

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

    //private ImageView img_write;
    private View layout_photo;
    private ImageView img_photo;
    //private TextView txt_time;
    //private TextView txt_addr;
    private EditText edit_content;
    private Button btn_delete;

    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_track);
        findViews();
        setViewsContent();
        setViewsListener();
    }


    protected void findViews() {
        layout_photo = findViewById(R.id.layout_photo);
        img_photo = (ImageView) findViewById(R.id.img_photo);
        //img_write = (ImageView) findViewById(R.id.img_write);
        //txt_time = (TextView) findViewById(R.id.txt_time);
        //txt_addr = (TextView) findViewById(R.id.txt_addr);
        edit_content = (EditText) findViewById(R.id.edit_content);
        btn_delete = (Button) findViewById(R.id.btn_delete);
    }

    protected void setViewsContent() {
        setPost("", null);
    }

    private void setPost(String tripId, String json) {
        if (TextUtils.isEmpty(json)) {
            setTitle("add_trip_detail");
            post = new Post();
            btn_delete.setVisibility(View.GONE);
        } else {
            setTitle("edit_trip_detail");
            if (0 > 0) {
                img_photo.setVisibility(View.VISIBLE);
                BitmapBiz.display(img_photo, "");
            } else {
                img_photo.setVisibility(View.GONE);
            }
            //txt_time.setText("tripNoteDetail.TSectionTm");
            //txt_addr.setText("tripNoteDetail.CAddr");
            edit_content.setText("tripNoteDetail.CContent");
        }
        //tripNoteDetail.setCTripId(tripId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_diary:
//                String time = txt_time.getText().toString();
//                if (TextUtils.isEmpty(time)) {
//                    ToastUtil.showMessage("time null");
//                    return true;
//                }

                ProgressDialog dialog = new ProgressDialog(this);
                dialog.show();
//                DialogUtil.showRequestProgressDialog(_this());
//                AsyncBiz.updateTripNoteDtl(tripNoteDetail, new AsyncCallBack(TripNoteDetail.class) {
//                    @Override
//                    public void onSuccessResponse(Response t) {
//                        DialogUtil.dismissDialog();
//                        ToastUtil.showMessage(t.successMsg);
//                        if (TextUtils.isEmpty(tripNoteDetail.CPk)) {
//                            TripNoteDetail responseTripNoteDetail = (TripNoteDetail) t.obj;
//                            tripNoteDetail.setCPk(responseTripNoteDetail.CPk);
//                            tripNoteDetail.updateCImgPk(responseTripNoteDetail.CImg);
//                            title_view.setTitleText(R.string.edit_trip_detail);
//                            btn_delete.setVisibility(View.VISIBLE);
//                            BroadcastFilters.sendBroadcast(BroadcastFilters.ACTION_ADD_TRIP_DETAIL, tripNoteDetail);
//                        } else {
//                            BroadcastFilters.sendBroadcast(BroadcastFilters.ACTION_UPDATE_TRIP_DETAIL, tripNoteDetail);
//                        }
//                        finish();
//                    }
//                });
                return true;
            case R.id.action_topic:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setViewsListener() {
        setDisplayHomeAsUpEnabled();
        layout_photo.setOnClickListener(this);
        //img_write.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_photo:
                showUploadDialog(this, false);
                break;
            //case R.id.img_write:
            // IntentUtil.gotoActivity(this, MapActivity.class);
            //IntentUtil.gotoActivity(this, MapTracksActivity.class);
            //    break;
            case R.id.btn_delete:
//                DialogUtil.showRequestProgressDialog(this);
//                AsyncBiz.deleteTripNoteDtl(tripNoteDetail.CPk, new AsyncCallBack(AnnualFeeInfo.class) {
//                    @Override
//                    public void onSuccessResponse(Response t) {
//                        DialogUtil.dismissDialog();
//                        ToastUtil.showMessage(t.successMsg);
//                        tripNoteDetail = new TripNoteDetail();
//                        title_view.setTitleText(R.string.add_trip_detail);
//                        btn_delete.setVisibility(View.GONE);
//                        BroadcastFilters.sendBroadcast(BroadcastFilters.ACTION_DEL_TRIP_DETAIL, tripNoteDetail);
//                        finish();
//                    }
//                });
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
