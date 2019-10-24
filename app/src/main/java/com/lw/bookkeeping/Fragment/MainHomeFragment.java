package com.lw.bookkeeping.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lw.bookkeeping.Entity.HomeRecord;
import com.lw.bookkeeping.R;
import com.lw.bookkeeping.Adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainHomeFragment extends Fragment  {

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


    private List<HomeRecord> hRecordList = new ArrayList<>();

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

        initMainHomeRecords();

        btnRecord.setOnClickListener(v -> Navigation.findNavController(btnRecord)
                .navigate(R.id.action_mainFragment_to_recordFragment));

        RecyclerView mainHomeRecyclerView = view.findViewById(R.id.main_home_recycler_view);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mainHomeRecyclerView.setLayoutManager(layoutManager);
        HomeAdapter adapter = new HomeAdapter(hRecordList);
        mainHomeRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnItemLongClickListener(new HomeAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(), "nihao", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initMainHomeRecords() {
        HomeRecord cater = new HomeRecord("-9", R.mipmap.cater, "餐饮");
        hRecordList.add(cater);
        HomeRecord communication = new HomeRecord("-20", R.mipmap.communication, "通讯");
        hRecordList.add(communication);
        HomeRecord digital = new HomeRecord("-2000", R.mipmap.digital, "数码");
        hRecordList.add(digital);
        HomeRecord fruit = new HomeRecord("-15", R.mipmap.fruit, "水果");
        hRecordList.add(fruit);
        HomeRecord redPocket = new HomeRecord("+100", R.mipmap.redpacket, "红包");
        hRecordList.add(redPocket);
        HomeRecord partTime = new HomeRecord("+50", R.mipmap.parttime, "兼职");
        hRecordList.add(partTime);
        HomeRecord otherIncome = new HomeRecord("+1000", R.mipmap.otherincome, "其他收入");
        hRecordList.add(otherIncome);
    }
}
