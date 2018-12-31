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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BaseDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

public class BarChartFragment extends Fragment {

    private BarChart mBarChart;
    private BarChartEntity barChartEntity;
    private String[] labels;
    private float[] values;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //反射一个视图View,就像Activity中的setContentView方法
        View view = inflater.inflate(R.layout.fragment_barchart, container, false);
        return view;
    }

    //相当于Activity中的onCreate方法的重写
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBarChart = view.findViewById(R.id.new_the_bar_chart);
    }

    @Override
    //当fragment从hide到show或者从show到hide时调用,常用于在此中进行数据更新(不会调用onCreate,onStart,onResume等方法)
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        Log.d("chen","2bar_onHide!!!");
        Log.d("chen","2pie_onHide!!!");
    }

    @Override
    public void onResume() {
        super.onResume();

        if (TableFragment.wayTable.equals("收入")) {

            labels = new String[5];
            labels[0] = "工资";
            labels[1] = "兼职";
            labels[2] = "股票";
            labels[3] = "收红包";
            labels[4] = "其它";

            values = new float[5];
            values[0] = StartActivity.salary_table;
            values[1] = StartActivity.part_time_job_table;
            values[2] = StartActivity.shares_table;
            values[3] = StartActivity.income_red_paper_table;
            values[4] = StartActivity.income_other_table;

            int[] chartColors = {
                    Color.parseColor("#45A2FF"),
                    Color.parseColor("#58D4C5"),
                    Color.parseColor("#45A2FF"),
                    Color.parseColor("#58D4C5"),
                    Color.parseColor("#FDB25F"),
            };

            ArrayList<BarEntry>[] entries = new ArrayList[5];
            ArrayList<BarEntry> entries1 = new ArrayList<>();

            entries1.add(new BarEntry(0.5f, values[0]));
            entries1.add(new BarEntry(1.0f, values[1]));
            entries1.add(new BarEntry(1.5f, values[2]));
            entries1.add(new BarEntry(2.0f, values[3]));
            entries1.add(new BarEntry(2.5f, values[4]));

            entries[0] = entries1;


            if (mBarChart.getData() != null) {
                mBarChart.getData().clearValues();
            }
            barChartEntity = new BarChartEntity(mBarChart, entries, labels, chartColors, Color.parseColor("#999999"), 13f);
        }

        if (TableFragment.wayTable.equals("支出")) {

            labels = new String[7];
            labels[0] = "餐饮";
            labels[1] = "购物";
            labels[2] = "交通";
            labels[3] = "日用";
            labels[4] = "医疗";
            labels[5] = "发红包";
            labels[6] = "其它";

            values = new float[7];
            values[0] = StartActivity.food_table;
            values[1] = StartActivity.shopping_table;
            values[2] = StartActivity.traffic_table;
            values[3] = StartActivity.commodity_table;
            values[4] = StartActivity.treatment_table;
            values[5] = StartActivity.pay_red_paper_table;
            values[6] = StartActivity.pay_other_table;

            //柱子颜色
            int[] chartColors = {
                    Color.parseColor("#45A2FF"),
                    Color.parseColor("#58D4C5"),
                    Color.parseColor("#FDB25F"),
                    Color.parseColor("#45A2FF"),
                    Color.parseColor("#58D4C5"),
                    Color.parseColor("#FDB25F"),
                    Color.parseColor("#FDB25F"),
            };

            ArrayList<BarEntry>[] entries = new ArrayList[7];
            ArrayList<BarEntry> entries1 = new ArrayList<>();

            entries1.add(new BarEntry(0.5f, values[0]));
            entries1.add(new BarEntry(1.0f, values[1]));
            entries1.add(new BarEntry(1.5f, values[2]));
            entries1.add(new BarEntry(2.0f, values[3]));
            entries1.add(new BarEntry(2.5f, values[4]));
            entries1.add(new BarEntry(3.0f, values[5]));
            entries1.add(new BarEntry(3.5f, values[6]));

            entries[0] = entries1;


            if (mBarChart.getData() != null) {
                mBarChart.getData().clearValues();
            }
            barChartEntity = new BarChartEntity(mBarChart, entries, labels, chartColors, Color.parseColor("#999999"), 13f);
        }
        barChartEntity.setBarWidth(0.2f);
        barChartEntity.setDrawValueAboveBar(true);
        mBarChart.setPinchZoom(false);
        mBarChart.setScaleEnabled(false);

        mBarChart.animateY(2000, Easing.EasingOption.EaseInOutQuart);
        barChartEntity.setAxisFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";
            }
        }, null);

        mBarChart.getLegend().setEnabled(false);

        /**
         * 拼接柱状图上文字，涉及到修改源码
         */
        mBarChart.getData().setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                switch ((int) ((entry.getX() * 10) / 5)) {
                    case 1:
                        return labels[0] + values[0];
                    case 2:
                        return labels[1] + values[1];
                    case 3:
                        return labels[2] + values[2];
                    case 4:
                        return labels[3] + values[3];
                    case 5:
                        return labels[4] + values[4];
                    case 6:
                        return labels[5] + values[5];
                    case 7:
                        return labels[6] + values[6];
                    default:
                        return null;
                }
            }
        });

        /**
         * 处理当数据都为0，不好看情况
         */
        float yMax = mBarChart.getData().getYMax() == 0 ? 100f : mBarChart.getData().getYMax();
        float delta = yMax / 5.5f;
        mBarChart.getAxisLeft().setAxisMaximum(yMax + delta);

        float yMin = mBarChart.getData().getYMin();
        if (yMin == 0) {
            yMin = 0;
        }
        float deltaMin = yMin / 5.0f;
        mBarChart.getAxisLeft().setAxisMinimum(yMin - deltaMin);
    }
}

/**
 * 柱状图
 * Created by jin
 */

class BarChartEntity extends BaseChartEntry<BarEntry> {
    public BarChartEntity(BarLineChartBase chart, List<BarEntry>[] entries, String[] labels, int[] chartColor, int valueColor, float textSize) {
        super(chart, entries, labels, chartColor, valueColor, textSize);
    }

    @Override
    protected void initChart() {
        super.initChart();
        mChart.getAxisLeft().setDrawGridLines(true);
        mChart.getAxisLeft().enableGridDashedLine(10f, 15f, 0f);
        mChart.getAxisLeft().setGridLineWidth(0.5f);
        mChart.getAxisLeft().setGridColor(Color.parseColor("#f5f5f5"));
        mChart.getAxisLeft().setDrawZeroLine(false);
        mChart.getAxisRight().setDrawZeroLine(false);
        mChart.getAxisRight().setZeroLineWidth(0f);
        mChart.getAxisLeft().setZeroLineWidth(0f);
        mChart.getAxisLeft().setDrawAxisLine(false);
        mChart.getXAxis().setDrawAxisLine(false);
        mChart.getXAxis().setAxisMinimum(0);

    }

    @Override
    protected void setChartData() {
        BarDataSet barDataSet;
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            barDataSet = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            barDataSet.setValues(mEntries[0]);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            barDataSet = new BarDataSet(mEntries[0], labels == null ? "" : labels[0]);
            barDataSet.setColors(mChartColors);
            List<Integer> colors = new ArrayList<>();
            for (int color : mChartColors) {
                colors.add(color);
            }
            barDataSet.setValueTextColors(colors);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(barDataSet);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(mTextSize);
            data.setBarWidth(0.9f);
            mChart.setData(data);
        }
    }


    public void setDrawValueAboveBar(boolean aboveBar) {
        ((BarChart) mChart).setDrawValueAboveBar(aboveBar);
    }

    /**
     * <p>设置bar宽度</p>
     *
     * @param barWidth float
     */
    public void setBarWidth(float barWidth) {
        ((BarChart) mChart).getData().setBarWidth(barWidth);
    }
}

abstract class BaseChartEntry<T extends Entry> {
    protected BarLineChartBase mChart;

    protected List<T>[] mEntries;
    protected String[] labels;
    protected int[] mChartColors;
    protected float mTextSize;
    protected int mValueColor;

    protected BaseChartEntry(BarLineChartBase chart, List<T>[] entries, String[] labels,
                             int[] chartColor, int valueColor, float textSize) {
        this.mChart = chart;
        this.mEntries = entries;
        this.labels = labels;
        this.mValueColor = valueColor;
        this.mChartColors = chartColor;
//        this.mTextSize = textSize;
        this.mTextSize = 11f;
        initChart();
    }

    /**
     * <p>初始化chart</p>
     */
    protected void initChart() {
        initProperties();
        setChartData();
        initLegend(Legend.LegendForm.LINE, mTextSize, mValueColor);
        initXAxis(mValueColor, mTextSize);
        initLeftAxis(mValueColor, mTextSize);
    }


    private void initLeftAxis(int color, float textSize) {
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(color);
        leftAxis.setTextSize(textSize);
        float yMax = mChart.getData().getYMax() == 0 ? 100f : mChart.getData().getYMax();
        leftAxis.setAxisMaximum(yMax + yMax * 0.007f);
//        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularityEnabled(false);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setLabelCount(6);
        leftAxis.setAxisLineWidth(1f);
        leftAxis.setAxisLineColor(mValueColor);

        mChart.getAxisRight().setEnabled(false);

    }

    private void initXAxis(int color, float textSize) {
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(textSize);
        xAxis.setAxisMinimum(0);
        xAxis.setTextColor(color);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawLabels(true);
        xAxis.setAxisLineWidth(1f);
        xAxis.setLabelCount(8);
        xAxis.setDrawLimitLinesBehindData(true);
        xAxis.setAxisLineColor(mValueColor);
        xAxis.setCenterAxisLabels(false);
        xAxis.setAxisMinimum(mChart.getData().getXMin());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    /**
     * <p>初始化属性信息</p>
     */
    private void initProperties() {
        mChart.setNoDataText("");
        // no description text
        mChart.getDescription().setEnabled(false);
        // enable touch gestures
        mChart.setTouchEnabled(true);
        mChart.setDragDecelerationFrictionCoef(0.9f);
        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleXEnabled(true);
        mChart.setPinchZoom(false);
        mChart.setVisibleXRangeMaximum(6);
        mChart.setScaleYEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(false);
        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);
    }

    /**
     * <p>初始化Legend展示信息</p>
     *
     * @param form           样式
     * @param legendTextSize 文字大小
     * @param legendColor    颜色值
     */
    public void initLegend(Legend.LegendForm form, float legendTextSize, int legendColor) {
        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        // modify the legend ...
        l.setForm(form);
        l.setTextSize(legendTextSize);
        l.setTextColor(legendColor);
        //l.setYOffset(11f);
        updateLegendOrientation(Legend.LegendVerticalAlignment.BOTTOM, Legend.LegendHorizontalAlignment.RIGHT, Legend.LegendOrientation.HORIZONTAL);
    }

    /**
     * <p>图例说明</p>
     *
     * @param vertical    垂直方向位置 默认底部
     * @param horizontal  水平方向位置 默认右边
     * @param orientation 显示方向 默认水平展示
     */

    public void updateLegendOrientation(Legend.LegendVerticalAlignment vertical, Legend.LegendHorizontalAlignment horizontal, Legend.LegendOrientation orientation) {
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(vertical);
        l.setHorizontalAlignment(horizontal);
        l.setOrientation(orientation);
        l.setDrawInside(false);

    }

    /**
     * 图表value显示开关
     */
    public void toggleChartValue() {
        List<BaseDataSet> sets = mChart.getData().getDataSets();
        for (BaseDataSet iSet : sets) {
            iSet.setDrawValues(!iSet.isDrawValuesEnabled());
        }
        mChart.invalidate();
    }

    public void setMarkView(MarkerView markView) {
        markView.setChartView(mChart); // For bounds control
        mChart.setMarker(markView); // Set the marker to the chart
        mChart.invalidate();
    }

    /**
     * x/ylabel显示样式
     *
     * @param xvalueFromatter    x
     * @param leftValueFromatter y
     */
    public void setAxisFormatter(IAxisValueFormatter xvalueFromatter, IAxisValueFormatter leftValueFromatter) {
        mChart.getXAxis().setValueFormatter(xvalueFromatter);
        mChart.getAxisLeft().setValueFormatter(leftValueFromatter);
        mChart.invalidate();

    }

    protected abstract void setChartData();


    /**
     * value显示格式设置
     *
     * @param valueFormatter IValueFormatter
     */
    public void setDataValueFormatter(IValueFormatter valueFormatter) {
        mChart.getData().setValueFormatter(valueFormatter);
    }
}

