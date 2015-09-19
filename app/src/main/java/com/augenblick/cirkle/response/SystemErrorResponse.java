package com.augenblick.cirkle.response;

import com.augenblick.cirkle.exception.SystemErrorCode;

/**
 * Created by Mohamed Wagdy on 4/16/2015
 */
public abstract class SystemErrorResponse extends CirkleResponse {
    public abstract SystemErrorCode getErrorCode();
}
