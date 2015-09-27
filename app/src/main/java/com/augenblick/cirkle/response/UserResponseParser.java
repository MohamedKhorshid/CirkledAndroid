package com.augenblick.cirkle.response;

import com.augenblick.cirkle.exception.CirkleSystemException;
import com.augenblick.cirkle.json.JsonParser;
import com.augenblick.cirkle.model.Cirkle;
import com.augenblick.cirkle.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Wagdy on 4/13/2015
 */
public class UserResponseParser {

    private static UserResponseParser _instance;

    private UserResponseParser() {}

    public static UserResponseParser getInstance() {
        if(_instance == null) {
            _instance = new UserResponseParser();
        }
        return _instance;
    }

    public List<User> parseUsers(String json) throws CirkleSystemException {
        List<User> users = new ArrayList<>();

        JSONArray usersArray = JsonParser.parseArray(json);

        for(int i = 0; i < usersArray.length(); i++) {
            JSONObject userObj = JsonParser.getJsonObject(usersArray, i);

            String displayName = JsonParser.getJsonString(userObj, "displayname");
            String email = JsonParser.getJsonString(userObj, "email");
            String userId = JsonParser.getJsonString(userObj, "_id");

            User user = new User();

            user.setDisplayName(displayName);
            user.setEmail(email);
            user.setUserId(userId);

            users.add(user);

        }

        return users;
    }

}
