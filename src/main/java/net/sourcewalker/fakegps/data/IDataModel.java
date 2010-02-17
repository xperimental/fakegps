package net.sourcewalker.fakegps.data;

import java.util.List;

import org.jdesktop.swingx.mapviewer.GeoPosition;

/**
 * This interface contains the standard data model to modify the application
 * data. It is implemented by a class containing the actual data.
 * 
 * @author Xperimental
 */
public interface IDataModel {

    /**
     * Add a new waypoint at the end of the simulated route.
     * 
     * @param wp
     *            Waypoint to append to route.
     */
    void addWaypoint(GpsWaypoint wp);

    /**
     * Get a list of all waypoints of the route.
     * 
     * @return List of waypoints.
     */
    List<GpsWaypoint> getWaypoints();

    /**
     * Remove a waypoint from the route.
     * 
     * @param wp
     *            Waypoint to remove.
     */
    void removeWaypoint(GpsWaypoint wp);

    /**
     * Remove all waypoints from the data model.
     */
    void clearWaypoints();

    /**
     * Returns true, if the waypoint is a normal point of the route (no start,
     * end or other special point). The method returns false, if the waypoint
     * isn't part of the route.
     * 
     * @param wp
     *            Waypoint to check.
     * @return True, if the waypoint is no special waypoint.
     */
    boolean isNormalPoint(GpsWaypoint wp);

    /**
     * Returns true, if the waypoint is the last point of the route.
     * 
     * @param wp
     *            Waypoint to check.
     * @return True, if the waypoint is at the end of the route.
     */
    boolean isEndPoint(GpsWaypoint wp);

    /**
     * Returns true, if the waypoint is the first point of the route.
     * 
     * @param wp
     *            Waypoint to check.
     * @return True, if the waypoint is at the start of the route.
     */
    boolean isStartPoint(GpsWaypoint wp);

    /**
     * Set a new tool to be used on the map.
     * 
     * @param newTool
     *            New tool to use.
     */
    void setCurrentTool(MapTool newTool);

    /**
     * Returns the current tool, that is used on the map.
     * 
     * @return Current map tool.
     */
    MapTool getCurrentTool();

    /**
     * Add a listener to the list of map tool listeners.
     * 
     * @param listener
     *            Listener to add.
     */
    void addToolListener(MapToolListener listener);

    /**
     * Remove a listener from the list of map tool listeners.
     * 
     * @param listener
     *            Listener to remove.
     */
    void removeToolListener(MapToolListener listener);

    /**
     * Add a listener to the list of change listeners.
     * 
     * @param listener
     *            Listener to add.
     */
    void addChangeListener(ModelChangeListener listener);

    /**
     * Remove a listener from the list of change listeners.
     * 
     * @param listener
     *            Listener to remove.
     */
    void removeChangeListener(ModelChangeListener listener);

    /**
     * Starts a new route controller.
     * 
     * @return New route controller.
     */
    IRoute startRoute();

    /**
     * Returns the current route controller, if there is one.
     * 
     * @return Current route controller or {@link IRoute#NULLROUTE} if there is
     *         none.
     */
    IRoute getRoute();

    /**
     * Notifies all route listeners, that there is a new location.
     * 
     * @param position
     *            Current location of route.
     */
    void notifyNewLocation(GeoPosition position);

    /**
     * Returns the current position of the route or <code>null</code> if there
     * is no route active.
     * 
     * @return Current route position or <code>null</code>.
     */
    GeoPosition getRoutePosition();

    /**
     * Notify all listeners that a new route controller has started.
     * 
     * @param route
     *            New route controller.
     */
    void notifyRouteStarted(IRoute route);

    /**
     * Notify all listeners that the route has stopped. This will also reset the
     * route position.
     */
    void notifyRouteEnded();

}
