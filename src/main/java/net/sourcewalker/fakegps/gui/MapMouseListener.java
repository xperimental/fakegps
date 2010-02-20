package net.sourcewalker.fakegps.gui;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import net.sourcewalker.fakegps.data.GpsWaypoint;
import net.sourcewalker.fakegps.data.IDataModel;
import net.sourcewalker.fakegps.data.MapTool;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.mapviewer.GeoPosition;

/**
 * Listener class for map mouse interactions.
 * 
 * @author Xperimental
 */
public class MapMouseListener extends MouseAdapter {

    /**
     * Constant for maximum distance between mouse and waypoint for remove tool.
     */
    private static final double REMOVE_DISTANCE = 5;

    /**
     * Contains the data model to modify.
     */
    private IDataModel model;

    /**
     * Contains the map view control.
     */
    private JXMapKit map;

    /**
     * Create a new instance of this class working with the provided map and
     * model.
     * 
     * @param mapKit
     *            Map to use.
     * @param dataModel
     *            Model to modify.
     */
    public MapMouseListener(final JXMapKit mapKit, final IDataModel dataModel) {
        map = mapKit;
        model = dataModel;
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public final void mouseClicked(final MouseEvent e) {
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

}
