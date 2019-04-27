package com.farwolf.weex.module;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

import java.util.Map;

public class TextModule extends WXModule {

    JSCallback callbak;

    @JSMethod
    public void testpint(Map<String,Object> m, JSCallback callbak){
        this.callbak=callbak;

        System.out.println("测试的内容"+String.valueOf(m.get("str")));

        this.callbak.invokeAndKeepAlive("测试返回到js端的内容");



    }

}
