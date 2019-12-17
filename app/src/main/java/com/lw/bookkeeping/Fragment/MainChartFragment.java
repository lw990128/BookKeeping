package com.lw.bookkeeping.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lw.bookkeeping.Entity.Record;
import com.lw.bookkeeping.R;
import com.lw.bookkeeping.Util.KeyValuePair;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;

public class MainChartFragment extends Fragment {

    private PieChart chart;

    private double caterSum;

    private double shopSum;

    private double transportSum;

    private double dailySum;

    private double vegetableSum;

    private double fruitSum;

    private double sportsSum;

    private double snacksSum;

    private double entertainmentSum;

    private double costumeSum;

    private double facialSum;

    private double housingSum;

    private double domesticSum;

    private double medicalSum;

    private double studySum;

    private double communicationSum;

    private double digitalSum;

    private double otherSpendSum;

    private double salarySum;

    private double moneyManagementSum;

    private double redPacketSum;

    private double partTimeJobSum;

    private double otherIncomeSum;

    private List<KeyValuePair> sumList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_chart_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this, view);

        sum();

        // 饼状图
        chart = view.findViewById(R.id.pie_chart);
        chart.setUsePercentValues(false);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        // 设置中间文件
//        chart.setCenterText(generateCenterSpannableText());

        chart.setDrawHoleEnabled(false);
//        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // 触摸旋转
        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(true);

        // 变化监听
//        chart.setOnChartValueSelectedListener((OnChartValueSelectedListener) this);
        // 模拟数据
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (KeyValuePair keyValuePair : sumList){
            if (keyValuePair.getSum() != (double) 0){
                entries.add(new PieEntry((float) keyValuePair.getSum(), keyValuePair.getCategory()));
            }
        }

        chart.animateY(1400, Easing.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // 设置数据
        setData(entries);

        // 输入标签样式
        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTextSize(12f);
    }

    private void sum(){
        sumList.clear();
        List<Record> recordList = LitePal.findAll(Record.class);
        for (Record record : recordList){
            switch (record.getCategory()) {
                case "餐饮":
                    caterSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "购物":
                    shopSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "日常":
                    dailySum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "蔬菜":
                    vegetableSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "娱乐":
                    entertainmentSum += Math.abs(Double.parseDouble(record.getSum()));
                case "交通":
                    transportSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "水果":
                    fruitSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "运动":
                    sportsSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "零食":
                    snacksSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "服饰":
                    costumeSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "住房":
                    housingSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "美容":
                    facialSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "学习":
                    studySum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "医疗":
                    medicalSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "居家":
                    domesticSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "数码":
                    digitalSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "通讯":
                    communicationSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "其他支出":
                    otherSpendSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "工资":
                    salarySum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "红包":
                    redPacketSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "理财":
                    moneyManagementSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "兼职":
                    partTimeJobSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
                case "其他收入":
                    otherIncomeSum += Math.abs(Double.parseDouble(record.getSum()));
                    break;
            }
        }
        sumList.add(new KeyValuePair("餐饮", caterSum));
        sumList.add(new KeyValuePair("通讯", communicationSum));
        sumList.add(new KeyValuePair("购物", shopSum));
        sumList.add(new KeyValuePair("日常", dailySum));
        sumList.add(new KeyValuePair("娱乐", entertainmentSum));
        sumList.add(new KeyValuePair("蔬菜", vegetableSum));
        sumList.add(new KeyValuePair("交通", transportSum));
        sumList.add(new KeyValuePair("水果", fruitSum));
        sumList.add(new KeyValuePair("运动", sportsSum));
        sumList.add(new KeyValuePair("零食", snacksSum));
        sumList.add(new KeyValuePair("服饰", costumeSum));
        sumList.add(new KeyValuePair("美容", facialSum));
        sumList.add(new KeyValuePair("住房", housingSum));
        sumList.add(new KeyValuePair("学习", studySum));
        sumList.add(new KeyValuePair("医疗", medicalSum));
        sumList.add(new KeyValuePair("居家", domesticSum));
        sumList.add(new KeyValuePair("数码", digitalSum));
        sumList.add(new KeyValuePair("其他支出", otherSpendSum));
        sumList.add(new KeyValuePair("红包", redPacketSum));
        sumList.add(new KeyValuePair("理财", moneyManagementSum));
        sumList.add(new KeyValuePair("工资", salarySum));
        sumList.add(new KeyValuePair("兼职", partTimeJobSum));
        sumList.add(new KeyValuePair("其他收入", otherIncomeSum));
    }

    private void setData(ArrayList<PieEntry> entries) {
        ArrayList<Integer> colors = new ArrayList<>();

        //数据和颜色
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);


        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);
        chart.highlightValues(null);
        //刷新
        chart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }
}
