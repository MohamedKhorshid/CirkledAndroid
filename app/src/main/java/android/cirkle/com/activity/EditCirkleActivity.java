package android.cirkle.com.activity;

import android.app.Activity;
import android.cirkle.com.R;
import android.cirkle.com.component.AutoCompleteAdapter;
import android.cirkle.com.component.CirkleAutoCompleteTextView;
import android.cirkle.com.error.ErrorMessage;
import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.json.JsonParser;
import android.cirkle.com.model.Cirkle;
import android.cirkle.com.model.JSONifiable;
import android.cirkle.com.model.User;
import android.cirkle.com.service.CirkleService;
import android.cirkle.com.service.UserService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class EditCirkleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_cirkle);

        initClassMembers();

        bindControls();

    }

    private void initClassMembers() {
        members = new ArrayList<>();
    }

    private List<User> members;

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

        // members list
        final ListView membersList = (ListView) findViewById(R.id.add_cirkle_members_list);
        membersList.setAdapter(new ArrayAdapter<User>(getApplicationContext(), 0, members) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                User user = getItem(position);

                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
                }

                TextView title = (TextView) convertView.findViewById(R.id.row_label);
                if (!user.getDisplayName().isEmpty()) {
                    title.setText(user.getDisplayName());
                } else {
                    title.setText(user.getEmail());
                }

                return convertView;
            }
        });

        // auto complete
        final CirkleAutoCompleteTextView autoComplete = (CirkleAutoCompleteTextView) findViewById(R.id.add_cirkle_members_ac);
        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowId) {
                User user = (User) view.getTag();
                members.add(user);
                Adapter adapter = membersList.getAdapter();
                if(adapter instanceof BaseAdapter) {
                    ((BaseAdapter) adapter).notifyDataSetChanged();
                }
                autoComplete.setText("");
            }
        });
        autoComplete.setThreshold(1);

        autoComplete.setAdapter(new AutoCompleteAdapter<User>() {
            @Override
            public List<User> getAutoCompleteResults(CharSequence charSequence) {
                try {
                    return new UserService().searchUsers((String) charSequence);
                } catch (CirkleException e) {
                    Toast.makeText(EditCirkleActivity.this, "Failed to search member", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }

            @Override
            public CharSequence getObjectAsString(User user) {
                if (!user.getDisplayName().isEmpty()) {
                    return user.getDisplayName();
                } else {
                    return user.getEmail();
                }

            }

            @Override
            public View getView(int position, View convertView, ViewGroup viewGroup) {
                User user = getItem(position);

                if (convertView == null) {
                    convertView = LayoutInflater.from(EditCirkleActivity.this).inflate(R.layout.list_row, viewGroup, false);
                }

                convertView.setTag(user);

                TextView title = (TextView) convertView.findViewById(R.id.row_label);

                if (!user.getDisplayName().isEmpty()) {
                    title.setText(user.getDisplayName());
                } else {
                    title.setText(user.getEmail());
                }

                return convertView;
            }
        });

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
                new CirkleService().addCirkle(cirkleName, members);
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