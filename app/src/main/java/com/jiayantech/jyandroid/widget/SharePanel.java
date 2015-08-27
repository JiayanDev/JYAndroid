package com.jiayantech.jyandroid.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ViewGroup mShareLayout;
    private View mBackground;

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
        mShareLayout = (ViewGroup)view.findViewById(R.id.layout_share);
        mBackground = (View)view.findViewById(R.id.transparent);

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

        //setAnimationStyle(R.style.share_panel_anim);
    }

    @Override
    public void dismiss() {
        //super.dismiss();
        Animator anim = AnimatorInflater.loadAnimator(mContext, R.animator.share_panel_hide);
        anim.setTarget(mShareLayout);
        final Drawable backgroundColor = mBackground.getBackground();
        final float translationY = mShareLayout.getTranslationY();

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mBackground.setBackgroundResource(android.R.color.transparent);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                SharePanel.super.dismiss();
                mBackground.setBackgroundDrawable(backgroundColor);
                mShareLayout.setTranslationY(translationY);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
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
