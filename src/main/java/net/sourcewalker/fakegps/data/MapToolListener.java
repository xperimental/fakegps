package net.sourcewalker.fakegps.data;

/**
 * This listener interface is used, when a class wants to be informed about
 * changes of the current map tool from the data model.
 * 
 * @author Xperimental
 */
public interface MapToolListener {

    /**
     * A new tool was selected. This can also be the same as the previous tool.
     * 
     * @param currentTool
     *            New tool, that was selected.
     */
    void toolChanged(MapTool currentTool);

}
