package com.augenblick.cirkle.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mohamed Wagdy on 29-06-2015.
 */
public class SessionManager {

    private SharedPreferences pref;

    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String SESSION_PREF = "session_pref";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_PASSWORD = "user_password";

    private static String email;
    private static String password;

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(SESSION_PREF, Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    public void logIn(String email, String password) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_LOGGED_IN, true);

        editor.putString(USER_EMAIL, email);
        editor.putString(USER_PASSWORD, password);

        editor.commit();
    }

    public String getEmail(){
        return pref.getString(USER_EMAIL, "");
    }

    public String getPassword(){
        return pref.getString(USER_PASSWORD, "");
    }

}
