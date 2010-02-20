package net.sourcewalker.fakegps.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.mapviewer.GeoPosition;

/**
 * Default data model and controller implementation.
 * 
 * @author Xperimental
 */
public class WaypointModel implements IDataModel {

    /**
     * Logger for this class.
     */
    private static final Log LOGGER = LogFactory.getLog(WaypointModel.class);

    /**
     * Default movement speed for this controller (deg/s).
     */
    private static final double DEFAULT_SPEED = 0.00001;

    /**
     * List containing the current waypoints.
     */
    private List<GpsWaypoint> waypoints;

    /**
     * Contains the currently active map tool.
     */
    private MapTool currentTool = MapTool.NULL;

    /**
     * Contains a list of map tool listeners.
     */
    private List<MapToolListener> mapToolListeners;

    /**
     * Contains a list of data change listeners.
     */
    private List<ModelChangeListener> changeListeners;

    /**
     * Contains the current route controller or {@link IRoute#NULLROUTE} if
     * there is none.
     */
    private IRoute currentRoute = IRoute.NULLROUTE;

    /**
     * Contains the current route position or <code>null</code> if there is no
     * route.
     */
    private GeoPosition currentRoutePosition = null;

    /**
     * Current route speed (deg/s).
     */
    private double routeSpeed = DEFAULT_SPEED;

    /**
     * Create a new empty data model.
     */
    public WaypointModel() {
        waypoints = new ArrayList<GpsWaypoint>();
        mapToolListeners = new ArrayList<MapToolListener>();
        changeListeners = new ArrayList<ModelChangeListener>();
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.IDataModel#addWaypoint(net.sourcewalker.fakegps
     * .GpsWaypoint)
     */
    @Override
    public final void addWaypoint(final GpsWaypoint wp) {
        waypoints.add(wp);
        fireDataChanged();
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.IDataModel#getWaypoints()
     */
    @Override
    public final List<GpsWaypoint> getWaypoints() {
        return waypoints;
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#isEndPoint(net.sourcewalker.
     * fakegps.data.GpsWaypoint)
     */
    @Override
    public final boolean isEndPoint(final GpsWaypoint wp) {
        if (waypoints.size() == 0) {
            return false;
        }
        return waypoints.get(waypoints.size() - 1).equals(wp);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#isNormalPoint(net.sourcewalker
     * .fakegps.data.GpsWaypoint)
     */
    @Override
    public final boolean isNormalPoint(final GpsWaypoint wp) {
        if (waypoints.size() == 0) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#isStartPoint(net.sourcewalker
     * .fakegps.data.GpsWaypoint)
     */
    @Override
    public final boolean isStartPoint(final GpsWaypoint wp) {
        if (waypoints.size() == 0) {
            return false;
        }
        return waypoints.get(0).equals(wp);
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IDataModel#getCurrentTool()
     */
    @Override
    public final MapTool getCurrentTool() {
        return currentTool;
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#setCurrentTool(net.sourcewalker
     * .fakegps.data.MapTool)
     */
    @Override
    public final void setCurrentTool(final MapTool newTool) {
        currentTool = newTool;
        fireToolChanged();
    }

    /**
     * Notifies all map tool listeners, that a new tool has been selected.
     */
    private void fireToolChanged() {
        LOGGER.debug("New map tool: " + currentTool);
        for (MapToolListener listener : mapToolListeners) {
            listener.toolChanged(currentTool);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#addToolListener(net.sourcewalker
     * .fakegps.data.MapToolListener)
     */
    @Override
    public final void addToolListener(final MapToolListener listener) {
        mapToolListeners.add(listener);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#removeToolListener
     * (net.sourcewalker.fakegps.data.MapToolListener)
     */
    @Override
    public final void removeToolListener(final MapToolListener listener) {
        mapToolListeners.remove(listener);
    }

    /**
     * Notifies all data listeners that some property has changed.
     */
    private void fireDataChanged() {
        for (ModelChangeListener listener : changeListeners) {
            listener.dataChanged();
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#addChangeListener
     * (net.sourcewalker.fakegps.data.ModelChangeListener)
     */
    @Override
    public final void addChangeListener(final ModelChangeListener listener) {
        changeListeners.add(listener);
    }

    /*
     * (non-Javadoc)
     * @seenet.sourcewalker.fakegps.data.IDataModel#removeChangeListener(net.
     * sourcewalker.fakegps.data.ModelChangeListener)
     */
    @Override
    public final void removeChangeListener(final ModelChangeListener listener) {
        changeListeners.remove(listener);
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IDataModel#clearWaypoints()
     */
    @Override
    public final void clearWaypoints() {
        waypoints.clear();
        fireDataChanged();
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#removeWaypoint(net.sourcewalker
     * .fakegps.data.GpsWaypoint)
     */
    @Override
    public final void removeWaypoint(final GpsWaypoint wp) {
        if (waypoints.remove(wp)) {
            fireDataChanged();
        }
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IDataModel#getRoute()
     */
    @Override
    public final IRoute getRoute() {
        return currentRoute;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IDataModel#startRoute()
     */
    @Override
    public final IRoute startRoute() {
        return new SimpleRoute(this, getWaypoints());
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#notifyNewLocation(org.jdesktop
     * .swingx.mapviewer.GeoPosition)
     */
    @Override
    public final void notifyNewLocation(final GeoPosition position) {
        currentRoutePosition = position;
        fireDataChanged();
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IDataModel#getRoutePosition()
     */
    @Override
    public final GeoPosition getRoutePosition() {
        return currentRoutePosition;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IDataModel#notfyRouteEnded()
     */
    @Override
    public final void notifyRouteEnded() {
        currentRoute = IRoute.NULLROUTE;
        currentRoutePosition = null;
        fireDataChanged();
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#notifyRouteStarted
     * (net.sourcewalker.fakegps.data.SimpleRoute)
     */
    @Override
    public final void notifyRouteStarted(final IRoute route) {
        currentRoute = route;
        fireDataChanged();
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IDataModel#setRouteSpeed(double)
     */
    @Override
    public final void setRouteSpeed(final double speed) {
        routeSpeed = speed;
        if (currentRoute != IRoute.NULLROUTE) {
            currentRoute.setSpeed(speed);
        }
        fireDataChanged();
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IDataModel#notifyRouteStateChange()
     */
    @Override
    public final void notifyRouteStateChange() {
        fireDataChanged();
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IDataModel#getRouteSpeed()
     */
    @Override
    public final double getRouteSpeed() {
        return routeSpeed;
    }

}
