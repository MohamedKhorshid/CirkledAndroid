package android.cirkle.com.response;

import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.exception.SystemErrorCode;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public class HttpResponseResolver {

    public static CirkleResponse resolve(String response, int statusCode) throws CirkleSystemException {

        CirkleResponse cirkleResponse = CirkleResponseFactory.getCirkleResponse(statusCode);

        if(cirkleResponse instanceof InvalidResponse) {
            throw new CirkleSystemException(SystemErrorCode.INVALID_RESPONSE, new Exception("Invalid response: " + response));
        } else if(cirkleResponse instanceof OkResponse) {
            cirkleResponse.setBody(response);
        }

        return cirkleResponse;
    }

}
