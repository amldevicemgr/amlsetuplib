package com.amltd.amlsetupsdk.models;

public class BatteryStatus {
    boolean IsCharging;
    int BatteryPercent;

    /**
     * Gets the battery charging status.
     * @return True if the battery is charging, otherwise false.
     */
    public boolean isCharging() {
        return IsCharging;
    }

    void setCharging(boolean charging) {
        IsCharging = charging;
    }

    /**
     * Gets the battery charge percentage.
     * @return The battery percentage.
     */
    public int getBatteryPercent() {
        return BatteryPercent;
    }

    void setBatteryPercent(int batteryPercent) {
        BatteryPercent = batteryPercent;
    }
}

