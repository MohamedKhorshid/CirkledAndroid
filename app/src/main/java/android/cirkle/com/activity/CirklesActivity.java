package android.cirkle.com.activity;

import android.app.Activity;
import android.cirkle.com.R;
import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.model.Cirkle;
import android.cirkle.com.service.CirkleService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class CirklesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.waiting);

        new LoadCirklesTask(getApplicationContext()).execute();
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
                List<Cirkle> cirkles = new CirkleService(context).getCirkles();
                return new AsyncTaskResult(cirkles);
            } catch (CirkleException cex) {
                return new AsyncTaskResult(cex);
            }
        }

        @Override
        protected void onPostExecute (AsyncTaskResult result){
            ResponseHandler.handleResponse(result, context, new IResponseHandler() {
                @Override
                public void handleSuccess(Object object) {
                    List<Cirkle> cirkles = (List<Cirkle>) object;

                    // populate view_cirkles
                    CirklesActivity.this.init(cirkles);

                }

                @Override
                public void handleBusinessException(CirkleBusinessException exception) {

                }

                @Override
                public void handleSystemException(CirkleSystemException exception) {
                    Toast.makeText(context, "Failed to load your cirkles", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cirkles_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.cirkles_menu_add:
                openAddCirkleActivity();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    private void openAddCirkleActivity() {
        Intent intent = new Intent(this, EditCirkleActivity.class);
        startActivity(intent);
    }

    private void init(List<Cirkle> cirkles) {
        setContentView(R.layout.view_cirkles);
        final ListView listview = (ListView) findViewById(R.id.cirkles_list);
        listview.setAdapter(new ArrayAdapter<Cirkle>(getApplicationContext(), 0, cirkles) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Cirkle cirkle = getItem(position);

                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
                }

                TextView title = (TextView) convertView.findViewById(R.id.row_label);
                title.setText(cirkle.getTitle());
                return convertView;
            }
        });

    }

}