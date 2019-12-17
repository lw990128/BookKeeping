package com.lw.bookkeeping.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;

import com.google.gson.Gson;
import org.json.JSONArray;

import com.lw.bookkeeping.Entity.DataBean;
import com.lw.bookkeeping.Entity.Record;
import com.lw.bookkeeping.R;
import com.lw.bookkeeping.Util.GetJsonDataUtil;
import com.lw.bookkeeping.Util.SingleOptionsPicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.btn_backToCategory)
    Button btnBackToCategory;

    @BindView(R.id.btn_save)
    Button btnSave;

    @BindView(R.id.tv_sum)
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

    private ArrayList<DataBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static boolean isLoaded = false;

    // 时间选择器
    private TimePickerView pvTime;

    // 二级联动的两个选项
    private int select1, select2;

    //
    private PopupWindow mPopupWindow;

    private View vPopupWindow;

    // 来保存计算器输入的值
    private StringBuffer str = new StringBuffer();

    private boolean aBoolean1 = true;  // 判断是否按了"=" , 未按为true

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

        // 给组件加监听器
        initEvent();

        /*
          将用户在 CategoryFragment 所选的消费种类
          传到这个 RecordFragment
          同时将两个按钮设置为接收到的 consumeWay 和 category
         */
        if (getArguments() != null) {
            String consumeWay = RecordFragmentArgs.fromBundle(getArguments()).getConsumeWay();
            String category = RecordFragmentArgs.fromBundle(getArguments()).getCategory();

            btnConsumeWay.setText(consumeWay);
            btnCategory.setText(category);
        }

        // 使时间按钮显示当前时间
        btnTime.setText(nowTime());

        // 初始化时间选择器
        initTimePicker();

        // 启动 handle 线程
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) { // 如果已创建就不再重新创建子线程了
                        thread = new Thread(() -> {
                            // 子线程中解析消费方式和消费类型数据
                            initJsonData();
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;
                case MSG_LOAD_FAILED:
                    break;
            }
        }
    };

    private void initEvent() {
        btnSave.setOnClickListener(this);
        btnCategory.setOnClickListener(this);
        btnConsumeWay.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnPayWay.setOnClickListener(this);
        btnBackToCategory.setOnClickListener(this);
        tvSum.setOnClickListener(this);
    }

    // 获取当前时间
    private String nowTime(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_backToCategory:
                // 从 MainHomeFragment 回调到 CategoryFragment
                Navigation.findNavController(btnBackToCategory)
                        .popBackStack();
                break;
            case R.id.btn_payWay:
                ArrayList<String> list1 = new ArrayList<>();

                // 将数据添加到 list1 里面
                list1.add("现金");
                list1.add("银行卡");
                list1.add("支付宝");
                list1.add("微信");

                SingleOptionsPicker.openOptionsPicker(this, list1, 1, btnPayWay);
                break;
            // 两个按钮实现相同的功能
            case R.id.btn_consumeWay:
            case R.id.btn_category:
                if (isLoaded) {
                    showPickerView();
                }
                break;
            case R.id.btn_time:
                pvTime.show(v, false); // 弹出时间选择器，传递参数过去，回调的时候则可以绑定此 view
                break;
            case R.id.tv_sum:
                // 显示一个计算器
                showAnimation();
                break;
            case R.id.btn_save:
                Record record = new Record();
                record.setConsumeWay(btnConsumeWay.getText().toString());
                if (btnConsumeWay.getText().toString().equals("支出")){
                    record.setSum("-" + tvSum.getText().toString());
                }else{
                    record.setSum("+" + tvSum.getText().toString());
                }
                record.setPayWay(btnPayWay.getText().toString());
                record.setDate(btnTime.getText().toString());
                record.setRemark(etRemark.getText().toString());
                record.setCategory(btnCategory.getText().toString());
                record.setIconId(returnIconPath(btnCategory.getText().toString()));
                record.save();

                Navigation.findNavController(btnSave)
                        .navigate(R.id.action_recordFragment_to_mainFragment);


                break;
            default:
                break;
        }
    }

    /**
     * @param str 类型名
     * @return 类型对应的图标的id
     */
    private Integer returnIconPath(String str){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("餐饮", R.drawable.cater);
        map.put("购物", R.drawable.shop);
        map.put("日用", R.drawable.daily);
        map.put("交通", R.drawable.transport);
        map.put("蔬菜", R.drawable.vegetable);
        map.put("水果", R.drawable.fruit);
        map.put("零食", R.drawable.snacks);
        map.put("运动", R.drawable.sports);
        map.put("娱乐", R.drawable.entertainment);
        map.put("通讯", R.drawable.communication);
        map.put("服饰", R.drawable.costume);
        map.put("美容", R.drawable.facial);
        map.put("住房", R.drawable.housing);
        map.put("学习", R.drawable.study);
        map.put("医疗", R.drawable.medical);
        map.put("居家", R.drawable.housing);
        map.put("数码", R.drawable.digital);
        map.put("其他支出", R.drawable.otherspend);
        map.put("工资", R.drawable.salary);
        map.put("兼职", R.drawable.parttime);
        map.put("理财", R.drawable.moneymanagement);
        map.put("红包", R.drawable.redpacket);
        map.put("其他收入", R.drawable.otherincome);

        return map.get(str);
    }

    // 显示一个简易计算器
    private void showAnimation() {
        LayoutInflater inflater = (LayoutInflater) Objects.requireNonNull(getActivity())
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //引入弹窗布局
        vPopupWindow = Objects.requireNonNull(inflater).inflate(R.layout.calculator, null, false);
        mPopupWindow = new PopupWindow(vPopupWindow, ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT, true);
        //设置背景透明
        addBackground();

        //设置进出动画
        mPopupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        // 键盘弹出时，PopupWindow向上移动
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //引入依附的布局
        View parentView = LayoutInflater.from(getActivity()).inflate(R.layout.record_fragment, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        mPopupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

        // 注册组件
        initEvent1();
    }

    private void initEvent1(){
        Button btnOne = vPopupWindow.findViewById(R.id.btn_one);
        btnOne.setOnClickListener(v -> numOperation("1"));
        Button btnTwo = vPopupWindow.findViewById(R.id.btn_two);
        btnTwo.setOnClickListener(v -> numOperation("2"));
        Button btnThree = vPopupWindow.findViewById(R.id.btn_three);
        btnThree.setOnClickListener(v -> numOperation("3"));
        Button btnFour = vPopupWindow.findViewById(R.id.btn_four);
        btnFour.setOnClickListener(v -> numOperation("4"));
        Button btnFive = vPopupWindow.findViewById(R.id.btn_five);
        btnFive.setOnClickListener(v -> numOperation("5"));
        Button btnSix = vPopupWindow.findViewById(R.id.btn_six);
        btnSix.setOnClickListener(v -> numOperation("6"));
        Button btnSeven = vPopupWindow.findViewById(R.id.btn_seven);
        btnSeven.setOnClickListener(v -> numOperation("7"));
        Button btnEight = vPopupWindow.findViewById(R.id.btn_eight);
        btnEight.setOnClickListener(v -> numOperation("8"));
        Button btnNine = vPopupWindow.findViewById(R.id.btn_nine);
        btnNine.setOnClickListener(v -> numOperation("9"));
        Button btnZero = vPopupWindow.findViewById(R.id.btn_zero);
        btnZero.setOnClickListener(v -> numOperation("0"));
        Button btnCalculator = vPopupWindow.findViewById(R.id.btn_calculator);
        btnCalculator.setOnClickListener(v -> mPopupWindow.dismiss());
        Button btnDelete = vPopupWindow.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(v -> {
            if (str.length() == 1){
                str.deleteCharAt(str.length()-1);
                tvSum.setText("0");
            }else if (str.length() < 1) {
                tvSum.setText("0");
            }else {
                str.deleteCharAt(str.length()-1);
                tvSum.setText(str);
            }
        });
//        Button btnPlus = vPopupWindow.findViewById(R.id.btn_plus);
//        btnPlus.setOnClickListener(v -> oprOperation("+", "-"));
//        Button btnMinus = vPopupWindow.findViewById(R.id.btn_minus);
//        btnMinus.setOnClickListener(v -> oprOperation("-", "+"));
        Button btnPoint = vPopupWindow.findViewById(R.id.btn_point);
        btnPoint.setOnClickListener(v -> {
            if (str.length() == 0){
                str.append("0.");
            }else{
                if (aBoolean1){
                    if (str.indexOf(".") != (str.length() - 1)){
                        if (str.indexOf("+") == (str.length() - 1) ||
                                str.indexOf("-") == (str.length() - 1)){
                            str.append("0.");
                        }else{
                            str.append(".");
                        }
                    }
                }else{
                    str.replace(0, str.length(), "0.");
                    aBoolean1 = true;
                }
            }
            tvSum.setText(str);
        });
    }

    /**
     *
     * @param num 点击的数字
     */
    private void numOperation(String num){
        if (str.length() == 0){
            str.append(num);
        }else{
            if (tvSum.getText().toString().equals("0")){
                str.deleteCharAt(str.length() - 1);
                str.append(num);
            }else{
                str.append(num);
            }
        }
        tvSum.setText(str);
        aBoolean1 = true;
    }

//    private void oprOperation(String str1, String str2){
//        if (str.length() == 0){
//            str.append("0").append(str1);
//        }else{
//            if (str.indexOf(str1) != (str.length() - 1)){
//                if (str.indexOf(str2) == (str.length() - 1)){
//                    str.deleteCharAt(str.length() - 1);
//                    str.append(str1);
//                }else{
//                    str.append(str1);
//                }
//            }
//        }
//        tvSum.setText(str);
//        aBoolean1 = true;
//    }

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

    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 11, 28);
        // 时间选择器
        pvTime = new TimePickerBuilder(getActivity(), (date, v) -> { // 选中事件回调
            // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v 则为 null
            /*btn_Time.setText(getTime(date));*/
            Button btn = (Button) v;
            btn.setText(nowTime());
        })
                .setLayoutRes(R.layout.pickerview_custom_time, v ->  {
                    final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                    TextView tvCancel = v.findViewById(R.id.tv_cancel);
                    tvSubmit.setOnClickListener(v1 ->  {
                        pvTime.returnData();
                        pvTime.dismiss();
                    });
                    tvCancel.setOnClickListener(v1 ->  pvTime.dismiss());
                })
                .setType(new boolean[]{true, true, true, true, true, true})
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.parseColor("#BBBBBB"))
                .setBgColor(Color.parseColor("#F5F5F5"))
                .setTextColorCenter(Color.parseColor("#333333"))
                .setLineSpacingMultiplier(1.8f)
                .isCenterLabel(true)
                .setContentTextSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .setOutSideColor(0x00000000)
                .setOutSideCancelable(true)
                .isCyclic(true) // 是否循环滚动
                .isDialog(true) // 是否显示为对话框样式
                .build();

        pvTime.setKeyBackCancelable(true);//系统返回键监听屏蔽掉
    }

    private void initJsonData() { // 解析数据

        /*
         * 解析 assets 下的 category.json 文件
         * 关键逻辑在于循环体
         */
        // 获取assets目录下的json文件数据
        String DataBean = new GetJsonDataUtil().getJson(Objects.requireNonNull(getActivity()), "category.json");

        // 用 Gson 转成实体
        ArrayList<DataBean> dataBean = parseData(DataBean);

        /*
         * 添加消费方式数据
         *
         * 注意：如果是添加的 DataBean 实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView 会通过 getPickerViewText 方法获取字符串显示出来。
         */
        options1Items = dataBean;

        for (int i = 0; i < dataBean.size(); i++) { // 遍历消费方式

            // 消费方式的类型
            ArrayList<String> categoryList = new ArrayList<>(dataBean.get(i).getCategory());

            // 添加消费类型数据
            options2Items.add(categoryList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    // Gson 解析
    private ArrayList<DataBean> parseData(String result) {
        ArrayList<DataBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                DataBean entity = gson.fromJson(data.optJSONObject(i).toString(), DataBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    private void showPickerView() { // 弹出选择器
        new SingleOptionsPicker(this, select1, select2, options1Items, options2Items,
                (options1, options2, options3, v) -> {
                    // 返回的分别是两个级别的选中位置
                    String opt1tx = options1Items.get(options1).getPickerViewText();

                    String opt2tx = options2Items.get(options1).get(options2);

                    btnConsumeWay.setText(opt1tx);
                    btnCategory.setText(opt2tx);

                    // 将选择后的选中项赋值
                    select1 = options1;
                    select2 = options2;
                }).show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}