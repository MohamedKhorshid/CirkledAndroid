package com.augenblick.cirkle.exception;

import com.augenblick.cirkle.R;
import com.augenblick.cirkle.json.JsonAttr;
import com.augenblick.cirkle.json.JsonParser;

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

    MISSING_REQUIRED_FIELDS(R.string.registration_missing_required_fields),
    INVALID_REQUEST(R.string.invalid_request),
    INVALID_EMAIL(R.string.registration_invalid_email),
    PASSWORD_MISMATCH(R.string.registration_password_mismatch),
    USER_EXISTS(R.string.registration_user_exists);

    private int message;

    BusinessErrorCode (int message) {
        this.message = message;
    }

    public int getMessage() {
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
