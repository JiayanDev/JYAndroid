package com.jiayantech.jyandroid.fragment.webview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;

import com.jiayantech.jyandroid.activity.ApplyEventActivity;
import com.jiayantech.jyandroid.activity.LoginActivity;
import com.jiayantech.jyandroid.biz.JsNativeBiz;
import com.jiayantech.jyandroid.fragment.ApplyEventFragment;
import com.jiayantech.jyandroid.model.web.JsCallApplyEvent;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.utils.ToastUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by liangzili on 15/7/9.
 */
public class EventDetailFragment extends WebViewFragment{

//    private Button mApplyButton;

    public static EventDetailFragment newInstance(long id){
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putLong(WebViewFragment.EXTRA_ID, id);
        //args.putLong(WebViewFragment.EXTRA_USER_ID, userId);
        //args.putString(WebViewFragment.EXTRA_USERNAME, userName);
        args.putString(WebViewFragment.EXTRA_TYPE, WebConstans.Type.TYPE_EVENT);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String onGetUrl() {
        return WebConstans.BASE_URL + WebConstans.Action.ACTION_EVENT;
    }

    @Override
    protected String onGetUrlParams() {
        Map<String, String> params = new ArrayMap<>();
        params.put("id", String.valueOf(mId));
        return HttpReq.encodeParameters(params, "utf-8");
    }

    @Override
    protected String onSetTitle() {
        return "活动详情";
    }

    @Override
    protected View onBindBottomLayout(LayoutInflater inflater) {
//        View view = inflater.inflate(R.layout.layout_apply_event, null);
//        mApplyButton = (Button)view.findViewById(R.id.btn_apply);
//        mApplyButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ApplyEventActivity.class);
//                intent.putExtra(ApplyEventFragment.EVENT_ID, mId);
//                startActivity(intent);
//            }
//        });
//        return view;
        return null;
    }

    @Override
    protected View onBindHeaderLayout(LayoutInflater inflater) {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        ToastUtil.showMessage(String.format("eventId is %d", mId));
    }

    @Override
    protected void onAddWebActionListener(BaseWebViewClient client) {
        super.onAddWebActionListener(client);

        //监听web页面报名参加活动的回调
        client.addActionListener(new WebActionListener<JsCallApplyEvent>(
                JsNativeBiz.ACTION_APPLY_EVENT, JsCallApplyEvent.class) {
            @Override
            public void execute(final JsCallApplyEvent data) {
                if(data == null){
                    return;
                }
                LoginActivity.checkLoginToRunnable((BaseActivity)getActivity(), new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder sb = new StringBuilder();
                        if(data.data.eventInfo.categoryIds != null) {
                            for (JsCallApplyEvent.CategoryId category : data.data.eventInfo.categoryIds) {
                                sb.append(category.name);
                                sb.append(" ");
                            }
                        }else{
                            sb.append("项目未知");
                        }
                        long id = data.data.id;
                        String project = sb.toString().trim();
                        String hospitalAndDoctor = data.data.eventInfo.hospitalName + " " +
                                data.data.eventInfo.doctorName;
                        String angelAvatar = data.data.eventInfo.angelUserInfo.avatar;
                        String angelName = data.data.eventInfo.angelUserInfo.name;
                        DateFormat df = new SimpleDateFormat("MM.dd E a HH:mm");
                        String time = df.format(new Date(data.data.eventInfo.beginTime * 1000));

                        Intent intent = new Intent(getActivity(), ApplyEventActivity.class);
                        intent.putExtra(ApplyEventFragment.EVENT_ID, id);
                        intent.putExtra(ApplyEventFragment.ANGEL_AVATAR, angelAvatar);
                        intent.putExtra(ApplyEventFragment.ANGEL_NAME, angelName);
                        intent.putExtra(ApplyEventFragment.PROJECT_NAME, project);
                        intent.putExtra(ApplyEventFragment.HOSPITAL_AND_DOCTOR,hospitalAndDoctor);
                        intent.putExtra(ApplyEventFragment.EVENT_TIME, time);
                        getActivity().startActivity(intent);

                    }
                });


                //onJsCallApplyEvent(id, angelAvatar, angelName, project, hospitalAndDoctor, time);
            }
        });
    }
}
