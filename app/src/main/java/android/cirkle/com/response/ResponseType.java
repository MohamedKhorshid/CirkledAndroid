package android.cirkle.com.response;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public enum ResponseType {
    OK(200),
    VALIDATION_ERROR(400),
    UNAUTHORIZED(401),
    INVALID(500);

    int code;

    ResponseType(int code) {
        this.code = code;
    }
}
