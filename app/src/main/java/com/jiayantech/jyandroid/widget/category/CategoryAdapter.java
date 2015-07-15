package com.jiayantech.jyandroid.widget.category;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangzili on 15/7/14.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    private List<Category> mList;

    public CategoryAdapter(List<Category> list) {
        mList = list;
        mList = Category.list();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                getLayoutInflater(parent).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageResource(mList.get(position).imageId);
        holder.name.setText(mList.get(position).title);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.txt_category);
            image = (ImageView)itemView.findViewById(R.id.img_category);
        }

    }

    private LayoutInflater getLayoutInflater(ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater;
    }
}
