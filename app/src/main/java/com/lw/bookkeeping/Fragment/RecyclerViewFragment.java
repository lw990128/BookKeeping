package com.lw.bookkeeping.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.lw.bookkeeping.Entity.Record;
import com.lw.bookkeeping.R;

import org.litepal.LitePal;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.image_category)
    ImageView imageCategory;

    @BindView(R.id.tv_displayCategory)
    TextView tvDisplayCategory;

    @BindView(R.id.tv_displaySum)
    TextView tvDisplaySum;

    @BindView(R.id.payWay)
    TextView tvPayWay;

    @BindView(R.id.time)
    TextView tvTime;

    @BindView(R.id.remark)
    TextView tvRemark;

    @BindView(R.id.btn_back)
    Button btnBack;

    private int position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recyclerview_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (getArguments() != null){
            position = RecyclerViewFragmentArgs.fromBundle(getArguments()).getPosition() + 1;
        }

        initEvent();
    }

    private void initEvent(){
        btnBack.setOnClickListener(this);

        Record record = LitePal.find(Record.class, position);
        Glide.with(this).load(record.getIconId()).into(imageCategory);
        tvDisplayCategory.setText(record.getCategory());
        tvDisplaySum.setText(record.getSum());
        tvPayWay.setText(record.getPayWay());
        if (record.getRemark() != null){
            tvRemark.setText(record.getRemark());
        }else{
            tvRemark.setText("--");
        }
        tvTime.setText(record.getDate());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_back) {
            Navigation.findNavController(btnBack)
                    .popBackStack();
        }
    }
}
