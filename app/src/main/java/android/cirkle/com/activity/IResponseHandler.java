package android.cirkle.com.activity;

import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleSystemException;

/**
 * Created by Mohamed Wagdy on 4/12/2015
 */
public interface IResponseHandler {
    public void handleSuccess(Object object);

    public void handleBusinessException(CirkleBusinessException exception);

    public void handleSystemException(CirkleSystemException exception);
}
