package com.example.buletoothdemo.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buletoothdemo.R;
import com.example.buletoothdemo.entity.DatasEntity;

/**
 * Created by 已配置适配器 on 2017/9/1.
 */

public class PairedAdapter extends BaseAdapter {
    private Context context;
    private BluetoothDevice device;//蓝牙设备
    public PairedAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return DatasEntity.mPairedDevices.size();
    }

    @Override
    public BluetoothDevice getItem(int i) {
        return DatasEntity.mPairedDevices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (null == view){
            view = LayoutInflater.from(context).inflate(R.layout.itemlistview,null);
            holderView =new HolderView(view);
            view.setTag(holderView);
        }else{
            holderView = (HolderView) view.getTag();
        }
        device =DatasEntity.mPairedDevices.get(i);
        if(device.getType()==1) {

            holderView.imageView.setImageResource(R.mipmap.home_title_android);
        }else if(device.getType()==2) {


            holderView.imageView.setImageResource(R.mipmap.home_title_qian);
        }else if(device.getType()==3) {


            holderView.imageView.setImageResource(R.mipmap.home_title_ios);
        }else{

                holderView.imageView.setImageResource(R.mipmap.home_title_qian);

        }
        if (device.getName()==null||device.getName().equals("null")){
            holderView.textView.setText(device.getAddress());
        }else{
            holderView.textView.setText(device.getName()+"  "+device.getAddress());
        }
        return view;
    }
    HolderView holderView;
    private class HolderView{
        ImageView imageView;

        TextView textView;

        public HolderView(View view) {
            this.imageView = view.findViewById(R.id.devices_img);
            this.textView = view.findViewById(R.id.address_tv);
        }
    }
}
