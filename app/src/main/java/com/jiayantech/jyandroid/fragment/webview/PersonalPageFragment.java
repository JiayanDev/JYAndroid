package com.jiayantech.jyandroid.fragment.webview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.JsNativeBiz;
import com.jiayantech.jyandroid.commons.Broadcasts;
import com.jiayantech.jyandroid.eventbus.AddPostFinishEvent;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.jyandroid.model.web.JsCallShowHeader;
import com.jiayantech.jyandroid.widget.NotifyingScrollView;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.helper.BroadcastHelper;
import com.jiayantech.library.http.BitmapBiz;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.utils.BitmapUtil;

import java.util.Map;

/**
 * Created by liangzili on 15/8/10.
 */
public class PersonalPageFragment extends WebViewOverlayFragment {
    private Drawable mToolbarBackgroundDrawable;

    private ImageView mImgAvatar;
    private TextView mTxtUsername;
    private TextView mTxtInfo;
    private RelativeLayout mBgLayout;

    public static PersonalPageFragment newInstance(long userId, String userName) {
        PersonalPageFragment fragment = new PersonalPageFragment();
        Bundle args = new Bundle();
        args.putLong(WebViewFragment.EXTRA_ID, userId);
        args.putString(WebViewFragment.EXTRA_TYPE, WebConstans.Type.TYPE_PERSONAL_PAGE);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbarBackgroundDrawable = new ColorDrawable(Color.WHITE);
        mToolbarBackgroundDrawable.setAlpha(0);

        ((BaseActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(mToolbarBackgroundDrawable);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mToolbarBackgroundDrawable.setCallback(mDrawableCallback);
        }

        mBroadcastHelper.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                callJsMethod(JsNativeBiz.JS_METHOD_G_REFRESH_TIMELINE, null);
            }
        }, Broadcasts.ACTION_PUBLISH_TOPIC, Broadcasts.ACTION_PUBLISH_DIARY, Broadcasts.ACTION_PUBLISH_DIARY_BOOK);
    }

    private BroadcastHelper mBroadcastHelper = new BroadcastHelper();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBroadcastHelper.onDestroy();
    }

    @Override
    protected String onGetUrl() {
        return WebConstans.BASE_URL + WebConstans.Action.ACTION_PERSONAL_PAGE;
    }

    @Override
    protected String onGetUrlParams() {
        Map<String, String> params = new ArrayMap<>();
        params.put("id", String.valueOf(mId));
        return HttpReq.encodeParameters(params, "utf-8");
    }

    @Override
    protected String onSetTitle() {
        if (AppInitManger.getUserId() == mId &&
                AppInitManger.getRole().equals(AppInit.ROLE_ANGEL)) {

            return getString(R.string.title_my_personal_page);
        } else {
            //return getString(R.string.title_personal_page, new Object[]{mUserName});
            return null;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected View onBindBottomLayout(LayoutInflater inflater) {
        return null;
    }

    @Override
    protected View onBindHeaderLayout(LayoutInflater inflater) {
        final View headerView = inflater.inflate(R.layout.layout_personal_page_header, null);
        mBgLayout = (RelativeLayout) headerView.findViewById(R.id.layout_bg);
        mImgAvatar = (ImageView) headerView.findViewById(R.id.img_avatar);
        mTxtUsername = (TextView) headerView.findViewById(R.id.txt_username);
        mTxtInfo = (TextView) headerView.findViewById(R.id.txt_info);
        mScrollView.setOnScrollChangeListener(new NotifyingScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
                final int headerHeight = headerView.getHeight() -
                        ((BaseActivity) getActivity()).getSupportActionBar().getHeight();
                final float ratio = (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
                final int newAlpha = (int) (ratio * 255);
                mToolbarBackgroundDrawable.setAlpha(newAlpha);

//                if (t > oldt) {
//                    mPublishButton.hide();
//                }else{
//                    mPublishButton.show();
//                }
            }

            @Override
            public void onStopScroll() {
                //mPublishButton.show();
            }
        });
        return headerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //ToastUtil.showMessage(String.format("PersonalPage Id is %d", mId));
    }

    private Drawable.Callback mDrawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            ((BaseActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {

        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {

        }
    };

    @Override
    protected void onAddWebActionListener(BaseWebViewClient client) {
        super.onAddWebActionListener(client);
        //显示header
        client.addActionListener(new WebActionListener<JsCallShowHeader>(
                JsNativeBiz.ACTION_SHOW_USER_PROFILE_HEADER, JsCallShowHeader.class) {
            @Override
            public void execute(final JsCallShowHeader data) {
                if (data.data.avatar != null) {
                    BitmapBiz.loadImage(data.data.avatar, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response,
                                               boolean isImmediate) {
                            Bitmap bitmap = response.getBitmap();
                            if (bitmap != null) {
                                mImgAvatar.setImageBitmap(bitmap);
                                mBgLayout.setBackgroundDrawable(new BitmapDrawable(getResources(),
                                        BitmapUtil.doBlur(bitmap, 50, false)));
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                }
                mTxtUsername.setText(data.data.name);
                StringBuilder builder = new StringBuilder();
                if(data.data.province != null){
                    builder.append(data.data.province);
                    builder.append(" ");
                    builder.append(data.data.city);
                }

                if(data.data.age != 0){
                    builder.append(" ");
                    builder.append(data.data.age);
                    builder.append("岁");
                }
//                mTxtInfo.setText(data.data.province + data.data.city + " " + data.data.age + "岁");
                mTxtInfo.setText(builder.toString());

//                if (data.data.id == AppInitManger.getUserId()) {
//                    LogUtil.i(TAG, "show add post button, id is " + data.data.id);
//                    callJsMethod(JsNativeBiz.JS_METHOD_G_SHOW_ADD_POST_BUTTON, null);
//                }
            }
        });

//        client.addActionListener(new WebActionListener(JsNativeBiz.ACTION_ADD_POST, BaseJsCall.class) {
//            @Override
//            public void execute(BaseJsCall data) {
//                startActivity(PublishDiaryActivity.class);
//            }
//        });

    }

    public void onEvent(AddPostFinishEvent event) {
        callJsMethod(JsNativeBiz.JS_METHOD_G_REFRESH_TIMELINE, null);
        mScrollView.smoothScrollTo(0, 0);
    }
}
