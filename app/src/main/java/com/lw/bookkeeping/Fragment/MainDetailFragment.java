package com.lw.bookkeeping.Fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lw.bookkeeping.Entity.Display;
import com.lw.bookkeeping.Entity.Record;
import com.lw.bookkeeping.R;
import com.lw.bookkeeping.Adapter.DisplayAdapter;

import org.litepal.LitePal;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainDetailFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.btn_classification)
    Button btnClassification;

    @BindView(R.id.tv_spend)
    TextView tvSpend;

    @BindView(R.id.tv_income)
    TextView tvIncome;

    private RecyclerView mainDetailRecyclerView;

    private DisplayAdapter displayAdapter;

    private List<Display> hDetailList = new ArrayList<>();

    // 判断数据是否刷新
    private boolean isRefresh = false;

    private PopupWindow dPopupWindow;

    // 设置数字为二位小数
    private DecimalFormat df = new DecimalFormat("0.00");

    private PopupWindow popupWindow;

    int id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        // 初始化组件
        initEvent();

        mainDetailRecyclerView = view.findViewById(R.id.main_detail_recycler_view);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mainDetailRecyclerView.setLayoutManager(layoutManager);
        displayAdapter = new DisplayAdapter(hDetailList);
        mainDetailRecyclerView.setAdapter(displayAdapter);

        if (!isRefresh){
            initMainDetailRecords();
            isRefresh = true;
        }else{
            refreshDetailRecord();
        }

        displayAdapter.setOnItemClickListener((view1, position) -> {
//            MainFragmentDirections.ActionMainFragmentToRecyclerViewFragment action =
//                    MainFragmentDirections.actionMainFragmentToRecyclerViewFragment(position);
//            Navigation.findNavController(mainHomeRecyclerView)
//                    .navigate(action);
        });

        displayAdapter.setOnItemLongClickListener((view12, position) -> {
            id = position + 1;
            showRecyclerView();
        });
    }

    private void showRecyclerView(){
        LayoutInflater inflater = (LayoutInflater) Objects.requireNonNull(getActivity())
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 引入弹窗布局
        View contentView = Objects.requireNonNull(inflater).inflate(R.layout.recyclerview_popup, null, false);
        popupWindow = new PopupWindow(contentView, ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT, true);
        // 设置背景透明
        addBackground1();

        // 设置进出动画
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        // 键盘弹出时，PopupWindow向上移动
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // 引入依附的布局
        View parentView = LayoutInflater.from(getActivity()).inflate(R.layout.main_home_fragment, null);
        // 相对于父控件的位置（例如正中央 Gravity.CENTER，下方 Gravity.BOTTOM 等），可以设置偏移或无偏移
        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);

        Button btnDelete = contentView.findViewById(R.id.delete);
        btnDelete.setOnClickListener(v -> {
            LitePal.delete(Record.class, id);
            hDetailList.clear();
            List<Record> recordList = LitePal.findAll(Record.class);
            for (Record record : recordList) {
                if (record.getDate().substring(0, 10).equals(nowTime())){
                    String time = "今天 " + record.getDate().substring(11, 16);
                    Display display = new Display(record.getIconId(), record.getCategory(), record.getSum(), time);
                    hDetailList.add(display);
                    mainDetailRecyclerView.scrollToPosition(hDetailList.size() - 1); // 将 recyclerview 定位到最后一行
                }
            }
            displayAdapter.notifyDataSetChanged();
            popupWindow.dismiss();
        });

    }

    private void addBackground1() {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = Objects.requireNonNull(getActivity()).getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getActivity().getWindow().setAttributes(lp);
        //dismiss时恢复原样
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = Objects.requireNonNull(getActivity()).getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().setAttributes(lp1);
        });
    }

    private void initEvent(){
        btnClassification.setOnClickListener(this);
        tvIncome.setOnClickListener(this);
        tvSpend.setOnClickListener(this);

        double monthlySpend = 0;
        double monthlyIncome = 0;

        List<Record> recordList = LitePal.findAll(Record.class);
        for (Record record : recordList) {
            if (record.getConsumeWay().equals("支出")){
                monthlySpend += Math.abs(Double.parseDouble((record.getSum())));
            }else{
                monthlyIncome += Math.abs(Double.parseDouble((record.getSum())));
            }
        }

        String monthlySpend1 = "支出 " + df.format(monthlySpend);
        String monthlyIncome1 = "收入 " + df.format(monthlyIncome);

        tvSpend.setText(monthlySpend1);
        tvIncome.setText(monthlyIncome1);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_classification) { // 显示分类
            showClassification();
        }
    }

    private void showClassification(){
        // 设置 ContentView
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.classification_popup, null);
        dPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT, true);
        dPopupWindow.setContentView(contentView);
        //设置背景透明
        addBackground();

        // 显示方式
        dPopupWindow.showAsDropDown(btnClassification);

        Button btnAll = contentView.findViewById(R.id.btn_all1);
        btnAll.setOnClickListener(v -> changeClassification(btnAll));
        Button btnTransport = contentView.findViewById(R.id.btn_transport1);
        btnTransport.setOnClickListener(v -> changeClassification(btnTransport));
        Button btnCater = contentView.findViewById(R.id.btn_cater1);
        btnCater.setOnClickListener(v -> changeClassification(btnCater));
        Button btnShop = contentView.findViewById(R.id.btn_shop1);
        btnShop.setOnClickListener(v -> changeClassification(btnShop));
        Button btnDaily = contentView.findViewById(R.id.btn_daily1);
        btnDaily.setOnClickListener(v -> changeClassification(btnDaily));
        Button btnVegetable = contentView.findViewById(R.id.btn_vegetable1);
        btnVegetable.setOnClickListener(v -> changeClassification(btnVegetable));
        Button btnFruit = contentView.findViewById(R.id.btn_fruit1);
        btnFruit.setOnClickListener(v -> changeClassification(btnFruit));
        Button btnSnacks = contentView.findViewById(R.id.btn_snacks1);
        btnSnacks.setOnClickListener(v -> changeClassification(btnSnacks));
        Button btnSports = contentView.findViewById(R.id.btn_sports1);
        btnSports.setOnClickListener(v -> changeClassification(btnSports));
        Button btnEntertainment = contentView.findViewById(R.id.btn_entertainment1);
        btnEntertainment.setOnClickListener(v -> changeClassification(btnEntertainment));
        Button btnCostume = contentView.findViewById(R.id.btn_costume1);
        btnCostume.setOnClickListener(v -> changeClassification(btnCostume));
        Button btnFacial = contentView.findViewById(R.id.btn_facial1);
        btnFacial.setOnClickListener(v -> changeClassification(btnFacial));
        Button btnStudy = contentView.findViewById(R.id.btn_study1);
        btnStudy.setOnClickListener(v -> changeClassification(btnStudy));
        Button btnHousing = contentView.findViewById(R.id.btn_housing1);
        btnHousing.setOnClickListener(v -> changeClassification(btnHousing));
        Button btnMedical = contentView.findViewById(R.id.btn_medical1);
        btnMedical.setOnClickListener(v -> changeClassification(btnMedical));
        Button btnCommunication = contentView.findViewById(R.id.btn_communication1);
        btnCommunication.setOnClickListener(v -> changeClassification(btnCommunication));
        Button btnDomestic = contentView.findViewById(R.id.btn_domestic1);
        btnDomestic.setOnClickListener(v -> changeClassification(btnDomestic));
        Button btnDigital = contentView.findViewById(R.id.btn_digital1);
        btnDigital.setOnClickListener(v -> changeClassification(btnDigital));
        Button btnOtherSpend = contentView.findViewById(R.id.btn_otherSpend1);
        btnOtherSpend.setOnClickListener(v -> changeClassification(btnOtherSpend));
        Button btnRedPacket = contentView.findViewById(R.id.btn_redPacket1);
        btnRedPacket.setOnClickListener(v -> changeClassification(btnRedPacket));
        Button btnMoneyManagement = contentView.findViewById(R.id.btn_moneyManagement1);
        btnMoneyManagement.setOnClickListener(v -> changeClassification(btnMoneyManagement));
        Button btnPartTimeJob = contentView.findViewById(R.id.btn_partTimeJob1);
        btnPartTimeJob.setOnClickListener(v -> changeClassification(btnPartTimeJob));
        Button btnOtherIncome = contentView.findViewById(R.id.btn_otherIncome1);
        btnOtherIncome.setOnClickListener(v -> changeClassification(btnOtherIncome));
        Button btnSalary = contentView.findViewById(R.id.btn_salary1);
        btnSalary.setOnClickListener(v -> changeClassification(btnSalary));
    }

    private String returnClassification(Integer integer){
        HashMap<Integer, String> map = new HashMap<>();
        map.put(R.id.btn_all1, "全部");
        map.put(R.id.btn_cater1, "餐饮");
        map.put(R.id.btn_shop1, "购物");
        map.put(R.id.btn_daily1, "日用");
        map.put(R.id.btn_transport1, "交通");
        map.put(R.id.btn_vegetable1, "蔬菜");
        map.put(R.id.btn_fruit1, "水果");
        map.put(R.id.btn_snacks1, "零食");
        map.put(R.id.btn_sports1, "运动");
        map.put(R.id.btn_entertainment1, "娱乐");
        map.put(R.id.btn_communication1, "通讯");
        map.put(R.id.btn_costume1, "服饰");
        map.put(R.id.btn_facial1, "美容");
        map.put(R.id.btn_housing1, "住房");
        map.put(R.id.btn_study1, "学习");
        map.put(R.id.btn_medical1, "医疗");
        map.put(R.id.btn_domestic1, "居家");
        map.put(R.id.btn_digital1, "数码");
        map.put(R.id.btn_otherSpend1, "其他支出");
        map.put(R.id.btn_salary1, "工资");
        map.put(R.id.btn_partTimeJob1, "兼职");
        map.put(R.id.btn_moneyManagement1, "理财");
        map.put(R.id.btn_redPacket1, "红包");
        map.put(R.id.btn_otherIncome1, "其他收入");

        return map.get(integer);
    }

    private void changeClassification(Button button){
        btnClassification.setText(returnClassification(button.getId()));

        hDetailList.clear();
        List<Record> recordList = LitePal.findAll(Record.class);
        for (Record record : recordList){
            if(returnClassification(button.getId()).equals("全部")){
                if (record.getDate().substring(0, 10).equals(nowTime())){
                    String time = "今天 " + record.getDate().substring(11, 16);
                    Display display = new Display(record.getIconId(), record.getCategory(), record.getSum(), time);
                    hDetailList.add(display);
                    mainDetailRecyclerView.scrollToPosition(hDetailList.size() - 1); // 将 RecyclerView 定位到最后一行
                }else{
                    Display display = new Display(record.getIconId(), record.getCategory(), record.getSum(), record.getDate().substring(5, 16));
                    hDetailList.add(display);
                    mainDetailRecyclerView.scrollToPosition(hDetailList.size() - 1); // 将 RecyclerView 定位到最后一行
                }
            }else if (record.getCategory().equals(returnClassification(button.getId()))){
                if (record.getDate().substring(0, 10).equals(nowTime())){
                    String time = "今天 " + record.getDate().substring(11, 16);
                    Display display = new Display(record.getIconId(), record.getCategory(), record.getSum(), time);
                    hDetailList.add(display);
                    mainDetailRecyclerView.scrollToPosition(hDetailList.size() - 1); // 将 RecyclerView 定位到最后一行
                }else{
                    Display display = new Display(record.getIconId(), record.getCategory(), record.getSum(), record.getDate().substring(5, 16));
                    hDetailList.add(display);
                    mainDetailRecyclerView.scrollToPosition(hDetailList.size() - 1); // 将 RecyclerView 定位到最后一行
                }
            }
        }
        displayAdapter.notifyDataSetChanged();
        dPopupWindow.dismiss();
    }

    private void addBackground() {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = Objects.requireNonNull(getActivity()).getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getActivity().getWindow().setAttributes(lp);
        //dismiss时恢复原样
        dPopupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = Objects.requireNonNull(getActivity()).getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().setAttributes(lp1);
        });
    }

    // 获取当前时间
    private String nowTime(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    // 初始化 RecyclerView 的数据
    private void initMainDetailRecords() {
        List<Record> recordList = LitePal.findAll(Record.class);
        for (Record record : recordList) {
            if (record.getDate().substring(0, 10).equals(nowTime())){
                String time = "今天 " + record.getDate().substring(11, 16);
                Display display = new Display(record.getIconId(), record.getCategory(), record.getSum(), time);
                hDetailList.add(display);
                mainDetailRecyclerView.scrollToPosition(hDetailList.size() - 1); // 将 RecyclerView 定位到最后一行
            }else{
                Display display = new Display(record.getIconId(), record.getCategory(), record.getSum(), record.getDate().substring(5, 16));
                hDetailList.add(display);
                mainDetailRecyclerView.scrollToPosition(hDetailList.size() - 1); // 将 RecyclerView 定位到最后一行
            }
        }
    }

    // 刷新 RecyclerView 的数据
    private void refreshDetailRecord() {
        hDetailList.clear();
        List<Record> recordList = LitePal.findAll(Record.class);
        for (Record record : recordList) {
            if (record.getDate().substring(0, 10).equals(nowTime())){
                String time = "今天 " + record.getDate().substring(11, 16);
                Display display = new Display(record.getIconId(), record.getCategory(), record.getSum(), time);
                hDetailList.add(display);
                mainDetailRecyclerView.scrollToPosition(hDetailList.size() - 1); // 将 RecyclerView 定位到最后一行
            }else{
                Display display = new Display(record.getIconId(), record.getCategory(), record.getSum(), record.getDate().substring(5, 16));
                hDetailList.add(display);
                mainDetailRecyclerView.scrollToPosition(hDetailList.size() - 1); // 将 RecyclerView 定位到最后一行
            }
        }
    }

}
