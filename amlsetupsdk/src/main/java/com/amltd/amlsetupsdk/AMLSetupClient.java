package com.amltd.amlsetupsdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.ResultReceiver;
import android.util.Log;

import com.amltd.amlsetupsdk.helpers.AMLDevice;
import com.amltd.amlsetupsdk.models.DeviceInfo;
import com.amltd.amlsetupsdk.models.ProcessConfigResult;
import com.amltd.amlsetupsdk.models.SdkError;
import com.google.gson.Gson;

public class AMLSetupClient {
    //region Public Methods

    /**
     * Constructs an AMLSetupClient instance to configure the device and receive device info.
     * @param context The context of the application.
     */
    public AMLSetupClient(Context context) {
        mContext = context;
        mPackage = context.getPackageName();
        mFilter = mPackage + Values.SDK_RESULT_SEND;
        mClientVersion = AMLDevice.getAMLSetupClientVersion(mContext);

        IntentFilter filter = new IntentFilter();
        filter.addAction(mFilter);

        //This broadcast receiver will handle incoming intents from aml setup service.
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action != null)
                {
                    if (action.equals(mFilter))
                    {
                        String errorString = intent.getStringExtra(Values.SDK_ERROR_EXTRA);
                        SdkError error = new Gson().fromJson(errorString, SdkError.class);
                        if (mOnErrorListener != null)
                            mOnErrorListener.onError(error);
                    }
                }
            }
        };

        //Register the broadcast receiver.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mContext.registerReceiver(mReceiver, filter, Context.RECEIVER_EXPORTED);
        }
        else
        {
            mContext.registerReceiver(mReceiver, filter);
        }

        initClient();
    }

    /**
     * Closes the AMLSetupClient.
     */
    public void close() {
        if (mReceiver != null) {
            BroadcastReceiver br = mReceiver;
            mReceiver = null;
            //Make sure to unregister when we no longer needed.
            mContext.unregisterReceiver(br);
        }
    }

    /**
     * Sets the OnErrorListener to receive service errors.
     * @param listener The OnErrorListener to listen for service errors.
     */
    public void setOnErrorListener(OnErrorListener listener) {
        mOnErrorListener = listener;
    }

    /**
     * Sets the OnConnectedListener to receive when the client connects and returns the permission info.
     * @param listener The OnConnectedListener to listen for connection.
     */
    public void setOnConnectedListener(OnConnectedListener listener) {
        mOnConnectedListener = listener;
    }

    /**
     * Request the device info asynchronously.  If permission is denied,
     * this operation will fail and a SDKError will be sent to the error listener.
     * @param receiver The OnReceiveDeviceInfo listener to receive the device info.
     */
    public void getDeviceInfo(OnReceiveDeviceInfo receiver)  {
        try {
            Intent intent = new Intent(Values.SDK_GET_DEVICE_INFO);
            Bundle bundle = new Bundle();
            ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    String deviceInfoString = resultData.getString(Values.SDK_EXTRA_DEVICE_INFO);
                    DeviceInfo info = new Gson().fromJson(deviceInfoString, DeviceInfo.class);
                    receiver.onReceive(info);
                }
            };
            Parcel p = Parcel.obtain();
            resultReceiver.writeToParcel(p, 0);
            p.setDataPosition(0);
            resultReceiver = ResultReceiver.CREATOR.createFromParcel(p);
            p.recycle();
            bundle.putParcelable(Intent.EXTRA_RESULT_RECEIVER, resultReceiver);
            bundle.putString(Values.SDK_SENDER_PACKAGE_EXTRA, mPackage);
            intent.putExtras(bundle);

            startService(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Reboots the device immediately. If permission is denied,
     * this operation will fail and a SDKError will be sent to the error listener.
     */
    public void rebootDevice() {
        Intent intent = new Intent(Values.SDK_REBOOT);
        Bundle bundle = new Bundle();
        bundle.putString(Values.SDK_SENDER_PACKAGE_EXTRA, mPackage);
        intent.putExtras(bundle);

        startService(intent);
    }

    /**
     * Forces the device to preform an OTA. If the battery is below 30 percent or permission is denied,
     * this operation will fail and a SDKError will be sent to the error listener.
     */
    public void forceOTA() {
        Intent intent = new Intent(Values.SDK_FORCE_OTA);
        Bundle bundle = new Bundle();
        bundle.putString(Values.SDK_SENDER_PACKAGE_EXTRA, mPackage);
        intent.putExtras(bundle);

        startService(intent);
    }

    /**
     * Sends a aml setup config string to be processed.  If permission is denied,
     * this operation will fail and a SDKError will be sent to the error listener.
     * Use the AML Setup Console application to generate the aml setup config string.
     * @param receiver The OnReceiveConfigResult listener to receive the result from the config processing.
     * @param configJson The aml setup config string generated with AML Setup Console.
     */
    public void sendConfigJson(OnReceiveConfigResult receiver, String configJson) {
        try {
            Intent intent = new Intent(Values.SDK_PROCESS_CONFIG_JSON);
            Bundle bundle = new Bundle();
            if (receiver != null)
            {
                ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
                    @Override
                    protected void onReceiveResult(int resultCode, Bundle resultData) {
                        String resultString = resultData.getString(Values.SDK_EXTRA_CONFIG_JSON_RESULT);
                        ProcessConfigResult result = new Gson().fromJson(resultString, ProcessConfigResult.class);
                        receiver.onReceive(result);
                    }
                };
                Parcel p = Parcel.obtain();
                resultReceiver.writeToParcel(p, 0);
                p.setDataPosition(0);
                resultReceiver = ResultReceiver.CREATOR.createFromParcel(p);
                p.recycle();
                bundle.putParcelable(Intent.EXTRA_RESULT_RECEIVER, resultReceiver);
            }

            bundle.putString(Values.SDK_SENDER_PACKAGE_EXTRA, mPackage);
            bundle.putString(Values.SDK_TASK_EXTRA, configJson);
            intent.putExtras(bundle);

            startService(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //endregion

    //region Private Methods

    //Application context
    private final Context mContext;

    //Package Permission to use AMLSetupClient
    private boolean mPermission = false;

    //Package name
    private String mPackage;

    //Broadcast filter
    private String mFilter;

    //Client version
    private int mClientVersion;

    //For receiving broadcast intents
    private BroadcastReceiver mReceiver;

    //For incoming error action
    private OnErrorListener mOnErrorListener;

    //For incoming connected action
    private OnConnectedListener mOnConnectedListener;

    /**
     * Inits the AML Setup Service and checks for permissions.
     */
    private void initClient() {
        Intent intent = new Intent(Values.SDK_INIT);
        Bundle bundle = new Bundle();
        ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                boolean hasPermission = resultData.getBoolean(Values.SDK_PERMISSION_RESULT_EXTRA);
                mPermission = hasPermission;
                if (mOnConnectedListener != null)
                    mOnConnectedListener.onConnected(mPermission);
            }
        };
        Parcel p = Parcel.obtain();
        resultReceiver.writeToParcel(p, 0);
        p.setDataPosition(0);
        resultReceiver = ResultReceiver.CREATOR.createFromParcel(p);
        p.recycle();
        bundle.putParcelable(Intent.EXTRA_RESULT_RECEIVER, resultReceiver);
        bundle.putString(Values.SDK_SENDER_PACKAGE_EXTRA, mPackage);
        intent.putExtras(bundle);

        startService(intent);
    }

    /**
     * Starts AML Setup Service with a intent.
     * @param intent The intent to send to the service.
     */
    private void startService(Intent intent) {
        if (intent == null) intent = new Intent();
        intent.setClassName(Values.PACKAGE_NAME, Values.CLASS_NAME);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mContext.startForegroundService(intent);
        } else {
            mContext.startService(intent);
        }
    }

    //endregion
}
