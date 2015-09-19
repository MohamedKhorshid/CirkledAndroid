package com.augenblick.cirkle.activity;

import android.app.Activity;
import com.augenblick.cirkle.R;
import com.augenblick.cirkle.activity.AsyncTaskResult;
import com.augenblick.cirkle.activity.IResponseHandler;
import com.augenblick.cirkle.activity.ResponseHandler;
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

/**
 * Created by Mohamed Wagdy on 1/3/2015.
 */
public class LoginActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void onLoginBtnClick(View view) {

        String email = ((EditText)findViewById(R.id.login_email)).getText().toString();
        String password = ((EditText)findViewById(R.id.login_password)).getText().toString();

        new LoginTask(getApplicationContext()).execute(email, password);

    }

    public void onNewRegBtnClick(View view) {
        Intent i = new Intent(this, RegistrationActivity.class);

        startActivity(i);

        finish();
    }

    class LoginTask extends AsyncTask<String, Void, AsyncTaskResult> {

        private Context context;

        public LoginTask(Context context) {
            this.context = context;
        }

        @Override
        protected AsyncTaskResult doInBackground(String... strings) {
            String email = strings[0];
            String password = strings[1];

            try {
                new UserService(context).loginUser(email, password);
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
                    Toast.makeText(context, "Logged in", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(LoginActivity.this, CirklesActivity.class);

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