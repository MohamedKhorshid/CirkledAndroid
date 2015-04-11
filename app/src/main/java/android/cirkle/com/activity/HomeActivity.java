package android.cirkle.com.activity;

import android.app.Activity;
import android.cirkle.com.R;
import android.cirkle.com.activity.AsyncTaskResult;
import android.cirkle.com.activity.AsyncTaskResultStatus;
import android.cirkle.com.error.ErrorMessage;
import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.service.CirkleService;
import android.cirkle.com.service.UserService;
import android.cirkle.com.session.SessionUtil;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // insert dummy login data
        SessionUtil.getInstance().setEmail("mohamed@wagdy.com");
        SessionUtil.getInstance().setPassword("mohamed");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.home);

        new LoadCirklesTask(getApplicationContext()).execute();
    }

}

class LoadCirklesTask extends AsyncTask<String, Void, AsyncTaskResult>
{

    private Context context;

    public LoadCirklesTask(Context context) {
        this.context = context;
    }

    @Override
    protected AsyncTaskResult doInBackground (String...strings){
        try {
            new CirkleService().getCirkles();
        } catch (CirkleException cex) {
            return new AsyncTaskResult(cex);
        }

        return new AsyncTaskResult();
    }

    @Override
    protected void onPostExecute (AsyncTaskResult result){
        if (result.getStatus() == AsyncTaskResultStatus.FAILED) {
            if (result.getException() instanceof CirkleBusinessException) {
                CirkleBusinessException businessException = (CirkleBusinessException) result.getException();
                Toast.makeText(context, ErrorMessage.format(businessException.getErrorCode(), context), Toast.LENGTH_LONG).show();
            } else if (result.getException() instanceof CirkleSystemException) {
                CirkleSystemException systemException = (CirkleSystemException) result.getException();
                Toast.makeText(context, systemException.getCode() + " " + systemException.getException().getMessage(), Toast.LENGTH_LONG).show();
            }

        }

    }
}