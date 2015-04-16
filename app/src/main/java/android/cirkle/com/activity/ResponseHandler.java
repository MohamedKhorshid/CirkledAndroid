package android.cirkle.com.activity;

import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.exception.CirkleUnauthorizedException;
import android.content.Context;

/**
 * Created by Mohamed Wagdy on 4/11/2015
 */
public class ResponseHandler {
    public static void handleResponse(AsyncTaskResult result, Context context, IResponseHandler handler) {
        if(result.getStatus() == AsyncTaskResultStatus.OK) {
            handler.handleSuccess(result.getObject());
        } else if (result.getStatus() == AsyncTaskResultStatus.FAILED){
            if(result.getException() instanceof CirkleBusinessException) {
                handler.handleBusinessException((CirkleBusinessException) result.getException());
            } else if(result.getException() instanceof CirkleSystemException){
                handler.handleSystemException((CirkleSystemException) result.getException());
            } else if(result.getException() instanceof CirkleUnauthorizedException) {
                // redirect to login page
            }

        }
    }
}
