package com.amltd.amlsetupsdk.models;

public class CurrentNetwork {
    String SSID;
    String RSSI;
    String BSSID;
    boolean Connected;
    String LinkSpeed;

    /**
     * Gets the network link speed.
     * @return The networks link speed.
     */
    public String getLinkSpeed() {
        return LinkSpeed;
    }

    void setLinkSpeed(String linkSpeed) {
        LinkSpeed = linkSpeed;
    }

    /**
     * Gets the network connection status.
     * @return True if the network is connected, otherwise false.
     */
    public boolean isConnected() {
        return Connected;
    }

    void setConnected(boolean connected) {
        Connected = connected;
    }

    /**
     * Gets the network SSID.
     * @return The networks SSID.
     */
    public String getSSID() {
        return SSID;
    }

    void setSSID(String SSID) {
        this.SSID = SSID;
    }

    /**
     * Gets the network RSSI.
     * @return The networks RSSI.
     */
    public String getRSSI() {
        return RSSI;
    }

    void setRSSI(String RSSI) {
        this.RSSI = RSSI;
    }

    /**
     * Gets the network BSSID.
     * @return The networks BSSID.
     */
    public String getBSSID() {
        return BSSID;
    }

    void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }
}
