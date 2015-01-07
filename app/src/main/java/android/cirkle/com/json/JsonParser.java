package android.cirkle.com.json;

import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.exception.SystemErrorCode;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public class JsonParser {

    public static JSONObject parse(String json) throws CirkleSystemException{

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            throw new CirkleSystemException(SystemErrorCode.JSON_PARSE_FAILED, e);
        }

        return jsonObject;
    }
}
