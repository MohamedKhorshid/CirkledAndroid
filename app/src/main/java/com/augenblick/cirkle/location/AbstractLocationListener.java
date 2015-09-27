package com.augenblick.cirkle.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.augenblick.cirkle.activity.AsyncTaskResult;
import com.augenblick.cirkle.activity.IResponseHandler;
import com.augenblick.cirkle.activity.ResponseHandler;
import com.augenblick.cirkle.exception.CirkleBusinessException;
import com.augenblick.cirkle.exception.CirkleException;
import com.augenblick.cirkle.exception.CirkleSystemException;

import java.util.logging.Logger;

/**
 * Created by Mohamed Wagdy on 24-09-2015.
 */
public abstract class AbstractLocationListener implements LocationListener {

    private static final String LOG_TAG = "LOCATION_SERVICE";

    private Context context;

    public AbstractLocationListener(Context context) {
        this.context = context;
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i(LOG_TAG, "Location changed: " + location.getLatitude() + ", " + location.getLongitude());

        /*try {
            new com.augenblick.cirkle.service.LocationService(context).addLocation(location.getLongitude(), location.getLatitude());
        } catch (CirkleException e) {
            Toast.makeText(context, "Failed to sync location", Toast.LENGTH_SHORT).show();
        }*/

        new SendLocationTask(context, location).execute();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class SendLocationTask extends AsyncTask<String, Void, AsyncTaskResult>{

        private Context context;
        private Location location;

        public SendLocationTask(Context context, Location location) {
            this.context = context;
            this.location = location;
        }

        @Override
        protected AsyncTaskResult doInBackground(String... params) {
            try {
                new com.augenblick.cirkle.service.LocationService(context).addLocation(location.getLongitude(), location.getLatitude());
                return new AsyncTaskResult();
            } catch (CirkleException e) {
                Toast.makeText(context, "Failed to sync location", Toast.LENGTH_SHORT).show();
                return new AsyncTaskResult(e);
            }
        }

        @Override
        protected void onPostExecute(AsyncTaskResult result) {
            ResponseHandler.handleResponse(result, context, new IResponseHandler() {

                @Override
                public void handleSuccess(Object object) {

                }

                @Override
                public void handleBusinessException(CirkleBusinessException exception) {
                    Log.e(LOG_TAG,"Failed to sync location", exception);
                }

                @Override
                public void handleSystemException(CirkleSystemException exception) {
                    Log.e(LOG_TAG,"Failed to sync location",exception);
                }
            });

        }
    }


}
