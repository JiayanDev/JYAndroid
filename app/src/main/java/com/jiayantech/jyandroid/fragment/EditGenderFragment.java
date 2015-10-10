package com.jiayantech.jyandroid.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.UserInfoActivity;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.commons.Constants;
import com.jiayantech.jyandroid.eventbus.EditFinishEvent;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.ResponseListener;

import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by liangzili on 15/8/4.
 */
public class EditGenderFragment extends DialogFragment {
    private int mGender;
    private LinearLayout mDialogLayout;
    private RadioGroup mGenderRadioGroup;
    private RadioButton mMaleRadioBtn;
    private RadioButton mFemaleRadioBtn;

    public static EditGenderFragment createFragment(int gender) {
        EditGenderFragment fragment = new EditGenderFragment();
        Bundle args = new Bundle();
        args.putInt(UserInfoActivity.EXTRA_GENDER, gender);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGender = getArguments().getInt(UserInfoActivity.EXTRA_GENDER, 0);

        setUpViews();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_edit_gender)
                .setView(mDialogLayout)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mGender = mGenderRadioGroup.getCheckedRadioButtonId() == R.id.radio_male ? 1 : 0;
                        Map<String, String> params = new ArrayMap<String, String>();
                        params.put("gender", String.valueOf(mGender));
                        UserBiz.update(params, new ResponseListener<AppResponse>() {
                            @Override
                            public void onResponse(AppResponse appResponse) {
                                EditFinishEvent event = new EditFinishEvent();
                                event.action = EditFinishEvent.ACTION_EDIT_GENDER;
                                event.gender = mGender;

                                AppInitManger.sAppInit.gender = mGender;
                                EventBus.getDefault().post(event);
                            }
                        });
                    }
                });
        Dialog dialog = builder.create();
        return dialog;
    }

    private void setUpViews() {
        mDialogLayout = (LinearLayout) LayoutInflater.from(getActivity()).
                inflate(R.layout.dialog_edit_gender, null);
        mGenderRadioGroup = (RadioGroup) mDialogLayout.findViewById(R.id.radio_group_gender);
        mMaleRadioBtn = (RadioButton) mGenderRadioGroup.findViewById(R.id.radio_male);
        mFemaleRadioBtn = (RadioButton) mGenderRadioGroup.findViewById(R.id.radio_female);
        if (mGender == Constants.Gender.MALE) {
            mMaleRadioBtn.setChecked(true);
        } else {
            mFemaleRadioBtn.setChecked(true);
        }
    }
}
