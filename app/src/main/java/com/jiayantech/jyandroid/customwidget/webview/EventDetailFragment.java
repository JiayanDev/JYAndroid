package com.jiayantech.jyandroid.customwidget.webview;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.utils.ToastUtil;

import java.util.Map;

/**
 * Created by liangzili on 15/7/9.
 */
public class EventDetailFragment extends WebViewFragment{

    private Button mApplyButton;

    public static EventDetailFragment newInstance(long id, long userId, String userName){
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putLong(WebViewFragment.EXTRA_ID, id);
        args.putLong(WebViewFragment.EXTRA_USER_ID, userId);
        args.putString(WebViewFragment.EXTRA_USERNAME, userName);
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
        return null;
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
    public void onResume() {
        super.onResume();
        ToastUtil.showMessage(String.format("eventId is %d", mId));
    }
}
