package com.stokey.androidunittest;


import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by stokey on 2018/6/17.
 */

public class RxUnitTestTools {
    private static boolean isInitRxTools = false;

    public static void openRxTools() {
        if (isInitRxTools) {
            return;
        }

        isInitRxTools = true;
        RxJavaPlugins.setIoSchedulerHandler(
                scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setComputationSchedulerHandler(
                scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(
                scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                scheduler -> Schedulers.trampoline());
    }

    public static void destroyRxTools() {
        if (isInitRxTools) {
            RxJavaPlugins.reset();
            RxAndroidPlugins.reset();
        }
    }
}
