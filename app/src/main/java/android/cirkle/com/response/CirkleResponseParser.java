package android.cirkle.com.response;

import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.json.JsonParser;
import android.cirkle.com.model.Cirkle;

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

            String cirkleName = JsonParser.getJsonString(cirkleObj, "name");
            int cirkleId = JsonParser.getJsonInt(cirkleObj, "cirkleid");

            Cirkle cirkle = new Cirkle();
            cirkle.setTitle(cirkleName);
            cirkle.setCirkleId(cirkleId);
            cirkles.add(cirkle);

        }

        return cirkles;
    }

    public Cirkle parseCirkle(String body) {
        return new Cirkle();
    }
}
