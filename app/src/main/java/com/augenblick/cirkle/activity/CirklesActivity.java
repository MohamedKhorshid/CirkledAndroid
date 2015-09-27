package com.augenblick.cirkle.activity;

import android.app.Activity;

import com.augenblick.cirkle.R;
import com.augenblick.cirkle.error.ErrorMessage;
import com.augenblick.cirkle.exception.CirkleBusinessException;
import com.augenblick.cirkle.exception.CirkleException;
import com.augenblick.cirkle.exception.CirkleSystemException;
import com.augenblick.cirkle.location.LocationService;
import com.augenblick.cirkle.model.Cirkle;
import com.augenblick.cirkle.service.CirkleService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class CirklesActivity extends Activity {

    private static final int CONTEXT_MENU_DELETE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.waiting);

        new LoadCirklesTask(getApplicationContext()).execute();

        startService(new Intent(this, LocationService.class));

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

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cirkle cirkle =  (Cirkle) ((ListView)parent).getItemAtPosition(position);

                Intent intent = new Intent(CirklesActivity.this, ViewCirkleActivity.class);
                intent.putExtra("cirkleId" , cirkle.getCirkleId());
                intent.putExtra("cirkleName" , cirkle.getTitle());
                startActivity(intent);
            }
        });

        registerForContextMenu(listview);

    }

    private void removeCirkleFromList(Cirkle cirkle) {
        final ListView listview = (ListView) findViewById(R.id.cirkles_list);
        ((ArrayAdapter)listview.getAdapter()).remove(cirkle);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.cirkles_list) {
            menu.add(0, CONTEXT_MENU_DELETE, 0, R.string.delete);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Object obj = ((ListView)info.targetView.getParent()).getItemAtPosition(info.position);
        switch(item.getItemId()) {
            case CONTEXT_MENU_DELETE:
                new DeleteCirkleTask(this).execute((Cirkle) obj);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    class DeleteCirkleTask extends AsyncTask<Cirkle, Void, AsyncTaskResult> {

        private Context context;

        public DeleteCirkleTask(Context context) {
            this.context = context;
        }

        @Override
        protected AsyncTaskResult doInBackground(Cirkle... cirkles) {

            try {
                new CirkleService(context).deleteCirkle(cirkles[0]);
            } catch (CirkleException cex) {
                return new AsyncTaskResult(cex);
            }

            return new AsyncTaskResult(cirkles[0]);
        }

        @Override
        protected void onPostExecute(AsyncTaskResult result) {
            ResponseHandler.handleResponse(result, context, new IResponseHandler() {
                @Override
                public void handleSuccess(Object object) {
                    Toast.makeText(context, "Cirkle deleted", Toast.LENGTH_LONG).show();
                    removeCirkleFromList((Cirkle)object);
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