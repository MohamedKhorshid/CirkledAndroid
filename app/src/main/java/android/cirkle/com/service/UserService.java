package android.cirkle.com.service;

import android.cirkle.com.exception.BusinessErrorCode;
import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.model.Cirkle;
import android.cirkle.com.model.User;
import android.cirkle.com.response.CirkleResponse;
import android.cirkle.com.response.UserResponseParser;
import android.cirkle.com.rest.RESTUtil;
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

        new RESTUtil(context).post(ServiceURL.REGISTER.getUrl(), params);

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

        new RESTUtil(context).post(ServiceURL.LOGIN.getUrl(), params);

    }

    public List<User> searchUsers(String searchText) throws CirkleException{
        Map<String, String> params = new HashMap<>();
        params.put("searchText", searchText);

        CirkleResponse response = new RESTUtil(context).get(ServiceURL.SEARCH_USERS.getUrl(), params);

        return UserResponseParser.getInstance().parseUsers(response.getBody());

    }
}
