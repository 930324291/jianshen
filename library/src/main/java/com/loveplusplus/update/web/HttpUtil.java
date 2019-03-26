package com.loveplusplus.update.web;

import android.graphics.Bitmap;


import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * Created by nicai on 2016/4/18.
 */
public class HttpUtil {

    /**
     * @param urlParam "login/login",
     * @param obj       new Req_Login(phone, pwd)
     * @param callBack new BaseAjaxCallBack()
     */
    public static void post(String urlParam, Object obj, BaseAjaxCallBack callBack){
//        FastHttp.ajaxByJosnWithCookies(MyApplication.SERVER_URL + urlParam, obj, MyApplication.getApplication().getCookies(), callBack);
    }

    public static void get(String cl, String id ,BaseAjaxCallBack callBack){
        String url = "https://leancloud.cn:443/1.1/classes/"+cl+"/"+id;
        InternetConfig config = new InternetConfig(InternetConfig.request_get);
        HashMap<String, Object> head = new HashMap<>();
        head.put("X-Avoscloud-Application-Id","yWlHGW3PAXhQDtKJISIE6IGM-gzGzoHsz");
        head.put("X-Avoscloud-Application-Key","LnfRUPES8ynDpSPWGbOt6Pxu");
        config.setHead(head);
        FastHttp.ajaxGet(url, config, callBack);
    }



}
