package net.sourcewalker.fakegps.gui;

import net.sourcewalker.fakegps.data.IDataModel;

/**
 * Abstract base class for classes providing actions for interacting with the
 * data model.
 * 
 * @author Xperimental
 */
public abstract class ActionsBase {

    /**
     * Data model to modify.
     */
    private IDataModel model;

    /**
     * Creates a new instance of the class using the provided data model.
     * 
     * @param dataModel
     *            Data model to modify.
     */
    protected ActionsBase(final IDataModel dataModel) {
        model = dataModel;
    }

    /**
     * Returns the model associated with this action class.
     * 
     * @return The data model.
     */
    public final IDataModel getModel() {
        return model;
    }

}
