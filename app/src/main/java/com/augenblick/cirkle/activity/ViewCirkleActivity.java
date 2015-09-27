package com.augenblick.cirkle.activity;

import com.augenblick.cirkle.R;
import com.augenblick.cirkle.exception.CirkleBusinessException;
import com.augenblick.cirkle.exception.CirkleException;
import com.augenblick.cirkle.exception.CirkleSystemException;
import com.augenblick.cirkle.location.PositioningService;
import com.augenblick.cirkle.model.Cirkle;
import com.augenblick.cirkle.model.UserLocation;
import com.augenblick.cirkle.service.CirkleService;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.augenblick.cirkle.service.LocationService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by Mohamed Wagdy on 17-07-2015.
 */
public class ViewCirkleActivity extends FragmentActivity {

    private MapFragment mapFragment;

    private Cirkle cirkle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_cirkle);

        String cirkleId = getIntent().getStringExtra("cirkleId");
        String cirkleName = getIntent().getStringExtra("cirkleName");


        setTitle(cirkleName);

        initMap();

        new LoadCirkleTask(getApplicationContext(), cirkleId).execute();
        new GetCirkleLocationsTask(getApplicationContext(), cirkleId).execute();

    }

    private void initMap() {

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.cirkle_map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                Location myLocation = new PositioningService().getLastKnownLocation(ViewCirkleActivity.this);
                if (myLocation != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 14));
                }
            }
        });
    }

    private void updateMap() {
        setTitle(cirkle.getTitle());
    }

    private void handleUserLocations(List<UserLocation> locations) {
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for(UserLocation location : locations) {
            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(latlng).title(location.getUserId());
            mapFragment.getMap().addMarker(markerOptions);

            builder.include(latlng);
        }

        mapFragment.getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 300));
    }

    class LoadCirkleTask extends AsyncTask<String, Void, AsyncTaskResult>
    {

        private Context context;
        private String cirkleId;

        public LoadCirkleTask(Context context, String cirkleId) {
            this.context = context;
            this.cirkleId = cirkleId;
        }

        @Override
        protected AsyncTaskResult doInBackground (String... strings){
            try {
                Cirkle cirkle = new CirkleService(context).getCirkle(cirkleId);
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

                    ViewCirkleActivity.this.cirkle = cirkle;

                    updateMap();

                }

                @Override
                public void handleBusinessException(CirkleBusinessException exception) {
                    Toast.makeText(context, "Failed to load cirkle", Toast.LENGTH_LONG).show();
                }

                @Override
                public void handleSystemException(CirkleSystemException exception) {
                    Toast.makeText(context, "Failed to load cirkle", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    class GetCirkleLocationsTask extends AsyncTask<String, Void, AsyncTaskResult>
    {

        private Context context;
        private String cirkleId;

        public GetCirkleLocationsTask(Context context, String cirkleId) {
            this.context = context;
            this.cirkleId = cirkleId;
        }

        @Override
        protected AsyncTaskResult doInBackground (String... strings){
            try {
                List<UserLocation> locations = new LocationService(context).getCirkleLocations(cirkleId);
                return new AsyncTaskResult(locations);
            } catch (CirkleException cex) {
                return new AsyncTaskResult(cex);
            }
        }

        @Override
        protected void onPostExecute (AsyncTaskResult result){
            ResponseHandler.handleResponse(result, context, new IResponseHandler() {
                @Override
                public void handleSuccess(Object object) {
                    List<UserLocation> locations = (List<UserLocation>) object;
                    handleUserLocations(locations);
                }

                @Override
                public void handleBusinessException(CirkleBusinessException exception) {

                }

                @Override
                public void handleSystemException(CirkleSystemException exception) {
                    Toast.makeText(context, "Failed to load locations", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

}
