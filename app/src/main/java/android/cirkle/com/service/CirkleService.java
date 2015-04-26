package android.cirkle.com.service;

import android.cirkle.com.exception.BusinessErrorCode;
import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.model.Cirkle;
import android.cirkle.com.response.CirkleResponse;
import android.cirkle.com.response.CirkleResponseParser;
import android.cirkle.com.rest.RESTUtil;
import android.cirkle.com.session.SessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohamed Wagdy on 3/31/2015
 */
public class CirkleService {

    public List<Cirkle> getCirkles() throws CirkleException {

        Map<String, String> params = new HashMap<>();
        params.put("email", SessionUtil.getInstance().getEmail());

        CirkleResponse response = RESTUtil.getInstance().get(ServiceURL.CIRCLES.getUrl(), params);

        return CirkleResponseParser.getInstance().parseCirkles(response.getBody());

    }

    public Cirkle addCirkle(String cirkleName) throws CirkleException {

        if(cirkleName.trim().isEmpty()) {
            throw new CirkleBusinessException(BusinessErrorCode.MISSING_REQUIRED_FIELDS);
        }

        Map<String, String> params = new HashMap<>();
        params.put("cirkleName", cirkleName);
        params.put("admin", SessionUtil.getInstance().getEmail());

        CirkleResponse response = RESTUtil.getInstance().post(ServiceURL.CIRCLES.getUrl(), params);

        return CirkleResponseParser.getInstance().parseCirkle(response.getBody());
    }
}
