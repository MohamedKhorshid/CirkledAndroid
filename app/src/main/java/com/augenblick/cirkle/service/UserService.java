package com.augenblick.cirkle.service;

import com.augenblick.cirkle.exception.BusinessErrorCode;
import com.augenblick.cirkle.exception.CirkleBusinessException;
import com.augenblick.cirkle.exception.CirkleException;
import com.augenblick.cirkle.exception.CirkleSystemException;
import com.augenblick.cirkle.model.Cirkle;
import com.augenblick.cirkle.model.User;
import com.augenblick.cirkle.response.CirkleResponse;
import com.augenblick.cirkle.response.UserResponseParser;
import com.augenblick.cirkle.rest.RESTUtil;
import android.content.Context;
import android.util.Patterns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohamed Wagdy on 1/5/2015
 */
public class UserService {

    private Context context;

    public UserService(Context context) {
        this.context = context;
    }

    public void addUser(String email, String password, String password2, String displayName) throws CirkleException {

        if(!email.trim().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw new CirkleBusinessException(BusinessErrorCode.INVALID_EMAIL);
        }

        if(email.trim().isEmpty() || password.trim().isEmpty() || password2.trim().isEmpty()) {
            throw new CirkleBusinessException(BusinessErrorCode.MISSING_REQUIRED_FIELDS);
        }

        if(!password.equals(password2)) {
            throw new CirkleBusinessException(BusinessErrorCode.PASSWORD_MISMATCH);
        }

        Map<String, String> params = new HashMap<>();

        params.put("email", email);
        params.put("password", password);
        params.put("displayname", displayName);

        new RESTUtil(context).post(ServiceURL.REGISTER.BASE, params);

    }

    public void loginUser(String email, String password) throws CirkleException {

        if(!email.trim().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw new CirkleBusinessException(BusinessErrorCode.INVALID_EMAIL);
        }

        if(email.trim().isEmpty() || password.trim().isEmpty()) {
            throw new CirkleBusinessException(BusinessErrorCode.MISSING_REQUIRED_FIELDS);
        }

        Map<String, String> params = new HashMap<>();

        params.put("email", email);
        params.put("password", password);

        new RESTUtil(context).post(ServiceURL.LOGIN.BASE, params);

    }

    public List<User> searchUsers(String searchText) throws CirkleException{
        Map<String, String> params = new HashMap<>();
        params.put("searchText", searchText);

        CirkleResponse response = new RESTUtil(context).get(ServiceURL.SEARCH_USERS.BASE, params);

        return UserResponseParser.getInstance().parseUsers(response.getBody());

    }
}
