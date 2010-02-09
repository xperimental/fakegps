package net.sourcewalker.fakegps.data;

import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.Waypoint;

/**
 * A simple waypoint of the route to be simulated for the Android device.
 * 
 * @author Xperimental
 */
public class GpsWaypoint extends Waypoint {

    /**
     * Creates a new waypoint at the specified geolocation.
     * 
     * @param latitude
     *            Latitude of waypoint (WGS-84).
     * @param longitude
     *            Longitude of waypoint (WGS-84).
     */
    public GpsWaypoint(final double latitude, final double longitude) {
        super(latitude, longitude);
    }

    /**
     * Creates a new waypoint at the specified geolocation.
     * 
     * @param geoPosition
     *            Position of the waypoint.
     */
    public GpsWaypoint(final GeoPosition geoPosition) {
        super(geoPosition);
    }

    /**
     * Returns the latitude of the waypoint.
     * 
     * @return Latitude of waypoint (WGS-84).
     */
    public final double getLatitude() {
        return getPosition().getLatitude();
    }

    /**
     * Returns the longitude of the waypoint.
     * 
     * @return Longitude of waypoint (WGS-84).
     */
    public final double getLongitude() {
        return getPosition().getLongitude();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        return String.format("Point(%f, %f)", getLatitude(), getLongitude());
    }

}
