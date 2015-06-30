package com.jiayantech.library.comm.imageupload;

/**
 * Created by liangzili on 15/6/30.
 * 当上传图片完成时调用，成功时调用{@link #onUploadSucess(int, String, String)},
 * 失败时调用{@link #onUploadFail(int, String, String)}
 */
public interface OnUpLoadCompleteListener {

    /**
     *
     * @param imageCode 自定义的图片的编号，用于标识
     * @param localPath 所上传图片的本地路径
     * @param remotePath 上传服务器后图片的路径
     */
    void onUploadSucess(int imageCode, String localPath, String remotePath);

    /**
     *
     * @param imageCode 自定义的图片的编号，用于标识
     * @param localPath 所上传图片的本地路径
     * @param errMsg 出错信息
     */
    void onUploadFail(int imageCode, String localPath, String errMsg);
}
