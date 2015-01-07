package android.cirkle.com.activity;

import android.app.Activity;
import android.cirkle.com.R;
import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.services.UserService;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

        new RegisterUserTask(getApplicationContext()).execute(email, password, displayName);

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
        String displayName = strings[2];

        try {
            new UserService().addUser(email, password, displayName);
        } catch(CirkleException cex) {
            return new AsyncTaskResult(cex);
        }

        return new AsyncTaskResult();
    }

    @Override
    protected void onPostExecute(AsyncTaskResult result) {
        if(result.getStatus() == AsyncTaskResultStatus.OK) {
            Toast.makeText(context, "User registered successfully", Toast.LENGTH_LONG).show();
        } else if (result.getStatus() == AsyncTaskResultStatus.FAILED){
            if(result.getException() instanceof CirkleBusinessException) {
                CirkleBusinessException businessException = (CirkleBusinessException) result.getException();
                Toast.makeText(context, businessException.getCode().toString(), Toast.LENGTH_LONG).show();
            } else if(result.getException() instanceof CirkleSystemException){
                CirkleSystemException systemException = (CirkleSystemException) result.getException();
                Toast.makeText(context, systemException.getCode() + " " + systemException.getException().getMessage(), Toast.LENGTH_LONG).show();
            }

        }

    }
}