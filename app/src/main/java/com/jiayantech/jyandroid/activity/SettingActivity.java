package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.CommBiz;
import com.jiayantech.jyandroid.biz.UploadImageBiz;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.commons.AppDialogUtils;
import com.jiayantech.jyandroid.fragment.webview.WebConstans;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.comm.ConfigManager;
import com.jiayantech.library.comm.Property;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.BaseAppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;
import com.jiayantech.library.utils.Utils;

/**
 * Created by 健兴 on 2015/8/3.
 */
public class SettingActivity extends BaseActivity {
    private TextView txt_service_phone;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViews();
        setViewsContent();
    }

    protected void findViews() {
        txt_service_phone = (TextView) findViewById(R.id.txt_service_phone);
    }

    protected void setViewsContent() {
        txt_service_phone.setText(Property.getProperty("phone"));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_password:
                startActivity(UpdatePassActivity.class);
                break;
            case R.id.txt_help:
                WebViewActivity.launchActivity(this, 0, WebConstans.Type.TYPE_HELP);
                break;
            case R.id.txt_about:
                startActivity(AboutUsActivity.class);
                break;
            case R.id.txt_feedback:
                startActivity(FeedbackActivity.class);
                break;
            case R.id.layout_service:
                Utils.takePhoneCall(this, txt_service_phone.getText().toString());
                break;
            case R.id.btn_exit:
                AppDialogUtils.showBottomDialog(this, "确定退出", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showProgressDialog();
                        UserBiz.logout(new ResponseListener<BaseAppResponse>() {
                            @Override
                            public void onResponse(BaseAppResponse baseAppResponse) {
                                //dismissProgressDialog();
                                //ToastUtil.showMessage("logout");
                                ConfigManager.putToken("");
                                CommBiz.appInit(new ResponseListener<AppResponse<AppInit>>() {
                                    @Override
                                    public void onResponse(AppResponse<AppInit> appInitAppResponse) {
                                        AppInit appInit = appInitAppResponse.data;
                                        AppInitManger.save(appInit);
                                        dismissProgressDialog();
                                        //((MainActivity) getActivity()).onCheckedChanged(null, R.id.radio_activity);
                                        ActivityResult.onFinishResult(SettingActivity.this);
                                        //用户退出后清空图片上传的policy和signature
                                        UploadImageBiz.clearProof();
                                    }
                                });
                            }
                        });

                    }
                });
                break;
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(R.string.title_mine_setting);
    }
}
