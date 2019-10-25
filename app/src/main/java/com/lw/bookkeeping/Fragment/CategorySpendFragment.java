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

public class CategorySpendFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.btn_cater)
    Button btnCater;

    @BindView(R.id.btn_shop)
    Button btnShop;

    @BindView(R.id.btn_daily)
    Button btnDaily;

    @BindView(R.id.btn_transport)
    Button btnTransport;

    @BindView(R.id.btn_vegetable)
    Button btnVegetable;

    @BindView(R.id.btn_fruit)
    Button btnFruit;

    @BindView(R.id.btn_snacks)
    Button btnSnacks;

    @BindView(R.id.btn_sports)
    Button btnSports;

    @BindView(R.id.btn_entertainment)
    Button btnEntertainment;

    @BindView(R.id.btn_communication)
    Button btnCommunication;

    @BindView(R.id.btn_costume)
    Button btnCostume;

    @BindView(R.id.btn_facial)
    Button btnFacial;

    @BindView(R.id.btn_housing)
    Button btnHousing;

    @BindView(R.id.btn_study)
    Button btnStudy;

    @BindView(R.id.btn_medical)
    Button btnMedical;

    @BindView(R.id.btn_domestic)
    Button btnDomestic;

    @BindView(R.id.btn_digital)
    Button btnDigital;

    @BindView(R.id.btn_otherspend)
    Button btnOtherspend;

    private String category;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_spend_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        initEvent();
    }

    private void initEvent(){
        btnCater.setOnClickListener(this);
        btnDaily.setOnClickListener(this);
        btnShop.setOnClickListener(this);
        btnTransport.setOnClickListener(this);
        btnVegetable.setOnClickListener(this);
        btnFruit.setOnClickListener(this);
        btnSnacks.setOnClickListener(this);
        btnSports.setOnClickListener(this);
        btnEntertainment.setOnClickListener(this);
        btnCommunication.setOnClickListener(this);
        btnCostume.setOnClickListener(this);
        btnFacial.setOnClickListener(this);
        btnHousing.setOnClickListener(this);
        btnStudy.setOnClickListener(this);
        btnMedical.setOnClickListener(this);
        btnDomestic.setOnClickListener(this);
        btnDigital.setOnClickListener(this);
        btnOtherspend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_cater:
                category = "餐饮";
                btnNavigation(btnCater);
                break;
            case R.id.btn_shop:
                category = "购物";
                btnNavigation(btnShop);
                break;
            case R.id.btn_daily:
                category = "日用";
                btnNavigation(btnDaily);
                break;
            case R.id.btn_transport:
                category = "交通";
                btnNavigation(btnTransport);
                break;
            case R.id.btn_entertainment:
                category = "娱乐";
                btnNavigation(btnEntertainment);
                break;
            case R.id.btn_vegetable:
                category = "蔬菜";
                btnNavigation(btnVegetable);
                break;
            case R.id.btn_snacks:
                category = "零食";
                btnNavigation(btnSnacks);
                break;
            case R.id.btn_fruit:
                category = "水果";
                btnNavigation(btnFruit);
                break;
            case R.id.btn_communication:
                category = "通讯";
                btnNavigation(btnCommunication);
                break;
            case R.id.btn_costume:
                category = "服饰";
                btnNavigation(btnCostume);
                break;
            case R.id.btn_medical:
                category = "医疗";
                btnNavigation(btnMedical);
                break;
            case R.id.btn_digital:
                category = "数码";
                btnNavigation(btnDigital);
                break;
            case R.id.btn_housing:
                category = "住房";
                btnNavigation(btnHousing);
                break;
            case R.id.btn_sports:
                category = "运动";
                btnNavigation(btnSports);
                break;
            case R.id.btn_facial:
                category = "美容";
                btnNavigation(btnFacial);
                break;
            case R.id.btn_study:
                category = "学习";
                btnNavigation(btnStudy);
                break;
            case R.id.btn_domestic:
                category = "居家";
                btnNavigation(btnDomestic);
                break;
            case R.id.btn_otherspend:
                category = "其他";
                btnNavigation(btnOtherspend);
                break;
        }
    }

    private void btnNavigation(Button button){
        String consumeWay = "支出";
        CategoryFragmentDirections.ActionCategoryFragmentToRecordFragment action =
                CategoryFragmentDirections.actionCategoryFragmentToRecordFragment(consumeWay, category);

        Navigation.findNavController(button)
                .navigate(action);
    }
}
