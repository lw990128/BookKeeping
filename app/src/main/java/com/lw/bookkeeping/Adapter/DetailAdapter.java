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

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

    private List<HomeRecord> dRecordList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView detailRecordImage;
        TextView detailRecordSum;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            detailRecordImage = itemView.findViewById(R.id.image_detailRecord);
            detailRecordSum = itemView.findViewById(R.id.text_detailRecordSum);
        }
    }

    public DetailAdapter(List<HomeRecord> dRecordList){
        this.dRecordList = dRecordList;
    }

    @NonNull
    @Override
    // 创建 ViewHolder 实例
    public DetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_record, viewGroup, false);
        return new DetailAdapter.ViewHolder(view);
    }

    @Override
    // 对子项数据进行布局
    public void onBindViewHolder(@NonNull DetailAdapter.ViewHolder viewHolder, int i) {
        HomeRecord homeRecord = dRecordList.get(i);
        viewHolder.detailRecordImage.setImageResource(homeRecord.getImageId());
        viewHolder.detailRecordSum.setText(homeRecord.getAmount());
    }

    @Override
    // 一共有多少子项
    public int getItemCount() {
        return dRecordList.size();
    }
}
