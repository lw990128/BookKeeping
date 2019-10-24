package com.lw.bookkeeping.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lw.bookkeeping.Entity.HomeRecord;
import com.lw.bookkeeping.R;
import com.lw.bookkeeping.Adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainDetailFragment extends Fragment {

    private List<HomeRecord> dRecordList = new ArrayList<>();

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
        initMainDetailRecords();
        RecyclerView mainDetailRecyclerView = view.findViewById(R.id.main_detail_recycler_view);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mainDetailRecyclerView.setLayoutManager(layoutManager);
        HomeAdapter adapter = new HomeAdapter(dRecordList);
        mainDetailRecyclerView.setAdapter(adapter);
    }

    private void initMainDetailRecords() {
        HomeRecord cater = new HomeRecord("-9", R.mipmap.cater);
        dRecordList.add(cater);
        HomeRecord communication = new HomeRecord("-20", R.mipmap.communication);
        dRecordList.add(communication);
        HomeRecord digital = new HomeRecord("-2000", R.mipmap.digital);
        dRecordList.add(digital);
        HomeRecord fruit = new HomeRecord("-15", R.mipmap.fruit);
        dRecordList.add(fruit);
        HomeRecord redPocket = new HomeRecord("+100", R.mipmap.redpacket);
        dRecordList.add(redPocket);
        HomeRecord partTime = new HomeRecord("+50", R.mipmap.parttime);
        dRecordList.add(partTime);
        HomeRecord otherIncome = new HomeRecord("+1000", R.mipmap.otherincome);
        dRecordList.add(otherIncome);
        HomeRecord domestic = new HomeRecord("-1000", R.mipmap.domestic);
        dRecordList.add(domestic);
        HomeRecord otherspend = new HomeRecord("-399", R.mipmap.otherspend);
        dRecordList.add(otherspend);
        HomeRecord salary = new HomeRecord("+2000", R.mipmap.salary);
        dRecordList.add(salary);
    }
}
