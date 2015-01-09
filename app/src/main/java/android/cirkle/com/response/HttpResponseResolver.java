package android.cirkle.com.response;

import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.exception.SystemErrorCode;
import android.cirkle.com.json.JsonAttr;
import android.cirkle.com.json.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public class HttpResponseResolver {

    public static CirkleResponse resolve(String response, int statusCode) throws CirkleSystemException {

        // JSONObject jsonResponse = JsonParser.parse(response);

        CirkleResponse cirkleResponse = CirkleResponseFactory.getCirkleResponse(statusCode);

        if(cirkleResponse instanceof InvalidResponse) {
            throw new CirkleSystemException(SystemErrorCode.INVALID_RESPONSE, new Exception("Invalid response: " + response));
        }

        return cirkleResponse;
    }

}
