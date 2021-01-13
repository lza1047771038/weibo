package com.open.weibo.base

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.open.core_base.service.ServiceFacade
import com.open.weibo.utils.AppStartUtils
import com.open.weibo.utils.LoginUtils
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AuthInfo

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        WbSdk.install(
            this,
            AuthInfo(this, LoginUtils.APP_KEY, LoginUtils.REDIRECT_URL, LoginUtils.SCOPE)
        )
        AppStartUtils.initWithOutPermission()
        Fresco.initialize(this)
        LoginUtils.getInstance().initSdk()
    }
}