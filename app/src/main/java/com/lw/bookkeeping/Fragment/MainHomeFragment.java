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
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lw.bookkeeping.Entity.Budget;
import com.lw.bookkeeping.Entity.Display;
import com.lw.bookkeeping.Entity.Record;
import com.lw.bookkeeping.R;
import com.lw.bookkeeping.Adapter.DisplayAdapter;

import org.litepal.LitePal;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainHomeFragment extends Fragment implements View.OnClickListener  {

    @BindView(R.id.textView1)
    TextView tvMonthlyTotal;

    @BindView(R.id.textView3)
    TextView tvMonthlySpend;

    @BindView(R.id.textView5)
    TextView tvMonthlyIncome;

    @BindView(R.id.btn_setBudget)
    Button btnSetBudget;

    @BindView(R.id.btn_record)
    Button btnRecord;

    // 弹出窗口
    private PopupWindow mPopupWindow;

    private EditText etBudget;

    private RecyclerView mainHomeRecyclerView;

    // RecyclerView 的数据
    private List<Display> hRecordList = new ArrayList<>();

    // 设置数字为二位小数
    private DecimalFormat df = new DecimalFormat("0.00");

    // 判断数据是否更改
    private boolean isRefresh = false;

    int id;

    PopupWindow popupWindow;

    private double monthlySpend = 0;
    private double monthlyIncome = 0;

    DisplayAdapter displayAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        // 初始化组件
        initEvent();

        mainHomeRecyclerView = view.findViewById(R.id.main_home_recycler_view);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mainHomeRecyclerView.setLayoutManager(layoutManager);
        displayAdapter = new DisplayAdapter(hRecordList);
        mainHomeRecyclerView.setAdapter(displayAdapter);

        if (!isRefresh){
            initMainHomeRecords();
            isRefresh = true;
        }else{
            refreshMainHomeRecord();
        }

        displayAdapter.setOnItemClickListener((view1, position) -> {
            MainFragmentDirections.ActionMainFragmentToRecyclerViewFragment action =
                    MainFragmentDirections.actionMainFragmentToRecyclerViewFragment(position);
            Navigation.findNavController(mainHomeRecyclerView)
                    .navigate(action);
        });

        displayAdapter.setOnItemLongClickListener((view12, position) -> {
            id = position + 1;
            showRecyclerView();
        });
    }

    private void initEvent() {
        tvMonthlyIncome.setOnClickListener(this);
        tvMonthlySpend.setOnClickListener(this);
        tvMonthlyTotal.setOnClickListener(this);
        btnRecord.setOnClickListener(this);
        btnSetBudget.setOnClickListener(this);

        List<Record> recordList = LitePal.findAll(Record.class);
        for (Record record : recordList) {
            if (record.getDate().substring(0, 7).equals(nowTime().substring(0, 7))){
                if (record.getConsumeWay().equals("支出")){
                    monthlySpend += Math.abs(Double.parseDouble((record.getSum())));
                }else{
                    monthlyIncome += Math.abs(Double.parseDouble((record.getSum())));
                }
            }
        }

        tvMonthlySpend.setText(df.format(monthlySpend));
        tvMonthlyIncome.setText(df.format(monthlyIncome));
        String monthlyTotal = "今日支出 ¥" + df.format(monthlySpend) + " 收入 ¥" + df.format(monthlyIncome);
        tvMonthlyTotal.setText(monthlyTotal);

        Budget budget = LitePal.find(Budget.class, 1);
        if (budget == null){
            btnSetBudget.setText("设置预算");
        }else{
            double btnBudget = Double.parseDouble(budget.getBudget()) - monthlySpend;
            btnSetBudget.setText(df.format(btnBudget));
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_record:
                // 从 MainFragment 跳转到 RecordFragment
                Navigation.findNavController(btnRecord)
                        .navigate(R.id.action_mainFragment_to_recordFragment);
                break;
            case R.id.btn_setBudget:
                // 显示弹窗
                showAnimation();
                break;
            default:
                break;
        }
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
            hRecordList.clear();
            List<Record> recordList = LitePal.findAll(Record.class);
            for (Record record : recordList) {
                if (record.getDate().substring(0, 10).equals(nowTime())){
                    String time = "今天 " + record.getDate().substring(11, 16);
                    Display display = new Display(record.getIconId(), record.getCategory(), record.getSum(), time);
                    hRecordList.add(display);
                    mainHomeRecyclerView.scrollToPosition(hRecordList.size() - 1); // 将 recyclerview 定位到最后一行
                }
            }
            displayAdapter.notifyDataSetChanged();
            popupWindow.dismiss();
        });

    }

    // 显示弹窗
    private void showAnimation() {
        LayoutInflater inflater = (LayoutInflater) Objects.requireNonNull(getActivity())
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 引入弹窗布局
        View vPopupWindow = Objects.requireNonNull(inflater).inflate(R.layout.set_budget_popuplayout, null, false);
        mPopupWindow = new PopupWindow(vPopupWindow, ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT, true);
        // 设置背景透明
        addBackground();

        // 设置进出动画
        mPopupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        // 键盘弹出时，PopupWindow向上移动
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // 引入依附的布局
        View parentView = LayoutInflater.from(getActivity()).inflate(R.layout.main_home_fragment, null);
        // 相对于父控件的位置（例如正中央 Gravity.CENTER，下方 Gravity.BOTTOM 等），可以设置偏移或无偏移
        mPopupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

        etBudget = vPopupWindow.findViewById(R.id.et_budget);
        if (LitePal.find(Budget.class, 1) == null){
            etBudget.setText("");
        }else{
            Budget budget = LitePal.find(Budget.class, 1);
            etBudget.setText(df.format(Double.parseDouble(budget.getBudget())));
        }

        Button btnConfirm = vPopupWindow.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(v -> {
            if (LitePal.find(Budget.class, 1) == null){
                Budget budget = new Budget();
                budget.setBudget(etBudget.getText().toString());
                budget.save();
                btnSetBudget.setText(df.format(Double.parseDouble(etBudget.getText().toString()) - monthlySpend));
            }else{
                Budget budgetToUpdate = LitePal.find(Budget.class, 1);
                budgetToUpdate.setBudget(etBudget.getText().toString());
                budgetToUpdate.save();
                btnSetBudget.setText(df.format(Double.parseDouble(etBudget.getText().toString()) - monthlySpend));
            }
            mPopupWindow.dismiss();
        });
    }

    private void addBackground() {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = Objects.requireNonNull(getActivity()).getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getActivity().getWindow().setAttributes(lp);
        //dismiss时恢复原样
        mPopupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = Objects.requireNonNull(getActivity()).getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().setAttributes(lp1);
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

    // 获取当前时间
    private String nowTime(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return simpleDateFormat.format(date);
    }

     //初始化 RecyclerView 的内容
    private void initMainHomeRecords(){
        List<Record> recordList = LitePal.findAll(Record.class);
        for (Record record : recordList) {
            if (record.getDate().substring(0, 10).equals(nowTime())){
                String time = "今天 " + record.getDate().substring(11, 16);
                Display display = new Display(record.getIconId(), record.getCategory(), record.getSum(), time);
                hRecordList.add(display);
                mainHomeRecyclerView.scrollToPosition(hRecordList.size() - 1); // 将 RecyclerView 定位到最后一行
            }
        }
    }

    // 更新 RecyclerView 的内容
    private void refreshMainHomeRecord(){
        hRecordList.clear();
        List<Record> recordList = LitePal.findAll(Record.class);
        for (Record record : recordList) {
            if (record.getDate().substring(0, 10).equals(nowTime())){
                String time = "今天 " + record.getDate().substring(11, 16);
                Display display = new Display(record.getIconId(), record.getCategory(), record.getSum(), time);
                hRecordList.add(display);
                mainHomeRecyclerView.scrollToPosition(hRecordList.size() - 1); // 将 recyclerview 定位到最后一行
            }
        }
    }
}
