package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.PostActivity;
import com.jiayantech.jyandroid.fragment.PostListFragment;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;

import java.util.List;

/**
 * Created by liangzili on 15/7/3.
 */
public class TopicCategoryAdapter extends RecyclerView.Adapter<TopicCategoryAdapter.ViewHolder> {
    private List<AppInit.Category> mCategoryList;
    private Context mContext;

    public TopicCategoryAdapter(Context context) {
        mContext = context;
        mCategoryList = AppInitManger.getProjectCategoryTopList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_category,
                parent, false);
        ViewHolder vh = new ViewHolder(view, new ViewHolderOnClickListener());
        return vh;
    }

    class ViewHolderOnClickListener implements View.OnClickListener {
        private int mPosition;

        public int getPosition() {
            return mPosition;
        }

        public void setPosition(int pos) {
            mPosition = pos;
        }

        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(mContext, PostDetailActivity.class);
//            intent.putExtra(WebViewFragment.EXTRA_ID, mList.get(mPosition).id);
//            intent.putExtra(WebViewFragment.EXTRA_TYPE, WebViewFragment.TYPE_TOPIC);
//            mContext.startActivity(intent);
            Intent intent = new Intent(mContext, PostActivity.class);
            intent.putExtra(PostListFragment.EXTRA_CATEGORY, mCategoryList.get(mPosition));
            mContext.startActivity(intent);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AppInit.Category category = mCategoryList.get(position);
        holder.name.setText(category.name);
        holder.listener.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ViewHolderOnClickListener listener;

        public ViewHolder(View itemView, ViewHolderOnClickListener listener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.category_name);
            this.listener = listener;
            itemView.setOnClickListener(listener);
        }
    }
}
