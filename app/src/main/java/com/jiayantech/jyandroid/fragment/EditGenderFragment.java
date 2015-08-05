package com.jiayantech.jyandroid.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.UserInfoActivity;

/**
 * Created by liangzili on 15/8/4.
 */
public class EditGenderFragment extends DialogFragment{
    private int mGender;
    private LinearLayout mDialogLayout;
    private RadioGroup mGenderRadioGroup;
    private RadioButton mMaleRadioBtn;
    private RadioButton mFemaleRadioBtn;

    public static EditGenderFragment createFragment(int gender){
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
        builder.setTitle(R.string.title_edit_gender);
    }

    private void setUpViews(){
        mDialogLayout = (LinearLayout)LayoutInflater.from(getActivity()).
                inflate(R.layout.dialog_edit_gender, null);
        mGenderRadioGroup = (RadioGroup)mDialogLayout.findViewById(R.id.radio_group_gender);
        mMaleRadioBtn = (RadioButton)mGenderRadioGroup.findViewById(R.id.radio_button_male);
        mFemaleRadioBtn = (RadioButton)mGenderRadioGroup.findViewById(R.id.radio_button_female);
    }
}
