package com.augenblick.cirkle.json;

import com.augenblick.cirkle.exception.CirkleSystemException;
import com.augenblick.cirkle.exception.SystemErrorCode;
import com.augenblick.cirkle.model.JSONifiable;
import com.augenblick.cirkle.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.util.List;

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

    public static String getArray(List<? extends JSONifiable> array) throws JSONException {
        JSONStringer jsonArray = new JSONStringer();
        jsonArray.array();
        for(JSONifiable item : array) {
            item.jsonify(jsonArray);
        }
        jsonArray.endArray();
        return jsonArray.toString();
    }
}
