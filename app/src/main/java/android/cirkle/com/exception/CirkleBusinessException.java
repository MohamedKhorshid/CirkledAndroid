package android.cirkle.com.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public class CirkleBusinessException extends CirkleException{

    private String errorCode;

    public CirkleBusinessException(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
