package com.jiayantech.library.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liangzili on 15/7/27.
 * RecyclerView with emptyView
 */
public class EmptyRecyclerView extends RecyclerView{
    private View emptyView;
    
    private final AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    private void checkIfEmpty() {
        if(emptyView != null && getAdapter() != null){
            final boolean emptyViewVisible = (getAdapter().getItemCount() == 0);
            emptyView.setVisibility(emptyViewVisible? View.VISIBLE: View.GONE);
            setVisibility(emptyViewVisible? View.GONE: View.VISIBLE);
        }
    }

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setEmptyView(View view){
        emptyView = view;
        checkIfEmpty();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if(oldAdapter != null){
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if(adapter != null){
            adapter.registerAdapterDataObserver(observer);
        }

        checkIfEmpty();
    }
}
