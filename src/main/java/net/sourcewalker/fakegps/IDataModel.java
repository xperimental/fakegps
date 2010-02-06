package net.sourcewalker.fakegps;

import java.util.List;

public interface IDataModel {

    public abstract void addWaypoint(GpsWaypoint wp);

    public abstract List<GpsWaypoint> getWaypoints();

}
