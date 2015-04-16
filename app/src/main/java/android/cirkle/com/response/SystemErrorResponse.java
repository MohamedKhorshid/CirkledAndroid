package android.cirkle.com.response;

import android.cirkle.com.exception.SystemErrorCode;

/**
 * Created by Mohamed Wagdy on 4/16/2015
 */
public abstract class SystemErrorResponse extends CirkleResponse {
    public abstract SystemErrorCode getErrorCode();
}
