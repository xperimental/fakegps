package net.sourcewalker.fakegps.data;

import java.util.List;

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

}
