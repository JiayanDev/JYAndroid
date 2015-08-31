package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.model.location.BaseLocation;

import java.util.List;

/**
 * Created by liangzili on 15/8/28.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>{
    private Context mContext;
    private List<? extends BaseLocation> mLocationList;
    private LayoutInflater mLayoutInflater;
    private LocationClickListener mLocationClickListener;

    public LocationAdapter(Context context, List<? extends BaseLocation> locationList,
                           LocationClickListener listener){
        mContext = context;
        mLocationList = locationList;
        mLayoutInflater = LayoutInflater.from(context);
        mLocationClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_location, parent, false);
        return new ViewHolder(itemView, mLocationClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BaseLocation location = mLocationList.get(position);
        String name = location.name;
        holder.mTxtLocation.setText(name);

    }

    @Override
    public int getItemCount() {
        return mLocationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTxtLocation;
        private LocationClickListener mListener;

        public ViewHolder(View itemView, LocationClickListener listener) {
            super(itemView);
            mTxtLocation = (TextView)itemView.findViewById(R.id.txt_location);
            mListener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onLocationClick(mTxtLocation.getText().toString());
        }
    }

    public static interface LocationClickListener {
        void onLocationClick(String name);
    }
}
