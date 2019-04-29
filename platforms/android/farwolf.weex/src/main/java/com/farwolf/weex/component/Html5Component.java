package com.farwolf.weex.component;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

import java.util.HashMap;

public class Html5Component extends WXComponent<WebView> {
    //我们操作的webView
    private WebView webView;
    //持有组建的当前weex实例
    private WXSDKInstance instance;
    //要加载的HTML文件，此文件放在assets下的html文件夹下
    private String url; //file:///android_asset/html/index.html
    //浏览器加载界面是否完成
    private boolean  mLoadFinish=false;
    //构造方法，获得当前weex实例instance
   /* public Html5Component(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
        this.instance = instance;
    public Html5Component(WXSDKInstance instance, WXDomObject dom, WXVContainer parent, int type) {
        super(instance, dom, parent, type);
        this.instance = instance;
    } *///此方法在新版本已除

    public Html5Component(WXSDKInstance instance, WXVContainer parent,  BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);

    }



    //初始化webView设置一些参数
    @SuppressLint("SetJavaScriptEnabled")
    private void init(){
        webView.setInitialScale(100);
        webView.addJavascriptInterface(this,"native");
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
//        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
//        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); //关闭webview中缓存 LOAD_CACHE_ELSE_NETWORK
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片

        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
      //  settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);


        webView.loadUrl(url);
        //复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        webView.setWebViewClient(new WebViewClient(){
        /*    @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }    */


            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageFinished(WebView view, String url) {
                mLoadFinish=true;
                //这里出发webView组件的finish事件，表示网页已经加载完成
                fireEvent("finish");
            }
        });
    }
    //父类的方法，重写这个方法获取path属性的值，这个是我们要加载的html界面
   // 由于 WXDomObject 下沉至 WeexCore 中，所以 getDomObject() 方法已被删除。


    @Override
    protected WebView initComponentHostView(@NonNull Context context) {
      /*  if(getDomObject().getAttrs().get("path")!=null){
            this.url="file:///android_asset/html/"+getDomObject().getAttrs().get("path").toString();
        }
      this.url="初始化url"; */

        //新建webView
        this.webView = new WebView(context);
        init();
        return this.webView;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
    }
    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
    }
    /*要加载的html的文件路径*/
    @WXComponentProp(name = "path")
    public void setPath(String path) {
        this.url = path;
        webView.loadUrl(url);
      /*  if (path.indexOf("http")>-1 ||path.indexOf("https")>-1 ) {
            this.url = path;
        }else {
            this.url = "file:///android_asset/html/" + path;
        } */
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onFinishLayout() {
        super.onFinishLayout();
    }
    //为html提供的通用的触发webView在weex中的对应方法名的事件
    @JavascriptInterface
    public void fireWeexEvent(final String weexMethod, String param){
        String data="";
        if(param!=null){
            data=param;
        }
        final HashMap map=new HashMap();
        map.put("data",data);
        this.webView.post(new Runnable() {
            @Override
            public void run() {
                fireEvent(weexMethod,map);
            }
        });
    }
    /*为weex界面提供的调用h5中js方法的接口*/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @JSMethod
    public void JavatoHtml(String functionName, String param, final JSCallback callback){
        System.out.println("已经执行到这个方法了！！");
     if (!mLoadFinish){
            return;
        }
        if (!TextUtils.isEmpty(functionName)) {
            System.out.println("weex页面的点击："+param);


           webView.evaluateJavascript("javascript:"+functionName+"("+param+")", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                    if(callback!=null){
                        HashMap map=new HashMap();
                        map.put("res",value);
                        System.out.println("返回到weex页面的结果："+value);
                        callback.invoke(map);
                    }
                }
            });
        }
    }
}