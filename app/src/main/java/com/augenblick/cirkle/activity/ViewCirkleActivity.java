package com.augenblick.cirkle.activity;

import com.augenblick.cirkle.R;
import com.augenblick.cirkle.exception.CirkleBusinessException;
import com.augenblick.cirkle.exception.CirkleException;
import com.augenblick.cirkle.exception.CirkleSystemException;
import com.augenblick.cirkle.model.Cirkle;
import com.augenblick.cirkle.service.CirkleService;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by Mohamed Wagdy on 17-07-2015.
 */
public class ViewCirkleActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.waiting);

        String cirkleId = getIntent().getStringExtra("cirkleId");
        String cirkleName = getIntent().getStringExtra("cirkleName");

        setTitle(cirkleName);

        new LoadCirkleTask(getApplicationContext()).execute(cirkleId);

    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.cirkle_map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

            }
        });
    }

    class LoadCirkleTask extends AsyncTask<String, Void, AsyncTaskResult>
    {

        private Context context;

        public LoadCirkleTask(Context context) {
            this.context = context;
        }

        @Override
        protected AsyncTaskResult doInBackground (String... strings){
            try {
                Cirkle cirkle = new CirkleService(context).getCirkle(strings[0]);
                return new AsyncTaskResult(cirkle);
            } catch (CirkleException cex) {
                return new AsyncTaskResult(cex);
            }
        }

        @Override
        protected void onPostExecute (AsyncTaskResult result){
            ResponseHandler.handleResponse(result, context, new IResponseHandler() {
                @Override
                public void handleSuccess(Object object) {
                    Cirkle cirkle = (Cirkle) object;

                    ViewCirkleActivity.this.setTitle(cirkle.getTitle());

                    setContentView(R.layout.view_cirkle);

                    initMap();

                }

                @Override
                public void handleBusinessException(CirkleBusinessException exception) {

                }

                @Override
                public void handleSystemException(CirkleSystemException exception) {
                    Toast.makeText(context, "Failed to load cirkle", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

}
