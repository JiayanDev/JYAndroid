package com.jiayantech.jyandroid.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.model.Search;
import com.jiayantech.library.base.BaseApplication;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by janseon on 2015/7/14.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class SearchAdapter extends BaseSimpleModelAdapter<Search> {
    private static final int color = BaseApplication.getContext().getResources().getColor(R.color.theme_color);

    public SearchAdapter(List<Search> list, String spanStr) {
        super(toSpanList(list, spanStr));
    }

    private static List<Search> toSpanList(List<Search> list, String spanStr) {
        if (!TextUtils.isEmpty(spanStr)) {
            for (Search search : list)
                search.spannableName = getForegroundColorSpan(search.name, spanStr);
        }
        return list;
    }

    public static SpannableString getForegroundColorSpan(String str, String spanStr) {
        SpannableString spannableString = new SpannableString(str);
        int start = 0;
        int end = 0;
        while ((start = str.indexOf(spanStr, end)) >= 0) {
            end = start + spanStr.length();
            spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_search, this);
    }


    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<Search> {
        public TextView txt_content;
        public TextView txt_hint;

        public ViewHolder(ViewGroup parent, int layoutId) {
            this(parent, layoutId, null);
        }

        public ViewHolder(ViewGroup parent, int layoutId, SearchAdapter adapter) {
            super(parent, layoutId, adapter);
            txt_content = (TextView) itemView.findViewById(R.id.txt_content);
            txt_hint = (TextView) itemView.findViewById(R.id.txt_hint);
        }

        @Override
        public void onBind(Search search, int position) {
            txt_content.setText(search.spannableName);
            if (TextUtils.isEmpty(search.hospitalName)) {
                txt_hint.setVisibility(View.GONE);
            } else {
                txt_hint.setVisibility(View.VISIBLE);
                txt_hint.setText(search.hospitalName);
            }
        }
    }
}
