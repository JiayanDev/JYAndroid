package com.jiayantech.library.http.imageupload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jiayantech.library.utils.FileUtil;

import java.io.ByteArrayOutputStream;
import java.net.URLConnection;

/**
 * Created by liangzili on 15/6/30.
 */
public class FormImage {
    private static final String FORM_NAME = "image";

    public static final int COMPRESS_FACTOR = 80;
    public static final Bitmap.CompressFormat IMAGE_FORMAT = Bitmap.CompressFormat.JPEG;

    private String mFileName;
    private String mName;
    private String mFilePath;
    private String mMime;
    private Bitmap mBitmap;

    public FormImage(Bitmap bitmap, String fileName) {
        this(bitmap, fileName, FORM_NAME);
    }

    public FormImage(Bitmap bitmap, String fileName, String name) {
        mBitmap = bitmap;
        mFileName = fileName;
        mMime = "image/png";
        mName = name;
    }

    public FormImage(String path) {
        this(path, FORM_NAME);
    }

    public FormImage(String path, String name) {
        mFilePath = path;
        mFileName = FileUtil.getFileName(path);
        mMime = URLConnection.guessContentTypeFromName(path);
        mName = name;
        //mBitmap = BitmapFactory.decodeFile(mFilePath);
    }

    public String getFileName() {
        return mFileName;
    }

    public String getMime() {
        return mMime;
    }

    public String getName() {
        return mName;
    }

    public byte[] getValue() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(null == mBitmap){
            mBitmap = BitmapFactory.decodeFile(mFilePath);
        }
        mBitmap.compress(IMAGE_FORMAT, COMPRESS_FACTOR, baos);
        return baos.toByteArray();
    }
//        else{
//            byte[] byteContent = null;
//            FileInputStream fis = null;
//            File file;
//            try {
//                file = new File(mFilePath);
//                fis = new FileInputStream(file);
//                if(file.length() > Integer.MAX_VALUE){
//                    return null;
//                }
//                byteContent = new byte[(int)file.length()];
//                fis.read(byteContent);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }catch (IOException e){
//                e.printStackTrace();
//            }finally {
//                try {
//                    if(fis != null) {
//                        fis.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return byteContent;
//            }
//        }

}
