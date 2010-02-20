package net.sourcewalker.fakegps.data;

/**
 * The state of the route controller.
 * 
 * @author Xperimental
 */
public enum RouteState {
    /**
     * Route controller is running. Continuous position updates are expected.
     */
    RUNNING,
    /**
     * The route controller is paused. No position updates are generated but the
     * current position is held until the controller is unpaused.
     */
    PAUSED,
    /**
     * Route controller is stopped. Expect an event notifying the end of the
     * route immediately.
     */
    STOPPED
}
