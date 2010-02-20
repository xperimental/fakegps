package net.sourcewalker.fakegps.data;

/**
 * This interface describes the methods of a route controller. A route
 * controller should take a list of waypoints and create a stream of points to
 * be sent to a consumer.
 * 
 * @author Xperimental
 */
public interface IRoute {

    /**
     * Signifies the absence of a route controller.
     */
    IRoute NULLROUTE = new NullRoute();

    /**
     * Stops the route controller.
     */
    void stop();

    /**
     * Returns true, if the route is currently paused.
     * 
     * @return True, if the route is paused.
     */
    boolean isPaused();

    /**
     * Tell the route controller to pause the route.
     */
    void pause();

    /**
     * Tell the route controller to unpause the route.
     */
    void unpause();

    /**
     * Set a new movement speed for the controller (deg/s).
     * 
     * @param newSpeed
     *            New movement speed (deg/s).
     */
    void setSpeed(double newSpeed);

    /**
     * Returns the current movement speed of the controller (deg/s).
     * 
     * @return Current movement speed in deg/s.
     */
    double getSpeed();

    /**
     * Returns the current state of the route controller.
     * 
     * @return State of the route controller.
     */
    RouteState getState();

}
