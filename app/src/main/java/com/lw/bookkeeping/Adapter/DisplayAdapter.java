package com.lw.bookkeeping.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lw.bookkeeping.Entity.Display;
import com.lw.bookkeeping.R;

import java.util.List;

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.ViewHolder>{

    // 存入数据的 List
    private List<Display> displayList;

    // 点击事件
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    // 长按事件
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

    // DisplayAdapter 内部类
    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView displayImage;
        TextView displayCategory;
        TextView displaySum;
        TextView displayTime;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            displayImage = itemView.findViewById(R.id.image_display);
            displayCategory = itemView.findViewById(R.id.text_displayCategory);
            displaySum = itemView.findViewById(R.id.text_displaySum);
            displayTime = itemView.findViewById(R.id.text_displayTime);
        }
    }

    public DisplayAdapter(List<Display> displayList){
        this.displayList = displayList;
    }

    @NonNull
    @Override
    // 创建 ViewHolder 实例
    public DisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.display, viewGroup, false);
        return new ViewHolder(view);
    }



    @Override
    // 对子项数据进行布局
    public void onBindViewHolder(@NonNull DisplayAdapter.ViewHolder viewHolder, int position) {
        Display display = displayList.get(position);
        Glide.with(viewHolder.itemView).load(display.getImageId()).into(viewHolder.displayImage);
        viewHolder.displayCategory.setText(display.getCategory());
        viewHolder.displaySum.setText(display.getSum());
        viewHolder.displayTime.setText(display.getTime());

        if (mOnItemClickListener != null){
            viewHolder.itemView.setOnClickListener(v ->
                    mOnItemClickListener.onItemClick(viewHolder.itemView, position));
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
        return displayList.size();
    }
}
