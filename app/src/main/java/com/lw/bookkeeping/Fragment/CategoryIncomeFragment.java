package com.lw.bookkeeping.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lw.bookkeeping.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryIncomeFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.btn_salary)
    Button btnSalary;

    @BindView(R.id.btn_partTimeJob)
    Button btnPartTimeJob;

    @BindView(R.id.btn_moneyManagement)
    Button btnMoneyManagement;

    @BindView(R.id.btn_redPacket)
    Button btnRedPacker;

    @BindView(R.id.btn_otherIncome)
    Button btnOtherIncome;

    private String category;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_income_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        initEvent();
    }

    private void initEvent(){
        btnSalary.setOnClickListener(this);
        btnPartTimeJob.setOnClickListener(this);
        btnRedPacker.setOnClickListener(this);
        btnMoneyManagement.setOnClickListener(this);
        btnOtherIncome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_salary:
                category = "工资";
                btnNavigation(btnSalary);
                break;
            case R.id.btn_partTimeJob:
                category = "兼职";
                btnNavigation(btnPartTimeJob);
                break;
            case R.id.btn_redPacket:
                category = "红包";
                btnNavigation(btnRedPacker);
                break;
            case R.id.btn_moneyManagement:
                category = "理财";
                btnNavigation(btnMoneyManagement);
                break;
            case R.id.btn_otherIncome:
                category = "其他";
                btnNavigation(btnOtherIncome);
                break;
        }
    }

    private void btnNavigation(Button button){
        String consumeWay = "收入";

        CategoryFragmentDirections.ActionCategoryFragmentToRecordFragment action =
                CategoryFragmentDirections.actionCategoryFragmentToRecordFragment(consumeWay, category);

        Navigation.findNavController(button)
                .navigate(action);
    }
}
