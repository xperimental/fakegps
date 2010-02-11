package net.sourcewalker.fakegps.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WaypointModel implements IDataModel {

    private static final Log logger = LogFactory.getLog(WaypointModel.class);

    private List<GpsWaypoint> waypoints;
    private MapTool currentTool = MapTool.NULL;
    private List<MapToolListener> mapToolListeners;
    private List<ModelChangeListener> changeListeners;

    public WaypointModel() {
        waypoints = new ArrayList<GpsWaypoint>();
        mapToolListeners = new ArrayList<MapToolListener>();
        changeListeners = new ArrayList<ModelChangeListener>();
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.IDataModel#addWaypoint(net.sourcewalker.fakegps
     * .GpsWaypoint)
     */
    public void addWaypoint(GpsWaypoint wp) {
        waypoints.add(wp);
        fireDataChanged();
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
        for (MapToolListener listener : mapToolListeners) {
            listener.toolChanged(currentTool);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#addToolListener(net.sourcewalker
     * .fakegps.data.MapToolListener)
     */
    @Override
    public void addToolListener(MapToolListener listener) {
        mapToolListeners.add(listener);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#removeToolListener(net.sourcewalker
     * .fakegps.data.MapToolListener)
     */
    @Override
    public void removeToolListener(MapToolListener listener) {
        mapToolListeners.remove(listener);
    }

    private void fireDataChanged() {
        for (ModelChangeListener listener : changeListeners) {
            listener.dataChanged();
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.data.IDataModel#addChangeListener(net.sourcewalker
     * .fakegps.data.ModelChangeListener)
     */
    @Override
    public void addChangeListener(ModelChangeListener listener) {
        changeListeners.add(listener);
    }

    /*
     * (non-Javadoc)
     * @seenet.sourcewalker.fakegps.data.IDataModel#removeChangeListener(net.
     * sourcewalker.fakegps.data.ModelChangeListener)
     */
    @Override
    public void removeChangeListener(ModelChangeListener listener) {
        changeListeners.remove(listener);
    }

}
