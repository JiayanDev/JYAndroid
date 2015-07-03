//package com.jiayantech.jyandroid.utils;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.sql.Date;
//import java.text.SimpleDateFormat;
//
//import net.tsz.afinal.bitmap.core.BitmapDecoder;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
////import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//
///**
// * @author 健兴
// * @version 1.0
// * @Description 总相册或者相机选择照片
// * @date 2014-7-4
// * @Copyright: Copyright (c) 2014 Shenzhen Inser Technology Co., Ltd. Inc. All
// * rights reserved.
// */
//public class PicGetter {
//    /**
//     * 相机
//     */
//    private static final int REQUEST_CODE_CAMERA = 1;
//    /**
//     * 相册
//     */
//    private static final int REQUEST_CODE_LOCAL_IMG = 2;
//    /**
//     * 相机、裁剪
//     */
//    private static final int REQUEST_CODE_CROP_CAMERA = 2;
//    /**
//     * 相册 、裁剪
//     */
//    private static final int REQUEST_CODE_CROP_LOCAL_IMG = 3;
//    /**
//     * 裁剪
//     */
//    private static final int REQUEST_CODE_CROP_IMG = 4;
//
//    private Activity mContext;
//
//    private static int reqWidth = (int) Density.getScreenWidth();
//    private static int reqHeight = (int) Density.getScreenHeight();
//
//    public PicGetter(Context context, PicGetListener l) {
//        mContext = (Activity) context;
//        mPicGetListener = l;
//    }
//
//    private void addActivityResult(ActivityResult activityResult) {
//        if (mContext instanceof BaseFragmentActivity) {
//            ((BaseFragmentActivity) mContext).addActivityResult(activityResult);
//        } else {
//            ((BaseActivity) mContext).addActivityResult(activityResult);
//        }
//    }
//
//    /**
//     * 描述：从相机去选择照片
//     *
//     * @version 1.0
//     * @createTime 2014-7-4 下午2:28:22
//     * @createAuthor 健兴
//     * @updateTime 2014-7-4 下午2:28:22
//     * @updateAuthor 健兴
//     * @updateInfo (此处输入修改内容, 若无修改可不写.)
//     */
//    public void startCamera() {
//        final Uri uri = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
//        if (uri != null) {
//            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//            mContext.startActivityForResult(i, REQUEST_CODE_CAMERA);
//            addActivityResult(new ActivityResult(REQUEST_CODE_CAMERA) {
//                @Override
//                public void onActivityResult(Intent data) {
//                    if (mPicGetListener != null) {
//                        String path = getUriPath(mContext, uri);
//                        Bitmap bitmap = decodeBitmapFromPath(path);
//                        // Bitmap bitmap = BitmapFactory.decodeFile(path);
//                        mPicGetListener.onPicGet(path, bitmap);
//                    } else {
//                        ToastUtil.showMessage(mContext, "no photo");
//                    }
//                }
//            });
//        } else {
//            ToastUtil.showMessage(mContext, "cant insert album)");
//        }
//    }
//
//    /**
//     * 描述：从相册选择照片
//     *
//     * @version 1.0
//     * @createTime 2014-7-4 下午2:28:52
//     * @createAuthor 健兴
//     * @updateTime 2014-7-4 下午2:28:52
//     * @updateAuthor 健兴
//     * @updateInfo (此处输入修改内容, 若无修改可不写.)
//     */
//    public void startImage() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");
//        mContext.startActivityForResult(intent, REQUEST_CODE_LOCAL_IMG);
//        addActivityResult(new ActivityResult(REQUEST_CODE_LOCAL_IMG) {
//            @Override
//            public void onActivityResult(Intent data) {
//                if (mPicGetListener != null) {
//                    String path = getUriPath(mContext, data.getData());
//                    Bitmap bitmap = decodeBitmapFromPath(path);
//                    // Bitmap bitmap = BitmapFactory.decodeFile(path);
//                    mPicGetListener.onPicGet(path, bitmap);
//                }
//            }
//        });
//    }
//
//    /**
//     * 描述：从相机去选择照片，并且进行裁剪操作
//     *
//     * @version 1.0
//     * @createTime 2014-7-4 下午2:28:22
//     * @createAuthor 健兴
//     * @updateTime 2014-7-4 下午2:28:22
//     * @updateAuthor 健兴
//     * @updateInfo (此处输入修改内容, 若无修改可不写.)
//     */
//    public void startCropCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File tempFile = new File(FileUtil.getCachePath("camera", getTimeMillisName()));
//        final Uri uri = Uri.fromFile(tempFile);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        mContext.startActivityForResult(intent, REQUEST_CODE_CROP_CAMERA);
//        addActivityResult(new ActivityResult(REQUEST_CODE_CROP_CAMERA) {
//            @Override
//            public void onActivityResult(Intent data) {
//                startPhotoZoom(uri);
//            }
//        });
//    }
//
//    /**
//     * 描述：从相册选择照片，并且进行裁剪
//     *
//     * @version 1.0
//     * @createTime 2014-7-4 下午2:28:52
//     * @createAuthor 健兴
//     * @updateTime 2014-7-4 下午2:28:52
//     * @updateAuthor 健兴
//     * @updateInfo (此处输入修改内容, 若无修改可不写.)
//     */
//    public void startCropImage() {
//        final File tempFile = new File(FileUtil.getCachePath("upload", getTimeMillisName()));
//        Intent intent = new Intent("android.intent.action.PICK");
//        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
//        intent.putExtra("output", Uri.fromFile(tempFile));
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);// 裁剪框比例
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", 150);// 输出图片大小
//        intent.putExtra("outputY", 150);
//        mContext.startActivityForResult(intent, REQUEST_CODE_CROP_LOCAL_IMG);
//        addActivityResult(new ActivityResult(REQUEST_CODE_CROP_LOCAL_IMG) {
//            @Override
//            public void onActivityResult(Intent data) {
//                if (mPicGetListener != null) {
//                    if (tempFile.exists()) {// 如果图片存在
//                        String path = tempFile.getAbsolutePath();
//                        Bitmap bitmap = decodeBitmapFromPath(path);
//                        // Bitmap bitmap = BitmapFactory.decodeFile(path);
//                        mPicGetListener.onPicGet(path, bitmap);
//                    }
//                }
//            }
//        });
//    }
//
//    /**
//     * 裁剪图片方法实现
//     *
//     * @param uri
//     */
//    public void startPhotoZoom(Uri uri) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", 150);
//        intent.putExtra("outputY", 150);
//        intent.putExtra("return-data", true);
//        mContext.startActivityForResult(intent, REQUEST_CODE_CROP_IMG);
//        addActivityResult(new ActivityResult(REQUEST_CODE_CROP_IMG) {
//            @SuppressLint("SimpleDateFormat")
//            @Override
//            public void onActivityResult(Intent data) {
//                if (mPicGetListener != null) {
//                    Bundle extras = data.getExtras();
//                    if (extras != null) {
//                        Bitmap bitmap = extras.getParcelable("data");
//                        String path = savePicture(bitmap, "upload", getTimeMillisName());
//                        mPicGetListener.onPicGet(path, bitmap);
//                    }
//                }
//            }
//        });
//    }
//
//    public static Bitmap decodeBitmapFromPath(String path) {
//        return BitmapDecoder.decodeSampledBitmapFromPath(path, reqWidth, reqHeight);
//    }
//
//    /**
//     * 描述：根据uri从 数据库中查询出对应的图片文件路径
//     *
//     * @param activity
//     * @param uri
//     * @return
//     * @version 1.0
//     * @createTime 2014-7-4 下午2:30:03
//     * @createAuthor 健兴
//     * @updateTime 2014-7-4 下午2:30:03
//     * @updateAuthor 健兴
//     * @updateInfo (此处输入修改内容, 若无修改可不写.)
//     */
//    public static String getUriPath(Activity activity, Uri uri) {
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor cursor = activity.managedQuery(uri, proj, // Which
//                // columns
//                // to return
//                null, // WHERE clause; which rows to return (all rows)
//                null, // WHERE clause selection arguments (none)
//                null); // Order-by clause (ascending by name)
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String path = cursor.getString(column_index);
//        return path;
//    }
//
//    /**
//     * 描述： 保存图片
//     *
//     * @param bitmap
//     * @param dir
//     * @param fileName
//     * @return
//     * @version 1.0
//     * @createTime 2014-7-4 下午2:30:50
//     * @createAuthor 健兴
//     * @updateTime 2014-7-4 下午2:30:50
//     * @updateAuthor 健兴
//     * @updateInfo (此处输入修改内容, 若无修改可不写.)
//     */
//    public static String savePicture(Bitmap bitmap, String dir, String fileName) {
//        if (bitmap == null || dir == null || dir.equals("") || fileName == null || fileName.equals("")) {
//            return null;
//        }
//        FileOutputStream iStream = null;
//        try {
//            String filePath = FileUtil.getCachePath(dir, fileName);
//            File fImage = new File(filePath);
//            if (!fImage.exists()) {
//                fImage.createNewFile();
//            }
//            iStream = new FileOutputStream(fImage);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, iStream);
//            iStream.flush();
//            iStream.close();
//            return filePath;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 描述：根据当前时间戳，生成随机文件名
//     *
//     * @return
//     * @version 1.0
//     * @createTime 2014-7-4 下午2:31:24
//     * @createAuthor 健兴
//     * @updateTime 2014-7-4 下午2:31:24
//     * @updateAuthor 健兴
//     * @updateInfo (此处输入修改内容, 若无修改可不写.)
//     */
//    @SuppressLint("SimpleDateFormat")
//    public static String getTimeMillisName() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        return sdf.format(new Date(System.currentTimeMillis())) + ".png";
//    }
//
//    private PicGetListener mPicGetListener;
//
//    /**
//     * @author 健兴
//     * @version 1.0
//     * @Description 选择了照片之后的回调接口
//     * @date 2014-7-4
//     * @Copyright: Copyright (c) 2014 Shenzhen Inser Technology Co., Ltd. Inc.
//     * All rights reserved.
//     */
//    public interface PicGetListener {
//        public void onPicGet(String path, Bitmap bitmap);
//    }
//}
