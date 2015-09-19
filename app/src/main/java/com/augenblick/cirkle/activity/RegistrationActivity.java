package com.augenblick.cirkle.activity;

import android.app.Activity;
import com.augenblick.cirkle.R;
import com.augenblick.cirkle.error.ErrorMessage;
import com.augenblick.cirkle.exception.CirkleBusinessException;
import com.augenblick.cirkle.exception.CirkleException;
import com.augenblick.cirkle.exception.CirkleSystemException;
import com.augenblick.cirkle.service.UserService;
import com.augenblick.cirkle.session.SessionManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.impl.client.BasicResponseHandler;

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
        String password2 = ((EditText)findViewById(R.id.reg_password2)).getText().toString();
        String displayName = ((EditText)findViewById(R.id.reg_display_name)).getText().toString();

        new RegisterUserTask(getApplicationContext()).execute(email, password, password2, displayName);

    }

    public void onLoginBtnClick(View view) {
        Intent i = new Intent(this, LoginActivity.class);

        startActivity(i);

        finish();
    }

    class RegisterUserTask extends AsyncTask<String, Void, AsyncTaskResult> {

        private Context context;

        public RegisterUserTask(Context context) {
            this.context = context;
        }

        @Override
        protected AsyncTaskResult doInBackground(String... strings) {
            String email = strings[0];
            String password = strings[1];
            String password2 = strings[2];
            String displayName = strings[3];

            try {
                new UserService(context).addUser(email, password, password2, displayName);

                SessionManager sessionManager = new SessionManager(context);
                sessionManager.logIn(email, password);
            } catch(CirkleException cex) {
                return new AsyncTaskResult(cex);
            }

            return new AsyncTaskResult();
        }

        @Override
        protected void onPostExecute(AsyncTaskResult result) {
            ResponseHandler.handleResponse(result, context, new IResponseHandler() {
                @Override
                public void handleSuccess(Object object) {
                    Toast.makeText(context, "User registered successfully", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(RegistrationActivity.this, CirklesActivity.class);

                    startActivity(i);

                    finish();
                }

                @Override
                public void handleBusinessException(CirkleBusinessException exception) {
                    Toast.makeText(context, ErrorMessage.format(exception.getErrorCode(), context), Toast.LENGTH_LONG).show();
                }

                @Override
                public void handleSystemException(CirkleSystemException exception) {
                    Toast.makeText(context, exception.getCode() + " " + exception.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}