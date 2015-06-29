package android.cirkle.com.activity;

import android.app.Activity;
import android.cirkle.com.R;
import android.cirkle.com.activity.AsyncTaskResult;
import android.cirkle.com.activity.IResponseHandler;
import android.cirkle.com.activity.ResponseHandler;
import android.cirkle.com.error.ErrorMessage;
import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.service.UserService;
import android.cirkle.com.session.SessionManager;
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

                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(i);
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