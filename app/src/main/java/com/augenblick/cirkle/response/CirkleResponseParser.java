package com.augenblick.cirkle.response;

import com.augenblick.cirkle.exception.CirkleSystemException;
import com.augenblick.cirkle.json.JsonParser;
import com.augenblick.cirkle.model.Cirkle;

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

        cirkle.setTitle(cirkleName);
        cirkle.setCirkleId(cirkleId);
        return cirkle;
    }
}
