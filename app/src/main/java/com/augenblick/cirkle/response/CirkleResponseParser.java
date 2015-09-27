package com.augenblick.cirkle.response;

import com.augenblick.cirkle.exception.CirkleSystemException;
import com.augenblick.cirkle.json.JsonParser;
import com.augenblick.cirkle.model.Cirkle;
import com.augenblick.cirkle.model.User;
import com.augenblick.cirkle.model.UserLocation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Wagdy on 4/13/2015
 */
public class CirkleResponseParser {

    private static CirkleResponseParser _instance;

    private CirkleResponseParser() {}

    public static CirkleResponseParser getInstance() {
        if(_instance == null) {
            _instance = new CirkleResponseParser();
        }
        return _instance;
    }

    public List<Cirkle> parseCirkles(String json) throws CirkleSystemException {
        List<Cirkle> cirkles = new ArrayList<>();

        JSONArray cirklesArray = JsonParser.parseArray(json);

        for(int i = 0; i < cirklesArray.length(); i++) {
            JSONObject cirkleObj = JsonParser.getJsonObject(cirklesArray, i);

            Cirkle cirkle = doParseCirkle(cirkleObj);
            cirkles.add(cirkle);

        }

        return cirkles;
    }

    public Cirkle parseCirkle(String cirkleJson) throws CirkleSystemException {

        JSONObject cirkleObj = JsonParser.parseObject(cirkleJson);

        return doParseCirkle(cirkleObj);
    }

    private Cirkle doParseCirkle(JSONObject cirkleObj) throws CirkleSystemException {

        Cirkle cirkle = new Cirkle();

        String cirkleName = JsonParser.getJsonString(cirkleObj, "name");
        String cirkleId = JsonParser.getJsonString(cirkleObj, "_id");

        String membersStr = JsonParser.getJsonString(cirkleObj, "members");

        JSONArray membersArr = JsonParser.parseArray(membersStr);

        List<User> members = new ArrayList<User>();

        for(int i = 0; i < membersArr.length(); i++) {
            User user = new User();

            String userId = JsonParser.getJsonString(membersArr, i);
            user.setUserId(userId);

            members.add(user);

        }


        cirkle.setTitle(cirkleName);
        cirkle.setCirkleId(cirkleId);
        cirkle.setMembers(members);
        return cirkle;
    }

    public List<UserLocation> parseCirkleLocations(String json) throws CirkleSystemException {
        List locations = new ArrayList<UserLocation>();

        JSONArray locationsArray = JsonParser.parseArray(json);

        for(int i = 0; i < locationsArray.length(); i++) {
            UserLocation location = new UserLocation();

            JSONObject locationObj = JsonParser.getJsonObject(locationsArray, i);

            location.setUserId(JsonParser.getJsonString(locationObj, "user"));
            location.setLatitude(JsonParser.getJsonDouble(locationObj, "latitude"));
            location.setLongitude(JsonParser.getJsonDouble(locationObj, "longitude"));

            locations.add(location);

        }

        return locations;
    }
}
