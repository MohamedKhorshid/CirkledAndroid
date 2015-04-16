package android.cirkle.com.activity;

import android.cirkle.com.exception.CirkleException;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public class AsyncTaskResult {

    private CirkleException exception;
    private AsyncTaskResultStatus status;
    private Object object;

    public AsyncTaskResult(Object object) {
        this();
        this.object = object;
    }

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

    public Object getObject() {
        return object;
    }
}

enum AsyncTaskResultStatus {
    OK, FAILED;
}