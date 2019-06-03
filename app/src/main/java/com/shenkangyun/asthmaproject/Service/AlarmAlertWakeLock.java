package com.shenkangyun.asthmaproject.Service;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

/**
 * Created by YANG on 15/10/21.
 */

/**
 * Hold a wakelock that can be acquired in the AlarmReceiver and
 * released in the AlarmAlert activity
 */
public class AlarmAlertWakeLock {

    private static PowerManager.WakeLock sCpuWakeLock;

    static void acquireCpuWakeLock(Context context) {
        Log.d("hu", "Acquiring cpu wake lock");
        if (sCpuWakeLock != null) {
            return;
        }

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        sCpuWakeLock = pm.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK |
                        PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.ON_AFTER_RELEASE, "hu");
        sCpuWakeLock.acquire();
    }

    public static void releaseCpuLock() {
        Log.d("hu", "Releasing cpu wake lock");
        if (sCpuWakeLock != null) {
            sCpuWakeLock.release();
            sCpuWakeLock = null;
        }
    }
}
