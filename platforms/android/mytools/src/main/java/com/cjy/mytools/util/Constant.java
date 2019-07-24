package com.cjy.mytools.util;

public class Constant {

    public final static int UNKNOW_ERROE = -3;
    public final static int IP_ERROE = -2;
    public final static int FAILED = -1;
    public final static int SUCCESS = 1;
    public final static int NET_FAILED = 2;
    public final static int TIME_OUT = 3;

    
    // request参数
    public static final int REQ_QR_CODE = 11002; // // 打开扫描界面请求码
    public static final int REQ_PERM_CAMERA = 11003; // 打开摄像头
    public static final int REQ_PERM_EXTERNAL_STORAGE = 11004; // 读写文件

    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";
    
}
