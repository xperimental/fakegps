package net.sourcewalker.fakegps.data;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WaypointModel implements ListModel, IDataModel {

    private static final Log logger = LogFactory.getLog(WaypointModel.class);

    private List<GpsWaypoint> waypoints;
    private List<ListDataListener> listeners;
    private MapTool currentTool = MapTool.NULL;
    private List<MapToolListener> mapToolListeners;

    public WaypointModel() {
        waypoints = new ArrayList<GpsWaypoint>();
        listeners = new ArrayList<ListDataListener>();
        mapToolListeners = new ArrayList<MapToolListener>();
    }

    /*
     * (non-Javadoc)
     * @seejavax.swing.ListModel#addListDataListener(javax.swing.event.
     * ListDataListener)
     */
    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.ListModel#getElementAt(int)
     */
    @Override
    public Object getElementAt(int index) {
        return waypoints.get(index);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.ListModel#getSize()
     */
    @Override
    public int getSize() {
        return waypoints.size();
    }

    /*
     * (non-Javadoc)
     * @seejavax.swing.ListModel#removeListDataListener(javax.swing.event.
     * ListDataListener)
     */
    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.IDataModel#addWaypoint(net.sourcewalker.fakegps
     * .GpsWaypoint)
     */
    public void addWaypoint(GpsWaypoint wp) {
        waypoints.add(wp);
        itemAdded(waypoints.size() - 1);
    }

    private void itemAdded(int index) {
        ListDataEvent evt = new ListDataEvent(this,
                ListDataEvent.INTERVAL_ADDED, index, index);
        for (ListDataListener listener : listeners) {
            listener.intervalAdded(evt);
        }
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.IDataModel#getWaypoints()
     */
    @Override
    public List<GpsWaypoint> getWaypoints() {
        return waypoints;
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#isEndPoint(net.sourcewalker.
     * fakegps.data.GpsWaypoint)
     */
    @Override
    public boolean isEndPoint(GpsWaypoint wp) {
        if (waypoints.size() == 0)
            return false;
        return waypoints.get(waypoints.size() - 1).equals(wp);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#isNormalPoint(net.sourcewalker
     * .fakegps.data.GpsWaypoint)
     */
    @Override
    public boolean isNormalPoint(GpsWaypoint wp) {
        if (waypoints.size() == 0)
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#isStartPoint(net.sourcewalker
     * .fakegps.data.GpsWaypoint)
     */
    @Override
    public boolean isStartPoint(GpsWaypoint wp) {
        if (waypoints.size() == 0)
            return false;
        return waypoints.get(0).equals(wp);
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IDataModel#getCurrentTool()
     */
    @Override
    public MapTool getCurrentTool() {
        return currentTool;
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#setCurrentTool(net.sourcewalker
     * .fakegps.data.MapTool)
     */
    @Override
    public void setCurrentTool(MapTool newTool) {
        currentTool = newTool;
        fireToolChanged();
    }

    private void fireToolChanged() {
        logger.debug("New map tool: " + currentTool);
        for (MapToolListener listener: mapToolListeners) {
            listener.toolChanged(currentTool);
        }
    }

    /* (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IDataModel#addToolListener(net.sourcewalker.fakegps.data.MapToolListener)
     */
    @Override
    public void addToolListener(MapToolListener listener) {
        mapToolListeners.add(listener);
    }

    /* (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IDataModel#removeToolListener(net.sourcewalker.fakegps.data.MapToolListener)
     */
    @Override
    public void removeToolListener(MapToolListener listener) {
        mapToolListeners.remove(listener);
    }

}
