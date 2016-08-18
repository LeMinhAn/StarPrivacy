package com.cleanwiz.applock.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by Jimmy on 8/16/2016.
 */
public class ForegroundDetectionService extends AccessibilityService{
    private static final String TAG = "ForegroundDetectionService";

    public static String packageName;
    public static long time;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if(accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            startService(new Intent(getBaseContext(), LockService.class));
            packageName = accessibilityEvent.getPackageName().toString();
            time = System.currentTimeMillis();
        }
    }

    @Override
    public void onInterrupt() {
    }
}
