package com.amltd.amlsetupsdk.models;

public class DeviceInfo {
    String SerialNumber;
    String Model;
    String Name;
    String Build;
    CurrentNetwork ActiveNetwork;
    BatteryStatus Battery;
    long AvailableStorage;
    String DeviceUpTime;
    String IpAddress;
    String WifiMacAddress;
    String EthernetMacAddress;
    String BtMacAddress;

    /**
     * Gets the device serial number.
     * @return The device serial number.
     */
    public String getSerialNumber() {
        return SerialNumber;
    }

    void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    /**
     * Gets the device model.
     * @return The device model.
     */
    public String getModel() {
        return Model;
    }

    void setModel(String model) {
        Model = model;
    }

    /**
     * Gets the device name.
     * @return The device name.
     */
    public String getName() {
        return Name;
    }

    void setName(String name) {
        Name = name;
    }

    /**
     * Gets the device firmware build.
     * @return The device firmware build name.
     */
    public String getBuild() {
        return Build;
    }

    void setBuild(String build) {
        Build = build;
    }

    /**
     * Gets the device's currently active network.
     * @return The device's active network info, or null if no network.
     */
    public CurrentNetwork getActiveNetwork() {
        return ActiveNetwork;
    }

    void setActiveNetwork(CurrentNetwork activeNetwork) {
        ActiveNetwork = activeNetwork;
    }

    /**
     * Gets the device's battery info.
     * @return The device's battery info.
     */
    public BatteryStatus getBattery() {
        return Battery;
    }

    void setBattery(BatteryStatus battery) {
        Battery = battery;
    }

    /**
     * Gets the device's available storage.
     * @return The bytes of available storage on the device.
     */
    public long getAvailableStorage() {
        return AvailableStorage;
    }

    void setAvailableStorage(long availableStorage) {
        AvailableStorage = availableStorage;
    }

    /**
     * Gets the device's total up-time.
     * @return The total time the device has been on.
     */
    public String getDeviceUpTime() {
        return DeviceUpTime;
    }

    void setDeviceUpTime(String deviceUpTime) {
        DeviceUpTime = deviceUpTime;
    }

    /**
     * Gets the device's ip address.
     * @return The ip address of the device.
     */
    public String getIpAddress() {
        return IpAddress;
    }

    void setIpAddress(String ipAddress) {
        IpAddress = ipAddress;
    }

    /**
     * Gets the device's Wi-Fi Mac Address.
     * @return The device's Wi-Fi Mac Address.
     */
    public String getWifiMacAddress() {
        return WifiMacAddress;
    }

    void setWifiMacAddress(String wifiMacAddress) {
        WifiMacAddress = wifiMacAddress;
    }

    /**
     * Gets the device's Ethernet Mac Address.
     * @return The device's Ethernet Mac Address, or null if device is not running on an Ethernet network.
     */
    public String getEthernetMacAddress() {
        return EthernetMacAddress;
    }

    void setEthernetMacAddress(String ethernetMacAddress) {
        EthernetMacAddress = ethernetMacAddress;
    }

    /**
     * Gets the device's Bluetooth Mac Address.
     * @return The device's Bluetooth Mac Address.
     */
    public String getBtMacAddress() {
        return BtMacAddress;
    }

    void setBtMacAddress(String btMacAddress) {
        BtMacAddress = btMacAddress;
    }
}
