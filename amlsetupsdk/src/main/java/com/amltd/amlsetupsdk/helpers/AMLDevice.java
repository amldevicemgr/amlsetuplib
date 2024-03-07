package com.amltd.amlsetupsdk.helpers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;

public class AMLDevice {
    /**
     * Checks whether the device is an AML device.
     * @return True if the device is AML, otherwise false.
     */
    public static boolean isAMLDevice()
    {
        String model = Build.MODEL;
        return model.equals("M7700") || model.equals("M7800") || model.equals("M6500") || model.equals("KDT7") || model.equals("M7800 BATCH");
    }

    /**
     * Checks whether the device supports AMLSetupClient.
     * @return True if the device is supports AMLSetupClient, otherwise false.
     */
    public static boolean isAMLSetupClientSupported(Context context)
    {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.amltd.amlsetup", 0);
            return packageInfo.versionCode >= 103 && isAMLDevice();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks the device AMLSetupClient version.
     * @return The AMLSetupClient version or 0 if AML Setup is not found on the device.
     */
    public static int getAMLSetupClientVersion(Context context)
    {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.amltd.amlsetup", 0);
            return packageInfo.versionCode;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
}
