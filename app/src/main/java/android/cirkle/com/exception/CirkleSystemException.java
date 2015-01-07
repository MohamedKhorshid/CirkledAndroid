package android.cirkle.com.exception;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public class CirkleSystemException extends CirkleException {

    private Exception exception;
    private SystemErrorCode code;

    public CirkleSystemException(SystemErrorCode code, Exception exception) {
        this.code = code;
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
    public SystemErrorCode getCode() {
        return code;
    }

}
