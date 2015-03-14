package android.cirkle.com.exception;

import android.cirkle.com.json.JsonAttr;
import android.cirkle.com.json.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public class BusinessErrorCode {

    public static String getErrorCode(String json) throws CirkleSystemException {

        JSONObject obj = JsonParser.parseObject(json);
        String errorMessage = JsonParser.getJsonString(obj, JsonAttr.ERROR_PARAM_MESSAGE.getName());

        return errorMessage;
    }

}
