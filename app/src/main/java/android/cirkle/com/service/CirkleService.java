package android.cirkle.com.service;

import android.cirkle.com.exception.BusinessErrorCode;
import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.exception.SystemErrorCode;
import android.cirkle.com.json.JsonParser;
import android.cirkle.com.model.Cirkle;
import android.cirkle.com.model.User;
import android.cirkle.com.response.CirkleResponse;
import android.cirkle.com.response.CirkleResponseParser;
import android.cirkle.com.rest.RESTUtil;
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

        Map<String, String> params = new HashMap<>();

        CirkleResponse response = new RESTUtil(context).get(ServiceURL.CIRCLES.getUrl(), params);

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

        CirkleResponse response = new RESTUtil(context).post(ServiceURL.CIRCLES.getUrl(), params);

        return CirkleResponseParser.getInstance().parseCirkle(response.getBody());
    }
}
