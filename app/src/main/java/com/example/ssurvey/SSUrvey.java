package com.example.ssurvey;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

/** SSUrvey Application ; 액티비티 감시자 역할 */
public class SSUrvey extends Application {

    public SSUrvey() {
        onCreate();
    }

    /** 현재 띄워진 액티비티 */
    private static Activity currentActivity = null;

    public static Activity getCurrentActivity() { return currentActivity; }

    /** 현재 띄워진 액티비티의 클래스 가져오기 */
    public static Class getCurrentActivityClass() {
        if(currentActivity != null)
            return currentActivity.getClass();
        return null;
    }

    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                currentActivity = activity;
            }

            @Override
            public void onActivityStarted(Activity activity) {
                currentActivity = activity;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                currentActivity = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                if (currentActivity == activity)
                    currentActivity = null;
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                if (currentActivity == activity)
                    currentActivity = null;
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) { }
        });
    }
}