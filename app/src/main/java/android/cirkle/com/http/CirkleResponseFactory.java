package android.cirkle.com.http;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public class CirkleResponseFactory {
    public static CirkleResponse getCirkleResponse(String responseType) {
        if(responseType == null) {
            return new InvalidResponse();
        } else {
            return new OkResponse();
        }
    }
}
