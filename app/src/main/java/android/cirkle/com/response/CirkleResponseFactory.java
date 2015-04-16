package android.cirkle.com.response;

import android.util.Log;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public class CirkleResponseFactory {
    public static CirkleResponse getCirkleResponse(int responseType) {
        Log.i("responseType", String.valueOf(responseType));
        if(responseType == ResponseType.OK.code) {
            return new OkResponse();
        } else if(responseType == ResponseType.VALIDATION_ERROR.code) {
            return new ValidationErrorResponse();
        } else if(responseType == ResponseType.INVALID.code) {
            return new UnrecognizedResponse();
        } else if(responseType == ResponseType.NOT_FOUND.code) {
            return new NotFoundResponse();
        } else if(responseType == ResponseType.UNAUTHORIZED.code) {
            return new UnauthorizedResponse();
        } else {
            return new UnrecognizedResponse();
        }
    }
}
