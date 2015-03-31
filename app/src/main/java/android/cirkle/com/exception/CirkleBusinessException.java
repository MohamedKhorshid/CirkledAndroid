package android.cirkle.com.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public class CirkleBusinessException extends CirkleException{

    public CirkleBusinessException(BusinessErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    private BusinessErrorCode errorCode;

    public BusinessErrorCode getErrorCode() {
        return errorCode;
    }
}
