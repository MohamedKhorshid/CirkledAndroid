package android.cirkle.com.service;

import android.cirkle.com.exception.BusinessErrorCode;
import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.rest.RESTUtil;
import android.util.Patterns;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohamed Wagdy on 1/5/2015
 */
public class UserService {

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

        RESTUtil.getInstance().post(ServiceURL.REGISTER.getUrl(), params);

    }
}
