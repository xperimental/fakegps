package net.sourcewalker.fakegps.gui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import net.sourcewalker.fakegps.data.GpsWaypoint;
import net.sourcewalker.fakegps.data.IDataModel;
import net.sourcewalker.fakegps.data.MapTool;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.mapviewer.GeoPosition;

public class MapMouseListener implements MouseListener {

    private static final double REMOVE_DISTANCE = 5;

    private IDataModel model;
    private JXMapKit map;

    public MapMouseListener(JXMapKit mapKit, IDataModel dataModel) {
        map = mapKit;
        model = dataModel;
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Point2D clickPoint = new Point(e.getX(), e.getY());
        switch (model.getCurrentTool()) {
        case ADDPOINT:
            GeoPosition geoPoint = map.getMainMap().convertPointToGeoPosition(
                    clickPoint);
            GpsWaypoint newWp = new GpsWaypoint(geoPoint);
            model.addWaypoint(newWp);
            model.setCurrentTool(MapTool.ADDPOINT);
            break;
        case REMOVEPOINT:
            for (GpsWaypoint wp : model.getWaypoints()) {
                Point2D wpScreen = map.getMainMap().convertGeoPositionToPoint(
                        wp.getPosition());
                if (clickPoint.distance(wpScreen) < REMOVE_DISTANCE) {
                    model.removeWaypoint(wp);
                    break;
                }
            }
            break;
        default:
            break;
        }
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see
     * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}
