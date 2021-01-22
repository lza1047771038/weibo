package com.open.weibo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;

import com.open.core_base.impl.ContextResolver;
import com.open.core_base.interfaces.IContext;
import com.open.core_base.service.ServiceFacade;
import com.open.core_image.impl.ImageImpl;
import com.open.core_image_interface.interfaces.IImage;
import com.open.core_network.utils.NetworkStatusUtils;
import com.open.core_theme.impl.ColorThemeWrapper;
import com.open.core_theme_interface.theme.IColorTheme;

import java.util.List;

public class AppStartUtils {

    public static void initWithOutPermission() {
        ServiceFacade.init();
        ServiceFacade.getInstance().put(IImage.class, new ImageImpl());
        ServiceFacade.getInstance().put(IContext.class, new ContextResolver());
        ServiceFacade.getInstance().put(IColorTheme.class, new ColorThemeWrapper());

        ProfileUtils.getInstance().init();
        NetworkStatusUtils.getInstance().registerNetworkCallback(NetworkListener.getInstance());
    }

    public static void stopApplication(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        int pid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : appProcessInfos) {
            if (pid != info.pid) {
                android.os.Process.killProcess(info.pid);
            }
        }
        android.os.Process.killProcess(pid);
        System.exit(0);
    }
}
