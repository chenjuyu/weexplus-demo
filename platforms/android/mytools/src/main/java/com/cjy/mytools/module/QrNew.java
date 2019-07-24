package com.cjy.mytools.module;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;

import com.cjy.mytools.google.zxing.CaptureActivity;
import com.cjy.mytools.util.Constant;
import com.farwolf.perssion.Perssion;
import com.farwolf.perssion.PerssionCallback;
import com.farwolf.weex.annotation.WeexModule;
import com.farwolf.weex.base.WXModuleBase;
import com.farwolf.weex.event.PermissionEvent;
import com.farwolf.weex.module.WXQRModule;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@WeexModule(name="qrnew")
public class QrNew extends WXModuleBase{

    JSCallback callback;

    @JSMethod //直接返回扫描的内容
    public void open(final JSCallback callback){
  /*
        Intent in=new Intent(this.mWXSDKInstance.getContext(), CaptureActivity.class);
        System.out.println("当前的activity:"+this.mWXSDKInstance.getContext());
        QrNew.this.callback =callback;
        ((Activity)this.mWXSDKInstance.getContext()).startActivityForResult(in, Constant.REQ_QR_CODE);
 */

        Perssion.check((Activity) mWXSDKInstance.getContext(), Manifest.permission.CAMERA,new PerssionCallback(){


            @Override
            public void onGranted() {


                Perssion.check((Activity) mWXSDKInstance.getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE , new PerssionCallback() {
                    @Override
                    public void onGranted() {
                       // WXQRModule.this.param=param;
                        QrNew.this.callback=callback;
                        if(!EventBus.getDefault().isRegistered(QrNew.this))
                        {
                            EventBus.getDefault().register(QrNew.this);
                        }

                        dojob(callback);

                    }
                });



            }
        });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(PermissionEvent event) {

        if(event.type==PermissionEvent.CAMREA)
        {
            dojob(callback);
        }

    }
    void dojob( JSCallback callback){
        Intent in=new Intent(this.mWXSDKInstance.getContext(), CaptureActivity.class);
        System.out.println("当前的activity:"+this.mWXSDKInstance.getContext());
        ((Activity)this.mWXSDKInstance.getContext()).startActivityForResult(in, Constant.REQ_QR_CODE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode== Constant.REQ_QR_CODE)
        {
            if(resultCode==1)
            {
                String url = data.getStringExtra("url");
                callback.invokeAndKeepAlive(url);
            }
        }

    }

}
