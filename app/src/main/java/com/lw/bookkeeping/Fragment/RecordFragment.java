package com.lw.bookkeeping.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lw.bookkeeping.Entity.Record;
import com.lw.bookkeeping.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.btn_backToCategory)
    Button btnBackToCategory;

    @BindView(R.id.btn_takePicture)
    Button btnTakePicture;

    @BindView(R.id.btn_save)
    Button btnSave;

    @BindView(R.id.text_sum)
    TextView tvSum;

    @BindView(R.id.btn_consumeWay)
    Button btnConsumeWay;

    @BindView(R.id.btn_category)
    Button btnCategory;

    @BindView(R.id.btn_payWay)
    Button btnPayWay;

    @BindView(R.id.btn_time)
    Button btnTime;

    @BindView(R.id.edit_remark)
    EditText etRemark;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.record_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        initEvent();

        if (getArguments() != null) {
            String consumeWay = RecordFragmentArgs.fromBundle(getArguments()).getConsumeWay();
            String category = RecordFragmentArgs.fromBundle(getArguments()).getCategory();

            btnConsumeWay.setText(consumeWay);
            btnCategory.setText(category);
        }

        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = simpleDateFormat.format(now);
        btnTime.setText(time);
    }


    private void initEvent() {
        btnSave.setOnClickListener(this);
        btnCategory.setOnClickListener(this);
        btnConsumeWay.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnTakePicture.setOnClickListener(this);
        btnPayWay.setOnClickListener(this);
        btnBackToCategory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_backToCategory:
                Navigation.findNavController(btnBackToCategory)
                        .popBackStack();
                break;
            case R.id.btn_save:
                Record record = new Record();
//                record.set
                break;
            case R.id.btn_time:

            default:
                break;
        }
    }
}
