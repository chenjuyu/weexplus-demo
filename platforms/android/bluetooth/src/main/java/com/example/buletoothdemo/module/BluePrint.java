package com.example.buletoothdemo.module;


import android.app.Activity;
import android.content.Intent;


import com.example.buletoothdemo.activity.MainActivity;
import com.farwolf.weex.annotation.WeexModule;
import com.farwolf.weex.base.WXModuleBase;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@WeexModule(name="blueprint")
public class BluePrint extends WXModuleBase {

    JSCallback callback;

    @JSMethod(uiThread = true) //直接返回扫描的内容
    public void print(final List<Map<String,Object>> data,final JSCallback callback){

        BluePrint.this.callback =callback;
        dojob(data,callback);


    }

    public  void dojob(final List<Map<String,Object>> data, final JSCallback callback){
        Intent in=new Intent(this.mWXSDKInstance.getContext(), MainActivity.class);
        in.putExtra("data",(Serializable) data);
        System.out.println("data的List值:"+data.toString());

        ((Activity)this.mWXSDKInstance.getContext()).startActivityForResult(in, 2);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode== 2)
        {
            if(resultCode==1)
            {
                String url = data.getStringExtra("url");
                callback.invokeAndKeepAlive(url);
            }
        }

    }



}
