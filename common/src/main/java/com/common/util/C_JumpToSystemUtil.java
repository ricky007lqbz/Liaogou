package com.common.util;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by ricky on 2016/10/26.
 * <p>
 * 跳转到系统指定界面工具类
 */

public class C_JumpToSystemUtil {

    /**
     * 跳转到app权限设置界面
     */
    public static void toAppPermissionSetting(Context context) {
        if (C_CheckPhoneSystemUtils.isEMUI()) {
            toHuawei(context);
        } else if (C_CheckPhoneSystemUtils.isFlyme() || C_CheckPhoneSystemUtils.isMIUI()) {
            toMiUIorFlyme(context);
        } else {
            toSystem(context);
        }
    }

    /**
     * 跳转Android原生系统设置界面
     */
    private static void toSystem(Context context) {
        String SCHEME = "package";
        //调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.1及之前版本)
        final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
        //调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.2)
        final String APP_PKG_NAME_22 = "pkg";
        //InstalledAppDetails所在包名
        final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
        //InstalledAppDetails类名
        final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";

        Intent intent = new Intent();
        final int apiLevel = Build.VERSION.SDK_INT;
        if (apiLevel >= 9) { // 2.3（ApiLevel 9）以上，使用SDK提供的接口
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts(SCHEME, context.getPackageName(), null);
            intent.setData(uri);
        } else { // 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）
            // 2.2和2.1中，InstalledAppDetails使用的APP_PKG_NAME不同。
            final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22
                    : APP_PKG_NAME_21);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName(APP_DETAILS_PACKAGE_NAME,
                    APP_DETAILS_CLASS_NAME);
            intent.putExtra(appPkgName, context.getPackageName());
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 跳转小米魅族手机权限设置界面
     */
    private static void toMiUIorFlyme(Context context) {
        Intent intent = new Intent();
        if (C_CheckPhoneSystemUtils.isMIUI()) {
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", context.getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                C_ToastUtil.show("只有MIUI才可以设置哦");
            }
        } else if (C_CheckPhoneSystemUtils.isFlyme()) {
            intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", context.getPackageName());
            try {
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                C_ToastUtil.show("只有Flyme才可以设置哦");
            }
        } else {
            //三星、索尼可以
            toSystem(context);
        }
    }

    /**
     * 跳转华为权限管理设置界面
     */
    private static void toHuawei(Context context) {
        try {
            //HUAWEI H60-l02 P8max测试通过
            C_L.d("进入指定app悬浮窗管理页面失败，自动进入所有app悬浮窗管理页面");
            Intent intent = new Intent("com.example.activity");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //   ComponentName comp = new ComponentName("com.huawei.systemmanager","com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            //   ComponentName comp = new ComponentName("com.huawei.systemmanager",
            // "com.huawei.permissionmanager.ui.SingleAppActivity");//华为权限管理，跳转到指定app的权限管理位置需要华为接口权限，未解决

            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity");//悬浮窗管理页面
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (SecurityException e) {
            Intent intent = new Intent("com.example.activity");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //   ComponentName comp = new ComponentName("com.huawei.systemmanager","com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            ComponentName comp = new ComponentName("com.huawei.systemmanager",
                    "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理，跳转到本app的权限管理页面,这个需要华为接口权限，未解决
            // ComponentName comp = new ComponentName("com.huawei.systemmanager","com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity");//悬浮窗管理页面

            intent.setComponent(comp);
            context.startActivity(intent);
            C_L.d("正在进入指定app悬浮窗开启位置..");
        } catch (ActivityNotFoundException e) {
            /**
             * 手机管家版本较低 HUAWEI SC-UL10
             */
            //   Toast.makeText(MainActivity.this, "act找不到", Toast.LENGTH_LONG).show();
            Intent intent = new Intent("com.example.activity");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.Android.settings", "com.android.settings.permission.TabItem");//权限管理页面 android4.4
            //   ComponentName comp = new ComponentName("com.android.settings","com.android.settings.permission.single_app_activity");//此处可跳转到指定app对应的权限管理页面，但是需要相关权限，未解决
            intent.setComponent(comp);
            context.startActivity(intent);
            e.printStackTrace();
        } catch (Exception e) {
            //抛出异常时提示信息
            C_ToastUtil.show("进入设置页面失败，请手动设置");
        }
    }
}
