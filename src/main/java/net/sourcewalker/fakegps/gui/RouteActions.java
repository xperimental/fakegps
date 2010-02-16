package net.sourcewalker.fakegps.gui;

import net.sourcewalker.fakegps.data.IDataModel;
import net.sourcewalker.fakegps.data.IRoute;

import org.jdesktop.application.Action;

/**
 * Contains actions for modifying the route. These actions are used within the
 * {@link RoutePanel}.
 * 
 * @author Xperimental
 */
public class RouteActions extends ActionsBase {

    /**
     * Create an instance of this class which is modifying the provided data
     * model.
     * 
     * @param dataModel
     *            Data model to use.
     */
    public RouteActions(final IDataModel dataModel) {
        super(dataModel);
    }

    @Action
    public void playRoute() {
        if (getModel().getRoute() == IRoute.NULLROUTE) {
            getModel().startRoute();
        }
    }

    @Action
    public void pauseRoute() {
        IRoute route = getModel().getRoute();
        if (route != IRoute.NULLROUTE) {
            if (route.isPaused()) {
                route.unpause();
            } else {
                route.pause();
            }
        }
    }

    @Action
    public void resetRoute() {
        IRoute route = getModel().getRoute();
        if (route != IRoute.NULLROUTE) {
            route.stop();
        }
    }

}
