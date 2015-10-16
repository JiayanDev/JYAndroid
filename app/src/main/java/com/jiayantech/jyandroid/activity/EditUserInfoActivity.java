package com.jiayantech.jyandroid.activity;

import android.support.v4.util.ArrayMap;

import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.eventbus.EditFinishEvent;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;

import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by liangzili on 15/8/4.
 */
public class EditUserInfoActivity extends EditActivity {
    @Override
    protected void onSave(final String text) {
        if (mText.equals(text)) {
            ToastUtil.showMessage("未作修改，无需保存");
        } else if(mText.length() > 10){
            ToastUtil.showMessage("用户昵称不能超过10个字符");
        }
        else{
            showProgressDialog();
            Map<String, String> params = new ArrayMap<>();
            params.put("name", text);
            UserBiz.update(params, new ResponseListener<AppResponse>() {

                @Override
                public void onResponse(AppResponse appResponse) {
                    dismissProgressDialog();
                    EditFinishEvent event = new EditFinishEvent();
                    event.action = EditFinishEvent.ACTION_EDIT_NAME;
                    event.name = text;

                    AppInitManger.sAppInit.name = text;

                    EventBus.getDefault().post(event);
                    finish();
                }
            });
        }
    }
}
