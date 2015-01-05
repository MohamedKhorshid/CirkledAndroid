package android.cirkle.com.activity;

import android.app.Activity;
import android.cirkle.com.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Mohamed Wagdy on 1/3/2015.
 */
public class RegistrationActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
    }

    public void onRegistrationBtnClick(View view) {

        String email = ((EditText)findViewById(R.id.reg_email)).getText().toString();
        String password = ((EditText)findViewById(R.id.reg_password)).getText().toString();
        String displayName = ((EditText)findViewById(R.id.reg_display_name)).getText().toString();

        new RegisterUserTask().execute(email, password, displayName);

    }
}

class RegisterUserTask extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... strings) {
        String email = strings[0];
        String password = strings[1];
        String displayName = strings[2];

        new UserService().addUser(email, password, displayName);

        return null;
    }
}