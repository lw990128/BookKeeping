package com.lw.bookkeeping.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lw.bookkeeping.Entity.HomeRecord;
import com.lw.bookkeeping.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    private List<HomeRecord> hRecordList;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        mOnItemLongClickListener = onItemLongClickListener;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView homeRecordImage;
        TextView homeRecordSum;
        TextView homeRecordName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            homeRecordImage = itemView.findViewById(R.id.image_homeRecord);
            homeRecordSum = itemView.findViewById(R.id.text_homeRecordSum);
            homeRecordName = itemView.findViewById(R.id.text_homeRecordName);
        }
    }

    public HomeAdapter(List<HomeRecord> hRecordList){
        this.hRecordList = hRecordList;
    }

    @NonNull
    @Override
    // 创建 ViewHolder 实例
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_record, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    // 对子项数据进行布局
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder viewHolder, int position) {
        HomeRecord homeRecord = hRecordList.get(position);
        viewHolder.homeRecordImage.setImageResource(homeRecord.getImageId());
        viewHolder.homeRecordSum.setText(homeRecord.getAmount());
        viewHolder.homeRecordName.setText(homeRecord.getText());

        if (mOnItemClickListener != null){
            viewHolder.itemView.setOnClickListener(v -> {
                mOnItemClickListener.onItemClick(viewHolder.itemView, position);
            });
        }
        if (mOnItemLongClickListener != null){
            viewHolder.itemView.setOnLongClickListener(v -> {
                mOnItemLongClickListener.onItemLongClick(viewHolder.itemView, position);
                return true;
            });
        }
    }

    @Override
    // 一共有多少子项
    public int getItemCount() {
        return hRecordList.size();
    }
}
