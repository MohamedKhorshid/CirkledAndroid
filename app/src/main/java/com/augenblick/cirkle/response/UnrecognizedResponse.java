package com.augenblick.cirkle.response;

import com.augenblick.cirkle.exception.SystemErrorCode;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public class UnrecognizedResponse extends SystemErrorResponse {
    @Override
    public SystemErrorCode getErrorCode() {
        return SystemErrorCode.UNRECOGNIZED_RESPONSE;
    }
}
