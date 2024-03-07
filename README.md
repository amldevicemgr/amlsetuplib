# AML Setup Library

## Overview

AML Setup Library provides an easy way to interface with an AML device to configure its settings and get hardware info. 
The library allows you to create an instance of AMLSetupClient. 
You can configure the device settings with a AML Setup configuration json string.
This library works for native Android implementations.

## Usage

Add jitpack.io reference to build.gradle repositories section.
```java
maven { url 'https://jitpack.io' }
```
Add reference to project to the build.gradle dependencies. Replace VERSION with the version tag.
```java
implementation 'com.github.amldevicemgr:amlsetuplib:VERSION'
```

The library contains a class called `AMLSetupClient` that is used to interface with the device:

```java
AMLSetupClient client = new AMLSetupClient(this);
```

The parameter for the constructor is the `Context` of the Android application.

## Example

```java
public AMLSetupClient client;
String ENABLE_WIFI_CONFIG_STRING = "/%@%/{\"W0\":\"1\",\"CC\":\"CONFIGDONE\"}";
boolean _connected = false;
DeviceInfo _deviceInfo;

public initAMLClient()
{
    if (AMLDevice.isAMLSetupClientSupported(this)) {
      amlSetupClient = new AMLSetupClient(this);
      amlSetupClient.setOnConnectedListener(new OnConnectedListener() {
        @Override
        public void onConnected(boolean permissionGranted) {
          if (permissionGranted)
            _connected = true;

          amlSetupClient.getDeviceInfo(new OnReceiveDeviceInfo() {
            @Override
            public void onReceive(DeviceInfo info) {
              _deviceInfo = info;
            }
          });

          //Send test config string to enable Wi-Fi
          sendConfig(ENABLE_WIFI_CONFIG_STRING);
        }
      });
      amlSetupClient.setOnErrorListener(new OnErrorListener() {
        @Override
        public void onError(SdkError error) {
          //process error
        }
      });
   }
}

    public void sendConfig(String config)
    {
        if (amlSetupClient != null && _connected)
        {
            amlSetupClient.sendConfigJson(new OnReceiveConfigResult() {
                @Override
                public void onReceive(ProcessConfigResult result) {
                    //process config result
                }
            }, config);
        }
    }
```

