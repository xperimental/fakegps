package net.sourcewalker.fakegps;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Set;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointPainter;
import org.jdesktop.swingx.mapviewer.WaypointRenderer;

@SuppressWarnings("unchecked")
public class GpsPainter extends WaypointPainter {

    public GpsPainter(Set waypoints) {
        super();

        setRenderer(new GpsRenderer());
        setWaypoints(waypoints);
    }

    public class GpsRenderer implements WaypointRenderer {

        /*
         * (non-Javadoc)
         * @see
         * org.jdesktop.swingx.mapviewer.WaypointRenderer#paintWaypoint(java
         * .awt.Graphics2D, org.jdesktop.swingx.JXMapViewer,
         * org.jdesktop.swingx.mapviewer.Waypoint)
         */
        public boolean paintWaypoint(Graphics2D g, JXMapViewer map,
                Waypoint wp) {
            g.setPaint(new Color(0, 0, 255, 200));
            Polygon poly = new Polygon();
            poly.addPoint(0, 0);
            poly.addPoint(11, 11);
            poly.addPoint(-11, 11);
            g.fill(poly);
            return false;
        }

    }

}
