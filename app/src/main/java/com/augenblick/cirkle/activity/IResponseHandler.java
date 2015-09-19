package com.augenblick.cirkle.activity;

import com.augenblick.cirkle.exception.CirkleBusinessException;
import com.augenblick.cirkle.exception.CirkleSystemException;

/**
 * Created by Mohamed Wagdy on 4/12/2015
 */
public interface IResponseHandler {
    public void handleSuccess(Object object);

    public void handleBusinessException(CirkleBusinessException exception);

    public void handleSystemException(CirkleSystemException exception);
}
