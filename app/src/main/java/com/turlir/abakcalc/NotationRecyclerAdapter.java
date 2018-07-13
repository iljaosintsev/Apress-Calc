package com.turlir.abakcalc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NotationRecyclerAdapter extends RecyclerView.Adapter<NotationRecyclerAdapter.Holder> {

    private final List<Object> mData;
    private final LayoutInflater mInflater;

    NotationRecyclerAdapter(Context cnt) {
        mData = new ArrayList<>();
        mInflater = LayoutInflater.from(cnt);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.notation_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    <T> void setItems(List<T> items) {
        mData.clear();
        mData.addAll(items);
    }

    static class Holder extends RecyclerView.ViewHolder {

        Holder(View itemView) {
            super(itemView);
        }

        void bind(Object visual) {
            ((TextView) itemView).setText(visual.toString());
        }
    }

}
