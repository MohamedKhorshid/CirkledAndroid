package android.cirkle.com.response;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public class CirkleResponseFactory {
    public static CirkleResponse getCirkleResponse(String responseType) {
        if(responseType == null) {
            return new InvalidResponse();
        } else if(responseType.equals(ResponseType.OK)) {
            return new OkResponse();
        } else if(responseType.equals(ResponseType.VALIDATION_ERROR)) {
            return new ValidationErrorResponse();
        } else {
            return new InvalidResponse();
        }
    }
}
