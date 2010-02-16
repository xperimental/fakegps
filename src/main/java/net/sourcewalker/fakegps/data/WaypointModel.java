package net.sourcewalker.fakegps.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.mapviewer.GeoPosition;

public class WaypointModel implements IDataModel {

    private static final Log LOGGER = LogFactory.getLog(WaypointModel.class);

    private List<GpsWaypoint> waypoints;
    private MapTool currentTool = MapTool.NULL;
    private List<MapToolListener> mapToolListeners;
    private List<ModelChangeListener> changeListeners;
    private IRoute currentRoute = IRoute.NULLROUTE;
    private GeoPosition currentRoutePosition = null;

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
     * net.sourcewalker.fakegps.data.IDataModel#removeToolListener(net.sourcewalker
     * .fakegps.data.MapToolListener)
     */
    @Override
    public final void removeToolListener(final MapToolListener listener) {
        mapToolListeners.remove(listener);
    }

    private void fireDataChanged() {
        for (ModelChangeListener listener : changeListeners) {
            listener.dataChanged();
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#addChangeListener(net.sourcewalker
     * .fakegps.data.ModelChangeListener)
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
    public GeoPosition getRoutePosition() {
        return currentRoutePosition;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IDataModel#notfyRouteEnded()
     */
    @Override
    public void notifyRouteEnded() {
        currentRoute = IRoute.NULLROUTE;
        currentRoutePosition = null;
        fireDataChanged();
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#notifyRouteStarted(net.sourcewalker
     * .fakegps.data.SimpleRoute)
     */
    @Override
    public void notifyRouteStarted(final IRoute route) {
        currentRoute = route;
        fireDataChanged();
    }

}
