package com.jiayantech.jyandroid.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.ShareBiz;
import com.jiayantech.library.utils.ToastUtil;

/**
 * Created by liangzili on 15/8/20.
 */
public class SharePanel extends PopupWindow implements View.OnClickListener{
    private Context mContext;
    private String mShareUrl;
    private String mTitle;

    private ImageView mShareToWechatImage;
    private ImageView mShareToCircleImage;
    private ImageView mShareToWeiboImage;
    private ImageView mShareToQZoneImage;

    private View mTransparentView;

    private Button mButtonCancel;

    public SharePanel(Context context, String url, String title){
        mContext = context;
        mShareUrl = url;
        mTitle = title;
        initView();
    }

    private void initView(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.window_share_panel, null);
        mShareToWechatImage = (ImageView)view.findViewById(R.id.image_wechat);
        mShareToWechatImage.setOnClickListener(this);

        mShareToCircleImage = (ImageView)view.findViewById(R.id.image_wechat_circle);
        mShareToCircleImage.setOnClickListener(this);

        mShareToWeiboImage = (ImageView)view.findViewById(R.id.image_weibo);
        mShareToWeiboImage.setOnClickListener(this);

        mShareToQZoneImage = (ImageView)view.findViewById(R.id.image_qzone);
        mShareToQZoneImage.setOnClickListener(this);

        mButtonCancel = (Button)view.findViewById(R.id.btn_cancel);
        mButtonCancel.setOnClickListener(this);

        mTransparentView = view.findViewById(R.id.transparent);
        mTransparentView.setOnClickListener(this);

        setContentView(view);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.transparent:
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.image_qzone:
            case R.id.image_weibo:
                ToastUtil.showMessage("暂不支持，稍后开放!");
                break;
            case R.id.image_wechat:
                ShareBiz.shareToWechat(mShareUrl, mTitle, ShareBiz.WECHAT_SESSION);
                break;
            case R.id.image_wechat_circle:
                ShareBiz.shareToWechat(mShareUrl, mTitle, ShareBiz.WECHAT_TIMELINE);
                break;
        }
    }
}
