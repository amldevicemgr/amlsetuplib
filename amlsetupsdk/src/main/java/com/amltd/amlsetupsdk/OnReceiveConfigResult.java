package com.amltd.amlsetupsdk;

import com.amltd.amlsetupsdk.models.DeviceInfo;
import com.amltd.amlsetupsdk.models.ProcessConfigResult;

public interface OnReceiveConfigResult {
    void onReceive(ProcessConfigResult result);
}
