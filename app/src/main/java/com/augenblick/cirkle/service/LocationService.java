package com.augenblick.cirkle.service;

import android.content.Context;

import com.augenblick.cirkle.exception.BusinessErrorCode;
import com.augenblick.cirkle.exception.CirkleBusinessException;
import com.augenblick.cirkle.exception.CirkleException;
import com.augenblick.cirkle.exception.CirkleSystemException;
import com.augenblick.cirkle.exception.SystemErrorCode;
import com.augenblick.cirkle.json.JsonParser;
import com.augenblick.cirkle.model.Cirkle;
import com.augenblick.cirkle.model.User;
import com.augenblick.cirkle.model.UserLocation;
import com.augenblick.cirkle.response.CirkleResponse;
import com.augenblick.cirkle.response.CirkleResponseParser;
import com.augenblick.cirkle.rest.RESTUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohamed Wagdy on 3/31/2015
 */
public class LocationService {

    private Context context;

    public LocationService(Context context) {
        this.context = context;
    }

    public void addLocation(double longitude, double latitude) throws CirkleException {

        Map<String, String> params = new HashMap<>();

        params.put("latitude", String.valueOf(latitude));
        params.put("longitude", String.valueOf(longitude));

        new RESTUtil(context).post(ServiceURL.LOCATIONS.BASE, params);

    }

    public List<UserLocation> getCirkleLocations(String cirkleId) throws CirkleException {
        CirkleResponse response = new RESTUtil(context).get(ServiceURL.LOCATIONS.BASE + ServiceURL.LOCATIONS.CIRKLE + "/" + cirkleId);

        return CirkleResponseParser.getInstance().parseCirkleLocations(response.getBody());
    }
}
