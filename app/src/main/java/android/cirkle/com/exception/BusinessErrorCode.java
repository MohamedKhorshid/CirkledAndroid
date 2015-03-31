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
public enum BusinessErrorCode {

    MISSING_REQUIRED_FIELDS("missing.required.fields"),
    INVALID_REQUEST("invalid.request"),
    INVALID_EMAIL("invalid.email"),
    PASSWORD_MISMATCH("password.mismatch"),
    USER_EXISTS("user.exists");

    private String message;

    BusinessErrorCode (String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static BusinessErrorCode parseErrorCode(String json) throws CirkleSystemException {

        JSONObject obj = JsonParser.parseObject(json);
        String errorMessage = JsonParser.getJsonString(obj, JsonAttr.ERROR_PARAM_MESSAGE.getName());

        return getValueOf(errorMessage);
    }

    public static BusinessErrorCode getValueOf(String codeName) {
        try {
            return valueOf(codeName);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

}
