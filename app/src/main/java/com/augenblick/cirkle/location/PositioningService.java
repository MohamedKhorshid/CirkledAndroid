package com.augenblick.cirkle.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by Mohamed Wagdy on 27-09-2015.
 */
public class PositioningService {

    public Location getLastKnownLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        return lastKnownLocation;
    }
}
