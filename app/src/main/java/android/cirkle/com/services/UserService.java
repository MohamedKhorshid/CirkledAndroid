package android.cirkle.com.services;

import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.rest.RESTUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohamed Wagdy on 1/5/2015
 */
public class UserService {

    public void addUser(String email, String password, String displayName) throws CirkleSystemException, CirkleBusinessException {

        if(email.trim().equals("") || password.trim().equals("")) {
            throw new CirkleBusinessException("missing.required.fields");
        }

        Map<String, String> params = new HashMap<>();

        params.put("email", email);
        params.put("password", password);
        params.put("displayname", displayName);

        RESTUtil.getInstance().post("/users", params);

    }
}
