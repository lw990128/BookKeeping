package com.lw.bookkeeping.Util;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lw.bookkeeping.R;

import java.util.List;

// 一二级联动
public class SingleOptionsPicker<T> {

    private Fragment fragment;
    //回调接口
    private OnPickerOptionsClickListener listener;
    private OptionsPickerView pvOptions;
    //一级联动或条件数据源
    private List<T> options1Items;
    //二级联动数据源
    private List<List<T>> options2Items;
    //默认选中的位置
    private int options1, options2;

    /**
     *普通条件选择项方法
     */
    private SingleOptionsPicker(Fragment fragment, String select, List<T> options1Items, OnPickerOptionsClickListener listener) {
        this.fragment = fragment;
        this.listener = listener;
        this.options1Items = options1Items;
        boolean isContinue = true;
        for (int i = 0; i < options1Items.size() && isContinue; i++) {
            //设置选中项
            if (select.equals(options1Items.get(i))) {
                options1 = i;
                isContinue = false;
            }
        }
        getInstance();
    }

    /**
     * 二级联动选择项方法
     */
    public SingleOptionsPicker(Fragment fragment, int options1, int options2, List<T> options1Items, List<List<T>> options2Items,
                               OnPickerOptionsClickListener listener) {
        this.fragment = fragment;
        this.listener = listener;
        this.options1 = options1;
        this.options2 = options2;
        this.options1Items = options1Items;
        this.options2Items = options2Items;
        getInstance();
    }


    private void getInstance() {
        // 自定义布局
        pvOptions = new OptionsPickerBuilder(fragment.getActivity(), (options1, options2, options3, v) -> {
            if (listener != null) {
                listener.onOptionsSelect(options1, options2, options3, v);
            }
        })
                //分隔线颜色。
                .setDividerColor(Color.parseColor("#BBBBBB"))
                //滚轮背景颜色
                .setBgColor(Color.parseColor("#F5F5F5"))
                //设置两横线之间的间隔倍数
                .setLineSpacingMultiplier(1.8f)
                //设置选中项的颜色
                .setTextColorCenter(Color.parseColor("#333333"))
                //是否只显示中间选中项的label文字，false则每项item全部都带有label
                .isCenterLabel(true)
                ////设置选择的三级单位
                .setLabels("", "", "")
                //标题文字
                .setTitleText("标题文字")
                //默认选中项
                .setSelectOptions(options1, options2)
                .setLayoutRes(R.layout.item_picker_options, v -> {
                    final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                    tvSubmit.setOnClickListener(v1 -> {
                        pvOptions.returnData();
                        pvOptions.dismiss();
                    });
                })
                .build();
        pvOptions.setPicker(options1Items, options2Items);// 二级选择器
    }

    public static void openOptionsPicker(Fragment fragment, List<String> list, final int type, final Button button) {
        String select = button.getText().toString();
        new SingleOptionsPicker(fragment, select, list,
                (options1, options2, options3, view) -> {
                    if (type == 1) {
                        int size = list.size();
                        String[] array = list.toArray(new String[size]);
                        button.setText(array[options1]);
                    }
                }).show();
    }

    /**
     * 显示选择器
     */
    public void show() {
        if (pvOptions != null && !pvOptions.isShowing()) {
            pvOptions.show();
        }
    }

    /**
     * 关闭选择器
     */
    public void dismiss() {
        if (pvOptions != null && pvOptions.isShowing()) {
            pvOptions.dismiss();
        }
    }

    /**
     * 选择项回调
     */
    public interface OnPickerOptionsClickListener {
        void onOptionsSelect(int options1, int options2, int options3, View view);
    }

}