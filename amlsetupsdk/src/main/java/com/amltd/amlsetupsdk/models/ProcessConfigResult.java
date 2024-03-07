package com.amltd.amlsetupsdk.models;

import java.util.List;

public class ProcessConfigResult {
    boolean Success;
    List<FailedSdkTask> FailedTasks;

    public boolean isSuccess() {
        return Success;
    }

    void setSuccess(boolean success) {
        Success = success;
    }

    public List<FailedSdkTask> getFailedTasks() {
        return FailedTasks;
    }

    void setFailedTasks(List<FailedSdkTask> failedTasks) {
        FailedTasks = failedTasks;
    }
}
