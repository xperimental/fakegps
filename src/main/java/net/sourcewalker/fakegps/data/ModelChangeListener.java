package net.sourcewalker.fakegps.data;

/**
 * This listener interface is used, when a class wants to know, if the contents
 * of the data model have changed.
 * 
 * @author Xperimental
 */
public interface ModelChangeListener {

    /**
     * Is called, when the data inside the model has changed.
     */
    void dataChanged();

}
