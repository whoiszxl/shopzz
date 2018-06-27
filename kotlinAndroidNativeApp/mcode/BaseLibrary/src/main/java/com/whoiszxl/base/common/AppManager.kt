package com.whoiszxl.base.common

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

class AppManager private constructor() {

    private val activityStack: Stack<Activity> = Stack()

    companion object {
        val instance: AppManager by lazy { AppManager() }
    }

    /**
     * 添加一个activity到自定义栈中
     */
    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    /**
     * 从自定义栈中移除一个activity
     */
    fun finishActivity(activity: Activity) {
        activity.finish()
        activityStack.remove(activity)
    }

    /**
     * 获取栈顶的activity
     */
    fun currentActivity() : Activity {
        return activityStack.lastElement()
    }


    /**
     * 清理自定义栈中所有activity
     */
    fun finishAllActivity() {
        for (activity in activityStack){
            activity.finish()
        }
        activityStack.clear()
    }

    /**
     * 退出app
     */
    fun exitApp(context: Context) {
        finishAllActivity()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.killBackgroundProcesses(context.packageName)
        System.exit(0)
    }
}