package android.cirkle.com.activity;

import android.app.Activity;
import android.cirkle.com.R;
import android.cirkle.com.component.AutoCompleteAdapter;
import android.cirkle.com.component.MyAutoCompleteTextView;
import android.cirkle.com.error.ErrorMessage;
import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.model.Cirkle;
import android.cirkle.com.model.User;
import android.cirkle.com.service.CirkleService;
import android.cirkle.com.session.SessionUtil;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class EditCirkleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_cirkle);

        bindControls();

    }

    private void bindControls() {
        // add button
        Button addCirkleBtn = (Button) findViewById(R.id.add_cirkle_btn);
        addCirkleBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String cirkleName = ((EditText) findViewById(R.id.add_cirkle_txt_name)).getText().toString();

                new AddCirkleTask(getApplicationContext()).execute(cirkleName);

            }
        });

        // auto complete
        final MyAutoCompleteTextView autoComplete = (MyAutoCompleteTextView) findViewById(R.id.add_cirkle_member_ac);

        autoComplete.setAdapter(new AutoCompleteAdapter<Cirkle>() {
            @Override
            public List<Cirkle> getAutoCompleteResults(CharSequence charSequence) {
                try {
                    return new CirkleService().getCirkles();
                } catch (CirkleException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public View getView(int position, View convertView, ViewGroup viewGroup) {
                Cirkle cirkle = getItem(position);

                if (convertView == null) {
                    convertView = LayoutInflater.from(EditCirkleActivity.this).inflate(R.layout.list_row, viewGroup, false);
                }

                TextView title = (TextView) convertView.findViewById(R.id.cirkle_row_title);
                title.setText(cirkle.getTitle());

                return convertView;
            }
        });

        autoComplete.setThreshold(1);

    }

    class AddCirkleTask extends AsyncTask<String, Void, AsyncTaskResult> {

        private Context context;

        public AddCirkleTask(Context context) {
            this.context = context;
        }

        @Override
        protected AsyncTaskResult doInBackground(String... strings) {
            String cirkleName = strings[0];

            try {
                new CirkleService().addCirkle(cirkleName);
            } catch (CirkleException cex) {
                return new AsyncTaskResult(cex);
            }

            return new AsyncTaskResult();
        }

        @Override
        protected void onPostExecute(AsyncTaskResult result) {
            ResponseHandler.handleResponse(result, context, new IResponseHandler() {
                @Override
                public void handleSuccess(Object object) {
                    Toast.makeText(context, "Cirkle added successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EditCirkleActivity.this, HomeActivity.class);
                    startActivity(intent);
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