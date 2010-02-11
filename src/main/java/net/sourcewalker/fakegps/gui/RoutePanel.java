package net.sourcewalker.fakegps.gui;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.sourcewalker.fakegps.data.IDataModel;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

public class RoutePanel extends JPanel {

    private static final long serialVersionUID = -1202641708255370124L;

    private IDataModel model;

    private TitledBorder border;

    private ResourceMap resMap;

    public RoutePanel(IDataModel dataModel) {
        model = dataModel;

        initResources();

        setName("routePanel");
        setBorder(getPanelBorder());

        resMap.injectComponents(this);
    }

    private void initResources() {
        resMap = Application.getInstance().getContext().getResourceMap(
                RoutePanel.class);
    }

    private TitledBorder getPanelBorder() {
        if (border == null) {
            border = new TitledBorder("");
        }
        return border;
    }

    public void setTitle(String title) {
        getPanelBorder().setTitle(title);
    }

}
