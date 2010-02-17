package net.sourcewalker.fakegps.gui;

import javax.swing.ActionMap;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.sourcewalker.fakegps.data.IDataModel;

import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;

/**
 * @author Xperimental
 */
public abstract class PanelBase extends JPanel {

    private static final long serialVersionUID = 3948540301390353803L;

    protected IDataModel model;

    protected ResourceMap resMap;

    protected ActionsBase actionsContainer;

    protected ActionMap actions;

    private TitledBorder border;

    protected PanelBase(Class<? extends PanelBase> clazz, String name,
            ActionsBase actionsClass, IDataModel dataModel) {
        super();
        model = dataModel;

        setName(name);
        setBorder(getPanelBorder());

        initResources(clazz, actionsClass);

        initComponents();

        resMap.injectComponents(this);
    }

    private void initResources(Class<? extends PanelBase> clazz,
            ActionsBase actionsClass) {
        actionsContainer = actionsClass;
        ApplicationContext ctx = Application.getInstance().getContext();
        resMap = ctx.getResourceMap(clazz);
        actions = ctx.getActionMap(actionsClass.getClass(), actionsClass);
    }

    protected abstract void initComponents();

    protected TitledBorder getPanelBorder() {
        if (border == null) {
            border = new TitledBorder("");
        }
        return border;
    }

    public void setTitle(String title) {
        getPanelBorder().setTitle(title);
    }

}
