package com.amltd.amlsetuplibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.amltd.amlsetupsdk.AMLSetupClient;
import com.amltd.amlsetupsdk.OnConnectedListener;
import com.amltd.amlsetupsdk.OnErrorListener;
import com.amltd.amlsetupsdk.OnReceiveConfigResult;
import com.amltd.amlsetupsdk.OnReceiveDeviceInfo;
import com.amltd.amlsetupsdk.helpers.AMLDevice;
import com.amltd.amlsetupsdk.models.CurrentNetwork;
import com.amltd.amlsetupsdk.models.DeviceInfo;
import com.amltd.amlsetupsdk.models.ProcessConfigResult;
import com.amltd.amlsetupsdk.models.SdkError;

public class MainActivity extends AppCompatActivity {

    AMLSetupClient amlSetupClient;
    String ENABLE_WIFI_CONFIG_STRING = "/%@%/{\"W0\":\"1\",\"CC\":\"CONFIGDONE\"}";
    boolean _connected = false;
    DeviceInfo _deviceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void rebootDevice()
    {
        if (amlSetupClient != null && _connected)
            amlSetupClient.rebootDevice();
    }

    public void doOTA()
    {
        if (amlSetupClient != null && _connected)
            amlSetupClient.forceOTA();
    }
}