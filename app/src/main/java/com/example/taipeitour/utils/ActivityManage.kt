package com.example.taipeitour.utils

import android.app.Activity
import java.lang.ref.WeakReference
import java.util.*
import kotlin.system.exitProcess

/**
 * Author: FlyWei
 * E-mail: tony91097@gmail.com
 * Date: 2023/2/9
 */
object ActivityManage {

    private val STACK = ActivityStack()
    private lateinit var topActivity: WeakReference<Activity>
    private lateinit var operations: ExtraOperations

    /**
     * 取得當前Activity
     */
    fun peek(): Activity? {
        return if (null != topActivity.get()) {
            topActivity.get()
        } else {
            STACK.peekFromStack()
        }
    }

    /**
     * 關閉當前Activity
     */
    fun pop() {
        STACK.popFromStack()?.finish()
    }

    /**
     * 關閉直到給定的Activity
     */
    fun <T : Activity?> popUntil(clazz: Class<T>?): T? {
        if (clazz != null) {
            while (!STACK.isEmpty()) {
                val activity = STACK.popFromStack()
                if (activity != null) {
                    if (clazz.name == activity.javaClass.name) {
                        return activity as T
                    }
                    activity.finish()
                }
            }
        }
        return null
    }

    /**
     * 關閉後的清理狀態
     */
    private const val MAX_DOUBLE_EXIT_MILLS = 1000L
    private var lastExitPressedMills = 0L

    interface ExtraOperations {
        fun onExit()
        fun onActivityFinish(activity: Activity)
    }

    fun setOperations(operations: ExtraOperations) {
        this.operations = operations
    }

    fun onExit() {
        val now = System.currentTimeMillis()
        if (now <= lastExitPressedMills + MAX_DOUBLE_EXIT_MILLS) {
            finishAll()
            operations.onExit()
            exitProcess(0)
        } else {
            lastExitPressedMills = now
        }
    }

    /**
     * 關閉全部Activity
     */
    private fun finishAll() {
        while (!STACK.isEmpty()) {
            val activity = STACK.popFromStack()
            activity?.apply {
                finish()
                operations.onActivityFinish(this)
            }
        }
    }

    fun setTopActivity(topActivity: Activity) {
        this.topActivity = WeakReference(topActivity)
    }

    fun push(activity: Activity) {
        STACK.pushToStack(activity)
    }

    fun remove(activity: Activity) {
        STACK.removeFromStack(activity)
    }

    private class ActivityStack {
        private val activityStack = Stack<WeakReference<Activity>>()

        fun isEmpty() = activityStack.isEmpty()

        fun pushToStack(activity: Activity) {
            activityStack.push(WeakReference(activity))
        }

        fun popFromStack(): Activity? {
            while (!activityStack.isEmpty()) {
                val weak = activityStack.pop()
                val activity = weak.get()
                if (activity != null) {
                    return activity
                }
            }
            return null
        }

        fun peekFromStack(): Activity? {
            while (!activityStack.isEmpty()) {
                val weak = activityStack.peek()
                val activity = weak.get()
                if (activity != null) {
                    return activity
                } else {
                    activityStack.pop()
                }
            }
            return null
        }

        fun removeFromStack(activity: Activity) {
            for (weak in activityStack) {
                val act = weak.get()
                if (act === activity) {
                    activityStack.remove(weak)
                    return
                }
            }
        }
    }
}