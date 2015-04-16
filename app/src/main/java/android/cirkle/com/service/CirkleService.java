package android.cirkle.com.service;

import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.model.Cirkle;
import android.cirkle.com.response.CirkleResponse;
import android.cirkle.com.response.CirkleResponseParser;
import android.cirkle.com.rest.RESTUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohamed Wagdy on 3/31/2015
 */
public class CirkleService {

    public List<Cirkle> getCirkles() throws CirkleException {

        Map<String, String> params = new HashMap<>();

        CirkleResponse response = RESTUtil.getInstance().get(ServiceURL.CIRCLE.getUrl(), params);

        return CirkleResponseParser.getInstance().parseCirkles(response.getBody());

    }

    public Cirkle addCirkle(String cirkleName) throws CirkleException {
        Map<String, String> params = new HashMap<>();
        params.put("cirkleName", cirkleName);

        CirkleResponse response = RESTUtil.getInstance().post(ServiceURL.CIRCLE.getUrl(), params);

        return CirkleResponseParser.getInstance().parseCirkle(response.getBody());
    }
}
