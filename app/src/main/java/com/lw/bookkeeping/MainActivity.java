package com.lw.bookkeeping;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 加载数据库
        LitePal.getDatabase();
    }
}
