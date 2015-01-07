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

    public static CirkleResponse resolve(String response) throws CirkleSystemException {

        JSONObject jsonResponse = JsonParser.parse(response);

        String type = null;
        try {
            type = jsonResponse.getString(JsonAttr.TYPE.getName());
        } catch (JSONException jex){
            throw new CirkleSystemException(SystemErrorCode.INVALID_RESPONSE, new Exception("Could not parse response: " + response, jex));
        }

        CirkleResponse cirkleResponse = CirkleResponseFactory.getCirkleResponse(type);

        if(cirkleResponse instanceof InvalidResponse) {
            throw new CirkleSystemException(SystemErrorCode.INVALID_RESPONSE, new Exception("Invalid response type: " + type + ". Response: " + response));
        }

        return cirkleResponse;
    }

}
