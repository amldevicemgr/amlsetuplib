package com.amltd.amlsetupsdk.models;

public class SdkError {
    String PackageName;
    int Type;
    String Reason;

    /**
     * Gets the package name that is using the SDK.
     * @return The package name.
     */
    public String getPackageName() {
        return PackageName;
    }

    void setPackageName(String packageName) {
        PackageName = packageName;
    }

    /**
     * Gets the type of SDK command that caused an error.
     * @return The SDK command integer.
     */
    public int getType() {
        return Type;
    }

    void setType(int type) {
        Type = type;
    }

    /**
     * Gets the reason for the SDK command error.
     * @return The reason for the error.
     */
    public String getReason() {
        return Reason;
    }

    void setReason(String reason) {
        Reason = reason;
    }
}
