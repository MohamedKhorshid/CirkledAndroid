package android.cirkle.com.service;

import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.model.Cirkle;
import android.cirkle.com.response.CirkleResponse;
import android.cirkle.com.rest.RESTUtil;

import java.util.ArrayList;
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

        List<Cirkle> cirkles = new ArrayList<>();

        // parse response

        return cirkles;

    }
}
