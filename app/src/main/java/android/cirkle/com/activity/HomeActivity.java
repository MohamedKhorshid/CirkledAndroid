package android.cirkle.com.activity;

import android.app.Activity;
import android.cirkle.com.session.SessionManager;
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

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(i);
    }
}