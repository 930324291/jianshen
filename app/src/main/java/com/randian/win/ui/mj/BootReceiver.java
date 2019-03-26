package com.randian.win.ui.mj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;


import com.randian.win.BuildConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nicai on 2019-3-14.
 */

public class BootReceiver extends BroadcastReceiver {

    private List<String> GetAllPackage(Context paramContext)
    {
        new ArrayList();
        ArrayList localArrayList = new ArrayList();
        Intent localIntent = new Intent("android.intent.action.MAIN", null);
        localIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> list = paramContext.getPackageManager().queryIntentActivities(localIntent, 0);
        for (int i = 0; i < list.size(); i++) {
            localArrayList.add(((ResolveInfo)list.get(i)).activityInfo.packageName);
        }
        return localArrayList;
    }

    public static void uninstallApk(Context paramContext, String paramString)
    {
//        Uri packageURI = Uri.parse("package:com.sudk.dou");
            Intent uninstallIntent = new Intent("android.intent.action.DELETE", Uri.parse("package:" + paramString));
            uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        paramContext.startActivity(uninstallIntent);

//        paramString = new Intent("android.intent.action.DELETE", Uri.parse("package:" + paramString));
//        paramString.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        paramContext.startActivity(paramString);
    }

    public IntentFilter getIntentFilter()
    {
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        localIntentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        localIntentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        localIntentFilter.addDataScheme("package");
        return localIntentFilter;
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
        if (paramIntent.getAction().equals("android.intent.action.PACKAGE_ADDED"))
        {
            String packageName = paramIntent.getData().getSchemeSpecificPart();
//            Log.e("cjl", "------����install app packageName = " + paramIntent);
            if (GetAllPackage(paramContext).contains(BuildConfig.APPLICATION_ID)) {
                uninstallApk(paramContext, BuildConfig.APPLICATION_ID);
            }
        }
    }

    public void register(Context paramContext)
    {
        paramContext.registerReceiver(this, getIntentFilter());
    }

    public void unregister(Context paramContext)
    {
        paramContext.unregisterReceiver(this);
    }
//    @Override
//    public void onReceive(Context context, Intent intent){
//
//        if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_ADDED)) {
//            String packageName = intent.getData().getSchemeSpecificPart();
//            MLogUtil.e("安装了:" +packageName + "包名的程序");
//            Toast.makeText(context, "安装成功" + packageName, Toast.LENGTH_LONG).show();
//
//            Uri packageURI = Uri.parse("package:com.sudk.dou");
//            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
//            uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(uninstallIntent);
//        } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REPLACED)) {
//            String packageName = intent.getData().getSchemeSpecificPart();
//            MLogUtil.e("替换了:"+ packageName);
//            Toast.makeText(context, "替换成功" + packageName, Toast.LENGTH_LONG).show();
//            Uri packageURI = Uri.parse("package:com.sudk.dou");
//            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
//            uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(uninstallIntent);
//        }
//
//    }
}
