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

public class RoutePainter implements Painter<JXMapViewer> {

    private static final Log logger = LogFactory.getLog(RoutePainter.class);

    private static final int POINT_RADIUS = 5;

    private IDataModel model;
    private Image startFlag;
    private Image endFlag;

    public RoutePainter(IDataModel dataModel) {
        model = dataModel;

        // Load images
        startFlag = loadImage("startFlag.png");
        endFlag = loadImage("endFlag.png");
    }

    /**
     * @param startFlag2
     * @param string
     */
    private Image loadImage(String key) {
        InputStream input = getClass().getResourceAsStream(key);
        BufferedImage image = null;
        try {
            image = ImageIO.read(input);
        } catch (IOException e) {
            logger.error("Can't load flag image: " + e);
        }
        return image;
    }

    /*
     * (non-Javadoc)
     * @see org.jdesktop.swingx.painter.Painter#paint(java.awt.Graphics2D,
     * java.lang.Object, int, int)
     */
    @Override
    public void paint(Graphics2D g, JXMapViewer map, int arg2, int arg3) {
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
            g.setPaint(new Color(0, 0, 0, 255));
            Point2D start = linePoints.remove(0);
            while (linePoints.size() > 0) {
                Point2D end = linePoints.remove(0);
                g.drawLine((int) start.getX(), (int) start.getY(), (int) end
                        .getX(), (int) end.getY());
                start = end;
            }
        }
    }

    /**
     * @param g
     * @param mapPoint
     */
    private void paintStartPoint(Graphics2D g, Point2D point) {
        drawFlag(g, point, startFlag);
    }

    /**
     * @param g
     * @param point
     * @param flag
     */
    private void drawFlag(Graphics2D g, Point2D point, Image flag) {
        g.drawImage(flag, (int) point.getX() - (flag.getWidth(null) / 2),
                (int) point.getY() - flag.getHeight(null), null);
    }

    /**
     * @param g
     * @param mapPoint
     */
    private void paintEndPoint(Graphics2D g, Point2D point) {
        drawFlag(g, point, endFlag);
    }

    /**
     * @param g
     * @param mapPoint
     */
    private void paintNormalPoint(Graphics2D g, Point2D point) {
        g.setPaint(new Color(0, 0, 255, 200));
        g.fillOval((int) (point.getX() - POINT_RADIUS),
                (int) (point.getY() - POINT_RADIUS), POINT_RADIUS * 2,
                POINT_RADIUS * 2);
    }

}
