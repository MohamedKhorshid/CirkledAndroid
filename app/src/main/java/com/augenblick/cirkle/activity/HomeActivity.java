package com.augenblick.cirkle.activity;

import android.app.Activity;
import com.augenblick.cirkle.session.SessionManager;
import android.content.Intent;
import android.os.Bundle;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SessionManager sessionManager = new SessionManager(this);

        boolean isLoggedIn = sessionManager.isLoggedIn();

        Intent i = null;
        if(isLoggedIn) {
            i = new Intent(this, CirklesActivity.class);
        } else {
            i = new Intent(this, LoginActivity.class);
        }

        startActivity(i);

        finish();
    }
}