package android.cirkle.com.response;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public class CirkleResponseFactory {
    public static CirkleResponse getCirkleResponse(int responseType) {
        if(responseType == ResponseType.OK.code) {
            return new OkResponse();
        } else if(responseType == ResponseType.VALIDATION_ERROR.code) {
            return new ValidationErrorResponse();
        } else if(responseType == ResponseType.INVALID.code) {
            return new InvalidResponse();
        } else {
            return new InvalidResponse();
        }
    }
}
