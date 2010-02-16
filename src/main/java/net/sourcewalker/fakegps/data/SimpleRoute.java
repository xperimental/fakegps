package net.sourcewalker.fakegps.data;

import java.util.ArrayList;
import java.util.List;

import org.jdesktop.swingx.mapviewer.GeoPosition;

/**
 * The standard route controller for FakeGPS.
 * 
 * @author Xperimental
 */
public class SimpleRoute implements IRoute {

    /**
     * Default movement speed for this controller.
     */
    private static final double DEFAULT_SPEED = 0.001;

    private static final int TICK_WAIT = 100;

    /**
     * Data model used for events.
     */
    private IDataModel model;

    /**
     * Contains the list of waypoints the route controller uses.
     */
    private List<GpsWaypoint> waypointList;

    /**
     * Contains the current movement speed in deg/s.
     */
    private double speed;

    /**
     * Contains the current state of the worker.
     */
    private RouteState state;

    /**
     * Contains the worker thread of this controller.
     */
    private RouteThread worker;

    /**
     * Create a new route controller for the list of waypoints.
     * 
     * @param dataModel
     *            Model to use for events.
     * @param waypoints
     *            Waypoints to use for route.
     */
    public SimpleRoute(final IDataModel dataModel,
            final List<GpsWaypoint> waypoints) {
        model = dataModel;
        waypointList = new ArrayList<GpsWaypoint>(waypoints);
        speed = DEFAULT_SPEED;
        state = RouteState.RUNNING;
        worker = new RouteThread();
        worker.start();
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IRoute#isPaused()
     */
    @Override
    public final boolean isPaused() {
        return state == RouteState.PAUSED;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IRoute#pause()
     */
    @Override
    public final void pause() {
        state = RouteState.PAUSED;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IRoute#stop()
     */
    @Override
    public final void stop() {
        state = RouteState.STOPPED;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IRoute#unpause()
     */
    @Override
    public final void unpause() {
        state = RouteState.RUNNING;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IRoute#getSpeed()
     */
    @Override
    public final double getSpeed() {
        return speed;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IRoute#setSpeed(double)
     */
    @Override
    public final void setSpeed(final double newSpeed) {
        speed = newSpeed;
    }

    /**
     * This class contains the worker implementation for this route controller.
     * 
     * @author Xperimental
     */
    private class RouteThread extends Thread {

        /**
         * Contains the current position of the route controller.
         */
        private GeoPosition currentPosition;

        /**
         * Creates a new worker thread.
         */
        protected RouteThread() {
            super("RouteWorker");
            setDaemon(true);
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            if (waypointList.size() > 1) {
                fireRouteStarted();
                for (int legStart = 0; legStart < waypointList.size() - 1; legStart++) {
                    GpsWaypoint start = waypointList.get(legStart);
                    GpsWaypoint end = waypointList.get(legStart + 1);
                    currentPosition = new GeoPosition(start.getLatitude(),
                            start.getLongitude());
                    GeoPosition speedVector = getSpeedVector(start, end);
                    while (state != RouteState.STOPPED && !legEndReached(end)) {
                        fireNewLocation(currentPosition);
                        currentPosition = new GeoPosition(currentPosition
                                .getLatitude()
                                + speedVector.getLatitude(), currentPosition
                                .getLongitude()
                                + speedVector.getLongitude());
                        try {
                            do {
                                Thread.sleep(TICK_WAIT);
                            } while (state == RouteState.PAUSED);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
            fireRouteEnded();
        }

        private boolean legEndReached(GpsWaypoint end) {
            if (distance(currentPosition, end.getPosition()) < (2 * speed)) {
                return true;
            }
            return false;
        }

        private double distance(GeoPosition a, GeoPosition b) {
            double latDiff = b.getLatitude() - a.getLatitude();
            double lonDiff = b.getLongitude() - a.getLongitude();
            return Math.sqrt(Math.pow(latDiff, 2) + Math.pow(lonDiff, 2));
        }

        private GeoPosition getSpeedVector(GpsWaypoint start, GpsWaypoint end) {
            double latDiff = end.getLatitude() - start.getLatitude();
            double lonDiff = end.getLongitude() - start.getLongitude();
            double length = Math.sqrt(Math.pow(latDiff, 2)
                    + Math.pow(lonDiff, 2));
            return new GeoPosition(latDiff / length * speed, lonDiff / length
                    * speed);
        }

    }

    /**
     * Notifies the listeners of the model, that the route controller has
     * advanced to a new location.
     * 
     * @param position
     *            Current location of route.
     */
    public void fireNewLocation(GeoPosition position) {
        model.notifyNewLocation(position);
    }

    /**
     * 
     */
    public void fireRouteStarted() {
        model.notifyRouteStarted(this);
    }

    /**
     * 
     */
    public void fireRouteEnded() {
        model.notifyRouteEnded();
    }

}
