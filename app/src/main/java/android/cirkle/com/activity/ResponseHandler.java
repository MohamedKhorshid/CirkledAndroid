package android.cirkle.com.activity;

import android.cirkle.com.error.ErrorMessage;
import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.exception.CirkleUnauthorizedException;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Mohamed Wagdy on 4/11/2015
 */
public class ResponseHandler {
    public static void handleResponse(AsyncTaskResult result, Context context) {
        if(result.getStatus() == AsyncTaskResultStatus.OK) {
            Toast.makeText(context, "User registered successfully", Toast.LENGTH_LONG).show();
        } else if (result.getStatus() == AsyncTaskResultStatus.FAILED){
            if(result.getException() instanceof CirkleBusinessException) {
                CirkleBusinessException businessException = (CirkleBusinessException) result.getException();
                Toast.makeText(context, ErrorMessage.format(businessException.getErrorCode(), context), Toast.LENGTH_LONG).show();
            } else if(result.getException() instanceof CirkleSystemException){
                CirkleSystemException systemException = (CirkleSystemException) result.getException();
                Toast.makeText(context, systemException.getCode() + " " + systemException.getException().getMessage(), Toast.LENGTH_LONG).show();
            } else if(result.getException() instanceof CirkleUnauthorizedException) {
                // redirect to login page
            }

        }
    }
}
