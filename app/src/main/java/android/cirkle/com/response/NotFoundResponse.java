package android.cirkle.com.response;

import android.cirkle.com.exception.SystemErrorCode;

/**
 * Created by Mohamed Wagdy on 4/16/2015
 */
public class NotFoundResponse extends SystemErrorResponse {

    @Override
    public SystemErrorCode getErrorCode() {
        return SystemErrorCode.NOT_FOUND;
    }
}
