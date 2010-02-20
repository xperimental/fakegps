package net.sourcewalker.fakegps.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.sourcewalker.fakegps.data.GpsWaypoint;
import net.sourcewalker.fakegps.data.IDataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.painter.Painter;

/**
 * Default renderer for waypoint and route display.
 * 
 * @author Xperimental
 */
public class RoutePainter implements Painter<JXMapViewer> {

    /**
     * Color of the line connecting the waypoints.
     */
    private static final Color LINE_COLOR = new Color(0, 0, 0, 255);

    /**
     * Color of the route position marker.
     */
    private static final Color ROUTE_COLOR = new Color(255, 0, 0, 200);

    /**
     * Color of normal waypoint markers.
     */
    private static final Color WAYPOINT_COLOR = new Color(0, 0, 255, 200);

    /**
     * Logger for this class.
     */
    private static final Log LOGGER = LogFactory.getLog(RoutePainter.class);

    /**
     * Radius of waypoint points.
     */
    private static final int POINT_RADIUS = 5;

    /**
     * Radius of route point.
     */
    private static final int ROUTE_RADIUS = 8;

    /**
     * Contains the data model that is rendered.
     */
    private IDataModel model;

    /**
     * Contains the image used for the start point.
     */
    private Image startFlag;

    /**
     * Contains the image used for the end point.
     */
    private Image endFlag;

    /**
     * Create a new default renderer for the data model.
     * 
     * @param dataModel
     *            Data model to render.
     */
    public RoutePainter(final IDataModel dataModel) {
        model = dataModel;

        // Load images
        startFlag = loadImage("startFlag.png");
        endFlag = loadImage("endFlag.png");
    }

    /**
     * Load the image from the resources.
     * 
     * @param key
     *            Key of image to load.
     * @return Image object of loaded image.
     */
    private Image loadImage(final String key) {
        InputStream input = getClass().getResourceAsStream(key);
        BufferedImage image = null;
        try {
            image = ImageIO.read(input);
        } catch (IOException e) {
            LOGGER.error("Can't load flag image: " + e);
        }
        return image;
    }

    /*
     * (non-Javadoc)
     * @see org.jdesktop.swingx.painter.Painter#paint(java.awt.Graphics2D,
     * java.lang.Object, int, int)
     */
    @Override
    public final void paint(final Graphics2D g, final JXMapViewer map,
            final int arg2, final int arg3) {
        List<Point2D> linePoints = new ArrayList<Point2D>();

        for (GpsWaypoint wp : model.getWaypoints()) {
            GeoPosition position = wp.getPosition();
            Point2D mapPoint = map.convertGeoPositionToPoint(position);
            linePoints.add(mapPoint);
            if (g.getClip().contains(mapPoint)) {
                if (model.isStartPoint(wp)) {
                    paintStartPoint(g, mapPoint);
                } else if (model.isEndPoint(wp)) {
                    paintEndPoint(g, mapPoint);
                } else {
                    paintNormalPoint(g, mapPoint);
                }
            }
        }

        if (linePoints.size() > 1) {
            g.setPaint(LINE_COLOR);
            Point2D start = linePoints.remove(0);
            while (linePoints.size() > 0) {
                Point2D end = linePoints.remove(0);
                g.drawLine((int) start.getX(), (int) start.getY(), (int) end
                        .getX(), (int) end.getY());
                start = end;
            }
        }

        GeoPosition routePosition = model.getRoutePosition();
        if (routePosition != null) {
            Point2D routeMap = map.convertGeoPositionToPoint(routePosition);
            if (g.getClip().contains(routeMap)) {
                paintRoutePosition(g, routeMap);
            }
        }
    }

    /**
     * Paint the current route position.
     * 
     * @param g
     *            Context to paint to.
     * @param routeMap
     *            Point to paint marker at.
     */
    private void paintRoutePosition(final Graphics2D g,
            final Point2D routeMap) {
        g.setPaint(ROUTE_COLOR);
        g.fillOval((int) (routeMap.getX() - ROUTE_RADIUS), (int) (routeMap
                .getY() - ROUTE_RADIUS), ROUTE_RADIUS * 2, ROUTE_RADIUS * 2);
    }

    /**
     * Paint the start point.
     * 
     * @param g
     *            Context to paint to.
     * @param point
     *            Point to paint marker at.
     */
    private void paintStartPoint(final Graphics2D g, final Point2D point) {
        drawFlag(g, point, startFlag);
    }

    /**
     * Paint the image at the given position.
     * 
     * @param g
     *            Context to paint to.
     * @param point
     *            Point to paint image at.
     * @param flag
     *            Image to paint.
     */
    private void drawFlag(final Graphics2D g, final Point2D point,
            final Image flag) {
        g.drawImage(flag, (int) point.getX() - (flag.getWidth(null) / 2),
                (int) point.getY() - flag.getHeight(null), null);
    }

    /**
     * Paint the end point.
     * 
     * @param g
     *            Context to paint to.
     * @param point
     *            Point to paint marker at.
     */
    private void paintEndPoint(final Graphics2D g, final Point2D point) {
        drawFlag(g, point, endFlag);
    }

    /**
     * Paint a normal waypoint marker.
     * 
     * @param g
     *            Context to paint to.
     * @param point
     *            Point to paint marker at.
     */
    private void paintNormalPoint(final Graphics2D g, final Point2D point) {
        g.setPaint(WAYPOINT_COLOR);
        g.fillOval((int) (point.getX() - POINT_RADIUS),
                (int) (point.getY() - POINT_RADIUS), POINT_RADIUS * 2,
                POINT_RADIUS * 2);
    }

}
