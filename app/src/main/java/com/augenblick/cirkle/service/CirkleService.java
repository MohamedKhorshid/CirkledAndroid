package com.augenblick.cirkle.service;

import com.augenblick.cirkle.exception.BusinessErrorCode;
import com.augenblick.cirkle.exception.CirkleBusinessException;
import com.augenblick.cirkle.exception.CirkleException;
import com.augenblick.cirkle.exception.CirkleSystemException;
import com.augenblick.cirkle.exception.SystemErrorCode;
import com.augenblick.cirkle.json.JsonParser;
import com.augenblick.cirkle.model.Cirkle;
import com.augenblick.cirkle.model.User;
import com.augenblick.cirkle.response.CirkleResponse;
import com.augenblick.cirkle.response.CirkleResponseParser;
import com.augenblick.cirkle.rest.RESTUtil;
import android.content.Context;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohamed Wagdy on 3/31/2015
 */
public class CirkleService {

    private Context context;

    public CirkleService(Context context) {
        this.context = context;
    }

    public List<Cirkle> getCirkles() throws CirkleException {
        CirkleResponse response = new RESTUtil(context).get(ServiceURL.CIRCLES.getUrl());

        return CirkleResponseParser.getInstance().parseCirkles(response.getBody());

    }

    public Cirkle addCirkle(String cirkleName, List<User> members) throws CirkleException {

        if(cirkleName.trim().isEmpty()) {
            throw new CirkleBusinessException(BusinessErrorCode.MISSING_REQUIRED_FIELDS);
        }

        Map<String, String> params = new HashMap<>();
        params.put("cirkleName", cirkleName);
        try {
            params.put("members", JsonParser.getArray(members));
        } catch (JSONException ex) {
            throw new CirkleSystemException(SystemErrorCode.JSON_PARSE_FAILED ,ex);
        }

        new RESTUtil(context).post(ServiceURL.CIRCLES.getUrl(), params);

        return null;
    }

    public void deleteCirkle(Cirkle cirkle) throws CirkleException {
        new RESTUtil(context).delete(ServiceURL.CIRCLES.getUrl() + "/" + cirkle.getCirkleId());
    }

    public Cirkle getCirkle(String cirkleId) throws CirkleException {
        CirkleResponse response = new RESTUtil(context).get(ServiceURL.CIRCLES.getUrl() + "/" + cirkleId);

        return CirkleResponseParser.getInstance().parseCirkle(response.getBody());
    }
}
