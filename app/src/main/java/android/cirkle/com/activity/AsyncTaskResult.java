package android.cirkle.com.activity;

import android.cirkle.com.exception.CirkleException;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public class AsyncTaskResult {

    private CirkleException exception;
    private AsyncTaskResultStatus status;

    public AsyncTaskResult(CirkleException ex) {
        this.exception = ex;
        this.status = AsyncTaskResultStatus.FAILED;
    }

    public AsyncTaskResult(){
        this.status = AsyncTaskResultStatus.OK;
    }

    public AsyncTaskResultStatus getStatus() {
        return status;
    }

    public CirkleException getException() {
        return exception;
    }
}

enum AsyncTaskResultStatus {
    OK, FAILED;
}