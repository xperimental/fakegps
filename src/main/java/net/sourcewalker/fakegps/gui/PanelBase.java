package net.sourcewalker.fakegps.gui;

import javax.swing.ActionMap;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.sourcewalker.fakegps.data.IDataModel;

import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;

/**
 * Abstract base class for GUI panels which interact with the data model.
 * 
 * @author Xperimental
 */
public abstract class PanelBase extends JPanel {

    /**
     * ID for serialization.
     */
    private static final long serialVersionUID = 3948540301390353803L;

    /**
     * Contains a reference to the data model.
     */
    private IDataModel model;

    /**
     * Contains the resource map for this panel.
     */
    private ResourceMap resMap;

    /**
     * Contains the actions class used by this panel.
     */
    private ActionsBase actionsContainer;

    /**
     * Contains the action map created from the actions class.
     */
    private ActionMap actions;

    /**
     * Contains the border around the panel.
     */
    private TitledBorder border;

    /**
     * Set the default attributes of the base class.
     * 
     * @param clazz
     *            Class to use for resource map.
     * @param name
     *            Name of panel (used for resources).
     * @param actionsClass
     *            Class containing the actions for this panel.
     * @param dataModel
     *            Data model to use.
     */
    protected PanelBase(final Class<? extends PanelBase> clazz,
            final String name, final ActionsBase actionsClass,
            final IDataModel dataModel) {
        super();
        model = dataModel;

        setName(name);
        setBorder(getPanelBorder());

        initResources(clazz, actionsClass);

        initComponents();

        getResMap().injectComponents(this);
    }

    /**
     * Initializes the resource and actions maps.
     * 
     * @param clazz
     *            Class to use for the resource map.
     * @param actionsClass
     *            Class containing the actions for this panel.
     */
    private void initResources(final Class<? extends PanelBase> clazz,
            final ActionsBase actionsClass) {
        actionsContainer = actionsClass;
        ApplicationContext ctx = Application.getInstance().getContext();
        resMap = ctx.getResourceMap(clazz);
        actions = ctx.getActionMap(actionsClass.getClass(), actionsClass);
    }

    /**
     * Initializes the GUI components in the panel.
     */
    protected abstract void initComponents();

    /**
     * Creates the border around the panel.
     * 
     * @return Border of panel.
     */
    protected final TitledBorder getPanelBorder() {
        if (border == null) {
            border = new TitledBorder("");
        }
        return border;
    }

    /**
     * Sets the title of the panel. The title is displayed in the border.
     * 
     * @param title
     *            New title of panel.
     */
    public final void setTitle(final String title) {
        getPanelBorder().setTitle(title);
    }

    /**
     * Returns the data model.
     * 
     * @return Data model.
     */
    public final IDataModel getModel() {
        return model;
    }

    /**
     * Returns the resource map of the implementing class.
     * 
     * @return Resource map.
     */
    public final ResourceMap getResMap() {
        return resMap;
    }

    /**
     * Returns the class containing the panel actions.
     * 
     * @return Actions class.
     */
    public final ActionsBase getActionsContainer() {
        return actionsContainer;
    }

    /**
     * Returns the actions map for this panel.
     * 
     * @return Actions map
     */
    public final ActionMap getActions() {
        return actions;
    }

}
