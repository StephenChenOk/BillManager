package com.chen.fy.mytext.adapter;
/**
 * 自定义适配器
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.fy.mytext.billsApp.R;
import com.chen.fy.mytext.entity.BillInfo;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    //数据
    private List<BillInfo> list;
    //反射器
    LayoutInflater inflater;

    /**
     * 构造器
     */
    public MyAdapter(Context context){
        //传入上下文以便初始化反射器
        inflater = LayoutInflater.from(context);
    }

    //传入数据
    public void setList(List<BillInfo> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return (list == null)? 0 :list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //声明ViewHolder类,用以存放要显示的控件
        ViewHolder viewHolder = null;
        if(view == null){     //判断缓冲池中是否已经有了View(优化1)
            //用反射把item布局反射为View
            view = inflater.inflate(R.layout.item_adapter,null);
            //创建ViewHolder对象
            viewHolder = new ViewHolder();
            //从view中反射获取相应的控件对象在存在ViewHolder中
            viewHolder.logo = view.findViewById(R.id.logo);
            viewHolder.time = view.findViewById(R.id.time);
            viewHolder.type = view.findViewById(R.id.type);
            viewHolder.number = view.findViewById(R.id.number);
            //把holder对象一起放到view中实现优化(优化2)
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();  //若缓冲池中有了view则可以直接用不需要再反射
        }
        //对上面获取的对象进行赋值
        BillInfo billInfo = list.get(i);    //获取当前行对象
        viewHolder.logo.setImageResource(billInfo.getLogo());
        viewHolder.time.setText(billInfo.getUtility_time());
        viewHolder.type.setText(billInfo.getType());
        viewHolder.number.setText(billInfo.getNumber()+"元");

        return view;
    }

    //创建一个内部类,放着要显示的View控件,通过实例化这个类,把其对象一起放到View
    public class ViewHolder{
        private ImageView logo;
        private TextView time;
        private TextView type;
        private TextView number;
    }
}
