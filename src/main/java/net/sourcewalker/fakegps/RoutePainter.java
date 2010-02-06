package net.sourcewalker.fakegps;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.painter.Painter;

public class RoutePainter implements Painter<JXMapViewer> {

    private IDataModel model;

    public RoutePainter(IDataModel dataModel) {
        model = dataModel;
    }

    /*
     * (non-Javadoc)
     * @see org.jdesktop.swingx.painter.Painter#paint(java.awt.Graphics2D,
     * java.lang.Object, int, int)
     */
    @Override
    public void paint(Graphics2D g, JXMapViewer map, int arg2, int arg3) {
        g.setPaint(new Color(0, 0, 255, 200));
        for (GpsWaypoint wp : model.getWaypoints()) {
            GeoPosition position = wp.getPosition();
            Point2D mapPoint = map.convertGeoPositionToPoint(position);
            if (g.getClip().contains(mapPoint)) {
                Polygon triangle = new Polygon();
                triangle.addPoint(0, 0);
                triangle.addPoint(11, 11);
                triangle.addPoint(-11, 11);
                triangle
                        .translate((int) mapPoint.getX(), (int) mapPoint.getY());
                g.fill(triangle);
            }
        }
    }

}
