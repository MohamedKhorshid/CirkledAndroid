package android.cirkle.com.exception;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public class CirkleBusinessException extends CirkleException{

    private BusinessErrorCode code;

    public CirkleBusinessException(BusinessErrorCode code) {
        this.code = code;
    }

    public BusinessErrorCode getCode() {
        return code;
    }
}
