package net.sourcewalker.fakegps;

import org.jdesktop.swingx.mapviewer.Waypoint;

public class GpsWaypoint extends Waypoint {

    public GpsWaypoint(double latitude, double longitude) {
        super(latitude, longitude);
    }

    public double getLatitude() {
        return getPosition().getLatitude();
    }

    public double getLongitude() {
        // TODO Auto-generated method stub
        return getPosition().getLongitude();
    }

}