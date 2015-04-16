package android.cirkle.com.json;

import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.exception.SystemErrorCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public class JsonParser {

    public static JSONObject parseObject(String json) throws CirkleSystemException{

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            throw new CirkleSystemException(SystemErrorCode.JSON_PARSE_FAILED, e);
        }

        return jsonObject;
    }

    public static JSONArray parseArray(String json) throws CirkleSystemException{

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json);
        } catch (JSONException e) {
            throw new CirkleSystemException(SystemErrorCode.JSON_PARSE_FAILED, e);
        }

        return jsonArray;
    }

    public static JSONObject getJsonObject(JSONArray jsonArray, int index) throws CirkleSystemException{

        try {
            return jsonArray.getJSONObject(index);
        } catch (JSONException e) {
            throw new CirkleSystemException(SystemErrorCode.JSON_PARSE_FAILED, e);
        }
    }

    public static String getJsonString(JSONObject jsonObj, String name) throws CirkleSystemException{

        try {
            return jsonObj.getString(name);
        } catch (JSONException e) {
            throw new CirkleSystemException(SystemErrorCode.JSON_PARSE_FAILED, e);
        }
    }

    public static int getJsonInt(JSONObject jsonObj, String name) throws CirkleSystemException{

        try {
            return jsonObj.getInt(name);
        } catch (JSONException e) {
            throw new CirkleSystemException(SystemErrorCode.JSON_PARSE_FAILED, e);
        }
    }
}
