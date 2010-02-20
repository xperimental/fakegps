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
     * Time between position updates.
     */
    private static final int TICK_WAIT = 3000;

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
        speed = dataModel.getRouteSpeed();
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
        model.notifyRouteStateChange();
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
        model.notifyRouteStateChange();
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
                for (int legStart = 0;
                     legStart < waypointList.size() - 1;
                     legStart++) {
                    GpsWaypoint start = waypointList.get(legStart);
                    GpsWaypoint end = waypointList.get(legStart + 1);
                    currentPosition = new GeoPosition(start.getLatitude(),
                            start.getLongitude());
                    while (state != RouteState.STOPPED && !legEndReached(end)) {
                        fireNewLocation(currentPosition);
                        GeoPosition speedVector = getSpeedVector(start, end);
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

        /**
         * Returns true, if the end of a segment is reached.
         * 
         * @param end
         *            Endpoint of segment.
         * @return True, if end of segment is reached.
         */
        private boolean legEndReached(final GpsWaypoint end) {
            if (distance(currentPosition, end.getPosition()) < (2 * speed)) {
                return true;
            }
            return false;
        }

        /**
         * Calculates the distance between two points.
         * 
         * @param a
         *            One point.
         * @param b
         *            Another point.
         * @return Distance between the two points.
         */
        private double distance(final GeoPosition a, final GeoPosition b) {
            double latDiff = b.getLatitude() - a.getLatitude();
            double lonDiff = b.getLongitude() - a.getLongitude();
            return Math.sqrt(Math.pow(latDiff, 2) + Math.pow(lonDiff, 2));
        }

        /**
         * Calculates the speed vector pointing from the start to the endpoint.
         * 
         * @param start
         *            Startpoint of segment.
         * @param end
         *            Endpoint of segment.
         * @return Speed vector pointing from start to end.
         */
        private GeoPosition getSpeedVector(final GpsWaypoint start,
                final GpsWaypoint end) {
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
    public final void fireNewLocation(final GeoPosition position) {
        model.notifyNewLocation(position);
    }

    /**
     * Notifies the model listeners, that this controller has started.
     */
    public final void fireRouteStarted() {
        model.notifyRouteStarted(this);
    }

    /**
     * Notifies the model listeners, that the route has ended.
     */
    public final void fireRouteEnded() {
        model.notifyRouteEnded();
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IRoute#getState()
     */
    @Override
    public final RouteState getState() {
        return state;
    }

}
