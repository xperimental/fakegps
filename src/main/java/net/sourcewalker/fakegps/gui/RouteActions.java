package net.sourcewalker.fakegps.gui;

import org.jdesktop.application.Action;

import net.sourcewalker.fakegps.data.IDataModel;

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

    }

    @Action
    public void pauseRoute() {

    }

    @Action
    public void resetRoute() {

    }

}
