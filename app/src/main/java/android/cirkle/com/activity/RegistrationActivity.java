package android.cirkle.com.activity;

import android.app.Activity;
import android.cirkle.com.R;
import android.cirkle.com.error.ErrorMessage;
import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.service.UserService;
import android.cirkle.com.session.SessionUtil;
import android.content.Context;
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
            new UserService().addUser(email, password, password2, displayName);
            SessionUtil.getInstance().setEmail(email);
            SessionUtil.getInstance().setPassword(password);
        } catch(CirkleException cex) {
            return new AsyncTaskResult(cex);
        }

        return new AsyncTaskResult();
    }

    @Override
    protected void onPostExecute(AsyncTaskResult result) {
        ResponseHandler.handleResponse(result, context);
    }
}