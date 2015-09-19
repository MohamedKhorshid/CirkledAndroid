package com.augenblick.cirkle.activity;

import android.app.Activity;
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

import com.augenblick.cirkle.R;
import com.augenblick.cirkle.component.AutoCompleteAdapter;
import com.augenblick.cirkle.component.CirkleAutoCompleteTextView;
import com.augenblick.cirkle.error.ErrorMessage;
import com.augenblick.cirkle.exception.CirkleBusinessException;
import com.augenblick.cirkle.exception.CirkleException;
import com.augenblick.cirkle.exception.CirkleSystemException;
import com.augenblick.cirkle.model.User;
import com.augenblick.cirkle.service.CirkleService;
import com.augenblick.cirkle.service.UserService;

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

                new AddCirkleTask(getApplicationContext(), cirkleName).execute();

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
                    return new UserService(EditCirkleActivity.this).searchUsers((String) charSequence);
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
        private String cirkleName;

        public AddCirkleTask(Context context, String cirkleName) {
            this.context = context;
            this.cirkleName = cirkleName;
        }

        @Override
        protected AsyncTaskResult doInBackground(String... strings) {

            try {
                new CirkleService(context).addCirkle(cirkleName, members);
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
                    Intent intent = new Intent(EditCirkleActivity.this, CirklesActivity.class);
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