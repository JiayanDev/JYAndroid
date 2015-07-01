package com.jiayantech.library.comm.imageupload;

import android.graphics.Bitmap;

import com.jiayantech.library.utils.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;

/**
 * Created by liangzili on 15/6/30.
 */
public class FormImage {
    public static final int TYPE_FILE = 0;
    public static final int TYPE_BITMAP = 1;
    private static final String FORM_NAME = "image";

    private String mFileName;
    private String mName;
    private String mFilePath;
    private String mMime;
    private Bitmap mBitmap;
    private int mImageType;

    public FormImage(Bitmap bitmap, String fileName){
        this(bitmap, fileName, FORM_NAME);
    }

    public FormImage(Bitmap bitmap, String fileName, String name){
        mBitmap = bitmap;
        mImageType = 1;
        mFileName = fileName;
        mMime = "image/png";
        mName = name;
    }

    public FormImage(String path){
        this(path, FORM_NAME);
    }

    public FormImage(String path, String name){
        mFilePath = path;
        mFileName = FileUtil.getFileName(path);
        mImageType = 0;
        mMime = URLConnection.guessContentTypeFromName(path);
        mName = name;
    }

    public String getFileName(){
        return mFileName;
    }

    public String getMime(){
        return mMime;
    }

    public String getName(){
        return mName;
    }

    public byte[] getValue(){
        if(mImageType == TYPE_BITMAP) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            return baos.toByteArray();
        }else{
            byte[] byteContent = null;
            FileInputStream fis = null;
            File file;
            try {
                file = new File(mFilePath);
                fis = new FileInputStream(file);
                if(file.length() > Integer.MAX_VALUE){
                    return null;
                }
                byteContent = new byte[(int)file.length()];
                fis.read(byteContent);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    if(fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return byteContent;
            }
        }
    }

}
