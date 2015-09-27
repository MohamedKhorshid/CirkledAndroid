package com.augenblick.cirkle.location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.*;
import android.os.Process;

/**
 * Created by Mohamed Wagdy on 23-09-2015.
 */
public class LocationService extends Service {

    private LocationServiceHandler serviceHandler;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        serviceHandler = new LocationServiceHandler(thread.getLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);

        return START_STICKY;
    }

    private class LocationServiceHandler extends Handler{

        private Looper looper;

        public LocationServiceHandler(Looper looper) {
            this.looper = looper;
        }

        @Override
        public void handleMessage(Message msg) {
            LocationManager locationManager = (LocationManager) LocationService.this.getSystemService(Context.LOCATION_SERVICE);

            AbstractLocationListener locationListener = new NetworkLocationListener(LocationService.this);

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
    }
}
