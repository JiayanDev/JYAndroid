package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.model.DiaryHeader;
import com.jiayantech.library.base.BaseModelAdapter;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.http.BitmapBiz;

import java.util.List;

/**
 * Created by liangzili on 15/7/1.
 */
public class DiaryHeaderAdapter extends BaseSimpleModelAdapter<DiaryHeader> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public DiaryHeaderAdapter(Context context, List<DiaryHeader> list) {
        super(list);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public DiaryHeaderViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        View view = mLayoutInflater.inflate(R.layout.item_diary_header, viewGroup, false);
        return new DiaryHeaderViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    class DiaryHeaderViewHolder extends BaseSimpleModelAdapter.ViewHolder<DiaryHeader> {
        public ImageView mCoverImage;
        public TextView mTitleText;
        public Button mUpdateButton;

        public DiaryHeaderViewHolder(View itemView) {
            super(itemView);
            mCoverImage = (ImageView) itemView.findViewById(R.id.cover);
            mTitleText = (TextView) itemView.findViewById(R.id.title);
            mUpdateButton = (Button) itemView.findViewById(R.id.update);
        }

        @Override
        public void onBind(DiaryHeader item, int position) {
            BitmapBiz.display(mCoverImage, item.currentPhoto);
            mTitleText.setText(item.projectName);
        }
    }


}
