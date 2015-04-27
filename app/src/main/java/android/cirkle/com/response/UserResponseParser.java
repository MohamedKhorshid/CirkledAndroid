package android.cirkle.com.response;

import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.json.JsonParser;
import android.cirkle.com.model.Cirkle;
import android.cirkle.com.model.User;

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
            JSONObject cirkleObj = JsonParser.getJsonObject(usersArray, i);

            String displayName = JsonParser.getJsonString(cirkleObj, "displayname");
            String email = JsonParser.getJsonString(cirkleObj, "email");
            int userId = JsonParser.getJsonInt(cirkleObj, "userid");

            User user = new User();
            if(!displayName.isEmpty()) {
                user.setDisplayName(displayName);
            } else {
                user.setDisplayName(email);
            }
            user.setEmail(email);
            user.setUserId(userId);

            users.add(user);

        }

        return users;
    }

    public Cirkle parseCirkle(String body) {
        return new Cirkle();
    }
}
