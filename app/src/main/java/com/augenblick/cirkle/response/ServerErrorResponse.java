package com.augenblick.cirkle.response;

import com.augenblick.cirkle.exception.SystemErrorCode;

/**
 * Created by Mohamed Wagdy on 17-07-2015.
 */
public class ServerErrorResponse extends SystemErrorResponse {
    @Override
    public SystemErrorCode getErrorCode() {
        return SystemErrorCode.SERVER_ERROR;
    }
}
