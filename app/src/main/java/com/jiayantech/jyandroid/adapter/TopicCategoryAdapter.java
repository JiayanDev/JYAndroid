package com.jiayantech.jyandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.manager.UserManger;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by liangzili on 15/7/3.
 */
public class TopicCategoryAdapter extends RecyclerView.Adapter<TopicCategoryAdapter.ViewHolder>{
    private List<String> mCategoryList;
    public TopicCategoryAdapter(){
        mCategoryList = UserManger.sProjectCategoryTopLevels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_category,
                parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mCategoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.category_name);
        }
    }
}
