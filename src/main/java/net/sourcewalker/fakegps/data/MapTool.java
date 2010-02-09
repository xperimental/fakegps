package net.sourcewalker.fakegps.data;

/**
 * This enumeration contains all the possible map tools that can be used. A
 * special tool is the <code>NULL</code> tool, as it deliberately does nothing.
 * 
 * @author Xperimental
 */
public enum MapTool {
    /**
     * Does nothing.
     */
    NULL,
    /**
     * Appends a new waypoint to the route when the user clicks on the map.
     */
    ADDPOINT
}
