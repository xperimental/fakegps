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

    /**
     * Starts the route simulation if there is none running.
     */
    @Action
    public final void playRoute() {
        if (getModel().getRoute() == IRoute.NULLROUTE) {
            getModel().startRoute();
        }
    }

    /**
     * Pauses or unpauses the route simulation if there is one running.
     */
    @Action
    public final void pauseRoute() {
        IRoute route = getModel().getRoute();
        if (route != IRoute.NULLROUTE) {
            if (route.isPaused()) {
                route.unpause();
            } else {
                route.pause();
            }
        }
    }

    /**
     * Stops the route simulation if there is one running.
     */
    @Action
    public final void resetRoute() {
        IRoute route = getModel().getRoute();
        if (route != IRoute.NULLROUTE) {
            route.stop();
        }
    }

}
