package com.jiayantech.jyandroid.widget.category;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;

import java.util.List;

/**
 * Created by liangzili on 15/7/14.
 */
public class CategoryAdapter2 extends BaseAdapter{
    private List<Category> mList = Category.list();
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,
                parent, false);
        TextView name = (TextView)view.findViewById(R.id.txt_category);
        ImageView image = (ImageView)view.findViewById(R.id.img_category);
        name.setText(mList.get(position).title);
        image.setImageResource(mList.get(position).imageId);

        return view;
    }
}
