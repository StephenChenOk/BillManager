package com.chen.fy.mytext.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chen.fy.mytext.billsApp.R;
import com.chen.fy.mytext.billsApp.StartActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class PieChartFragment extends Fragment {

    private ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

    private PieChart new_pie_chart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //反射一个视图View,就像Activity中的setContentView方法
        View view = inflater.inflate(R.layout.fragment_piechart,container,false);
        new_pie_chart = view.findViewById(R.id.mPieChart);
        return view;
    }

    //相当于Activity中的onCreate方法的重写
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    //当fragment从hide到show或者从show到hide时调用,常用于在此中进行数据更新(不会调用onCreate,onStart,onResume等方法)
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("chen","1pie_onHide!!!");
        Log.d("chen","1bar_onShow!!!");
    }

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 添加数据均匀饼装图
         */
        int[] colors = {Color.parseColor("#faa74c"), Color.parseColor("#58D4C5")
                , Color.parseColor("#36a3eb"), Color.parseColor("#cc435f")
                , Color.parseColor("#f1ea56"), Color.parseColor("#f49468")
                , Color.parseColor("#d5932c"), Color.parseColor("#34b5cc")
                , Color.parseColor("#8169c6"), Color.parseColor("#ca4561")
                , Color.parseColor("#fee335")};


        //添加数据
        if (entries==null) {
            entries = new ArrayList<PieEntry>();
        }else{
            entries.clear();
        }

        if(TableFragment.wayTable.equals("收入")) {
            if (StartActivity.salary_table != 0) {
                PieEntry pieEntry1 = new PieEntry(StartActivity.salary_table, "薪水");
                entries.add(pieEntry1);
            }
            if (StartActivity.part_time_job_table != 0) {
                PieEntry pieEntry2 = new PieEntry(StartActivity.part_time_job_table, "兼职");
                entries.add(pieEntry2);
            }
            if (StartActivity.shares_table != 0) {
                PieEntry pieEntry3 = new PieEntry(StartActivity.shares_table, "股票");
                entries.add(pieEntry3);
            }
            if (StartActivity.income_red_paper_table != 0) {
                PieEntry pieEntry4 = new PieEntry(StartActivity.income_red_paper_table, "收红包");
                entries.add(pieEntry4);
            }
            if (StartActivity.income_other_table != 0) {
                PieEntry pieEntry5 = new PieEntry(StartActivity.income_other_table, "其它收入");
                entries.add(pieEntry5);
            }
        }
        if(TableFragment.wayTable.equals("支出")) {
            if (StartActivity.food_table != 0) {
                PieEntry pieEntry6 = new PieEntry(StartActivity.food_table, "饮食");
                entries.add(pieEntry6);
            }
            if (StartActivity.shopping_table != 0) {
                PieEntry pieEntry7 = new PieEntry(StartActivity.shopping_table, "购物");
                entries.add(pieEntry7);
            }
            if (StartActivity.traffic_table != 0) {
                PieEntry pieEntry8 = new PieEntry(StartActivity.traffic_table, "交通");
                entries.add(pieEntry8);
            }
            if (StartActivity.commodity_table != 0) {
                PieEntry pieEntry9 = new PieEntry(StartActivity.commodity_table, "日用");
                entries.add(pieEntry9);
            }
            if (StartActivity.treatment_table != 0) {
                PieEntry pieEntry10 = new PieEntry(StartActivity.treatment_table, "医疗");
                entries.add(pieEntry10);
            }
            if (StartActivity.pay_red_paper_table != 0) {
                PieEntry pieEntry11 = new PieEntry(StartActivity.pay_red_paper_table, "发红包");
                entries.add(pieEntry11);
            }
            if (StartActivity.pay_other_table != 0) {
                PieEntry pieEntry12 = new PieEntry(StartActivity.pay_other_table, "其它支出");
                entries.add(pieEntry12);
            }
        }

        if (entries.size() != 0) {
            PieChartEntity pieChartEntity = new PieChartEntity(new_pie_chart, entries, new String[]{"", "", ""}
                    , colors, 12f, Color.GRAY, PieDataSet.ValuePosition.OUTSIDE_SLICE);
            pieChartEntity.setHoleEnabled(Color.TRANSPARENT, 15f, Color.TRANSPARENT, 10f);
            pieChartEntity.setLegendEnabled(false);
            pieChartEntity.setPercentValues(true);
        }

    }
}
/**
 * 饼状图
 * Created by jin
 */
class PieChartEntity {
    private PieChart mChart;
    private List<PieEntry> mEntries;
    private String[] labels;
    private int[] mPieColors;
    private int mValueColor;
    private float mTextSize;
    private PieDataSet.ValuePosition mValuePosition;

    public PieChartEntity(PieChart chart, List<PieEntry> entries, String[] labels,
                          int[] chartColor, float textSize, int valueColor, PieDataSet.ValuePosition valuePosition) {
        this.mChart = chart;
        this.mEntries = entries;
        this.labels = labels;
        this.mPieColors = chartColor;
        this.mTextSize = textSize;
        this.mValueColor = valueColor;
        this.mValuePosition = valuePosition;
        initPieChart();
    }

    public PieChartEntity(PieChart chart, List<PieEntry> entries, String[] labels,
                          int[] chartColor, float textSize, int valueColor) {
        this(chart, entries, labels, chartColor, textSize, valueColor, PieDataSet.ValuePosition.INSIDE_SLICE);

    }

    private void initPieChart() {
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDrawCenterText(false);
        mChart.getDescription().setEnabled(false);
        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setDrawEntryLabels(true);
        setChartData();
        mChart.animateY(1000, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(1f);
        l.setYOffset(0f);
        // entry label styling
        mChart.setEntryLabelColor(mValueColor);
        mChart.setEntryLabelTextSize(mTextSize);
        mChart.setExtraOffsets(10, 10, 10, 10);
    }

    public void setHoleDisenabled() {
        mChart.setDrawHoleEnabled(false);
    }

    /**
     * 中心圆是否可见
     *
     * @param holeColor   中心圆颜色
     * @param holeRadius  半径
     * @param transColor  透明圆颜色
     * @param transRadius 透明圆半径
     */
    public void setHoleEnabled(int holeColor, float holeRadius, int transColor, float transRadius) {
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(holeColor);
        mChart.setTransparentCircleColor(transColor);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(holeRadius);
        mChart.setTransparentCircleRadius(transRadius);
    }

    private void setChartData() {
        PieDataSet dataSet = new PieDataSet(mEntries, "");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(5f);
//        dataSet.setEntryLabelsColor(mValueColor);
        dataSet.setColors(mPieColors);
        //dataSet.setSelectionShift(0f);
        dataSet.setYValuePosition(mValuePosition);
        dataSet.setXValuePosition(mValuePosition);
        dataSet.setValueLineColor(mValueColor);
        dataSet.setSelectionShift(15f);
        dataSet.setValueLinePart1Length(0.6f);
        dataSet.setValueLineColor(mValueColor);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(mTextSize);
        data.setValueTextColor(mValueColor);
        data.setValueTextColor(mValueColor);
        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);
        mChart.invalidate();
    }

    /**
     * <p>说明文字是否可见</p>
     *
     * @param enabled true 可见,默认可见
     */
    public void setLegendEnabled(boolean enabled) {
        mChart.getLegend().setEnabled(enabled);
        mChart.invalidate();
    }

    public void setPercentValues(boolean showPercent) {
        mChart.setUsePercentValues(showPercent);
    }
}
