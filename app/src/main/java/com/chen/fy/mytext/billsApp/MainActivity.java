package com.chen.fy.mytext.billsApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chen.fy.mytext.adapter.MyFragmentPagerAdapter;
import com.chen.fy.mytext.entity.BillInfo;
import com.chen.fy.mytext.fragment.BarChartFragment;
import com.chen.fy.mytext.fragment.CountFragment;
import com.chen.fy.mytext.fragment.DetailFragmentTest;
import com.chen.fy.mytext.fragment.IncomeFragment;
import com.chen.fy.mytext.fragment.InfoFragment;
import com.chen.fy.mytext.fragment.PayFragment;
import com.chen.fy.mytext.fragment.PieChartFragment;
import com.chen.fy.mytext.fragment.TableFragment;
import com.chen.fy.mytext.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    //底部导航栏
    private RadioGroup radioGroup;
    private RadioButton radioBtn_table;
    private RadioButton radioBtn_detail;
    private RadioButton radioBtn_count;
    private RadioButton radioBtn_info;

    //fragment
    private DetailFragmentTest detailFragmentTest;
    private TableFragment tableFragment;
    private InfoFragment infoFragment;
    private CountFragment countFragment;

    public static Fragment[] fragments = new Fragment[4];
    public static boolean[] fragmentsUpdateFlag = new boolean[4];

    //viewPager
    private ViewPager viewPager;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    //代表界面view的常量(0代表第一页
    public static final int PAGER_ONE = 0;
    public static final int PAGER_TWO = 1;
    public static final int PAGER_THREE = 2;
    public static final int PAGER_FOUR = 3;

    public static String date[] = {Utils.time_change1(System.currentTimeMillis())};
    private List<BillInfo> list = new ArrayList<>();

    //获取的新账单数据
    private String new_number;
    private String new_way;
    private String new_type;
    private String new_date;
    private String new_time;

    public List<BillInfo> getList() {
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);


        //底部导航栏
        radioGroup = findViewById(R.id.radio_group_main);
        radioBtn_table = findViewById(R.id.radioBtn_table);
        radioBtn_detail = findViewById(R.id.radioBtn_detail);
        radioBtn_count = findViewById(R.id.radioBtn_count);
        radioBtn_info = findViewById(R.id.radioBtn_info);
        radioGroup.setOnCheckedChangeListener(this);

        //配置viewPager
        viewPager = findViewById(R.id.viewPage);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setCurrentItem(1);     //设置一开始显示的界面
        viewPager.addOnPageChangeListener(this);  //界面改变监听器

        //若有新数据载入,则加入数据库
        get_newData();

        //创建fragment对象
        detailFragmentTest = DetailFragmentTest.newInstance(date[0]);
        tableFragment = TableFragment.newInstance(date[0]);
        infoFragment = InfoFragment.newInstance();
        countFragment = CountFragment.newInstance();
        fragments[0] = tableFragment;
        fragments[1] = detailFragmentTest;
        fragments[2] = countFragment;
        fragments[3] = infoFragment;
        fragmentsUpdateFlag[0] = false;
        fragmentsUpdateFlag[1] = false;
        fragmentsUpdateFlag[2] = false;
        fragmentsUpdateFlag[3] = false;
    }

    //引入新数据
    public void get_newData() {
        if (getIntent() != null) {
            new_number = getIntent().getStringExtra("number");
            new_way = getIntent().getStringExtra("ways");
            new_type = getIntent().getStringExtra("type");
            new_date = getIntent().getStringExtra("date");  //转化为 年-月
            new_time = getIntent().getStringExtra("time"); //转化为 日 小时:分钟
        }
        if (new_date != null) {
            date[0] = new_date;   //显示当前新数据的日期
        }
        //把账单数据存放到数据库
        StartActivity.billdao.insert(LoginViewActivity.username
                , new_date, new_time, new_way, new_type, new_number);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;  //消化事件
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                finish();
                startActivity(intent);
                break;
        }
        return true;   //消化事件
    }

    //实现可以在一个fragment中进行动态的添加和删除fragment(因为在fragment中不能创建FragmentManager)
    public void setChantEssayFragment(String date) {
//        getSupportFragmentManager().beginTransaction().remove(detailFragmentTest).commitAllowingStateLoss();
//        Bundle args = new Bundle();
//        args.putString("date",date);
//        detailFragmentTest.setArguments(args);
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_main, detailFragmentTest)
//                .addToBackStack(null).commitAllowingStateLoss();
    }

    //报表碎片当首次进入时显示的fragment
    public void FirstTableFragment(PieChartFragment pieChartFragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.table_fragment_test, pieChartFragment)
                .addToBackStack(null).commitAllowingStateLoss();
    }

    //报表碎片Fragment嵌套时的调用
    public void TableFragmentController(String type, PieChartFragment pieChartFragment, BarChartFragment barChartFragment) {
        if (type.equals("饼图")) {
            getSupportFragmentManager().beginTransaction().remove(barChartFragment).addToBackStack(null).commitAllowingStateLoss();
            getSupportFragmentManager().beginTransaction().remove(pieChartFragment).addToBackStack(null).commitAllowingStateLoss();
            getSupportFragmentManager().beginTransaction().add(R.id.table_fragment_test, pieChartFragment)
                    .addToBackStack(null).commitAllowingStateLoss();
        }
        if (type.equals("柱图")) {
            getSupportFragmentManager().beginTransaction().remove(barChartFragment).addToBackStack(null).commitAllowingStateLoss();
            getSupportFragmentManager().beginTransaction().remove(pieChartFragment).addToBackStack(null).commitAllowingStateLoss();
            getSupportFragmentManager().beginTransaction().add(R.id.table_fragment_test, barChartFragment)
                    .addToBackStack(null).commitAllowingStateLoss();
        }
    }

    //记账碎片当首次进入时显示的fragment,把fragment都add进去,然后通过hide和show进行隐藏和显示
    public void FirstCountFragment(IncomeFragment incomeFragment, PayFragment payFragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.count_fragment, payFragment)
                .addToBackStack(null).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().hide(payFragment).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().add(R.id.count_fragment, incomeFragment)
                .addToBackStack(null).commitAllowingStateLoss();
    }

    //记账碎片Fragment嵌套时的调用
    public void CountFragmentController(boolean pay_or_income, IncomeFragment incomeFragment, PayFragment payFragment) {

        if (pay_or_income) { //收入
            getSupportFragmentManager().beginTransaction().hide(payFragment).commitAllowingStateLoss();
            getSupportFragmentManager().beginTransaction().show(incomeFragment).commitAllowingStateLoss();
        } else { //支出
            getSupportFragmentManager().beginTransaction().hide(incomeFragment).commitAllowingStateLoss();
            getSupportFragmentManager().beginTransaction().show(payFragment).commitAllowingStateLoss();
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //底部导航栏的选择(当设置默认选中时,第一次加载不执行点击事件,因为checked的状态没有改变)
        switch (checkedId) {
            case R.id.radioBtn_table: {
                viewPager.setCurrentItem(PAGER_ONE);
            }
            break;
            case R.id.radioBtn_detail: {
                viewPager.setCurrentItem(PAGER_TWO);
            }
            break;
            case R.id.radioBtn_count: {
                viewPager.setCurrentItem(PAGER_THREE);
            }
            break;
            case R.id.radioBtn_info: {
                viewPager.setCurrentItem(PAGER_FOUR);
            }
            break;
        }
    }

    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {
        // i 表示界面当前的状态,0表示什么都没做,1正在滑动,2滑动完毕
        if (i == 2) {
            switch (viewPager.getCurrentItem()) {
                case PAGER_ONE:
                    radioBtn_table.setChecked(true);
                    break;
                case PAGER_TWO:
                    radioBtn_detail.setChecked(true);
                    break;
                case PAGER_THREE:
                    radioBtn_count.setChecked(true);
                    break;
                case PAGER_FOUR:
                    radioBtn_info.setChecked(true);
                    break;
            }
        }
    }
}
