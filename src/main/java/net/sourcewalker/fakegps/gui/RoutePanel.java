package net.sourcewalker.fakegps.gui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;

import net.sourcewalker.fakegps.data.IDataModel;

import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;

public class RoutePanel extends JPanel {

    private static final long serialVersionUID = -1202641708255370124L;

    private IDataModel model;

    private TitledBorder border;

    private ResourceMap resMap;

    private ActionMap actions;

    private JSlider speedSlider;

    private JButton startButton;

    private JButton pauseButton;

    private JButton stopButton;

    private JPanel buttonPanel;

    private JLabel speedLabel;

    public RoutePanel(IDataModel dataModel) {
        model = dataModel;

        initResources();

        setName("routePanel");
        setBorder(getPanelBorder());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(getSpeedLabel());
        add(getSpeedSlider());
        add(getButtonPanel());

        resMap.injectComponents(this);
    }

    public JLabel getSpeedLabel() {
        if (speedLabel == null) {
            speedLabel = new JLabel(resMap.getString(
                    "routePanel.speedLabel.text", ""));
        }
        return speedLabel;
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.add(getStartButton());
            buttonPanel.add(getPauseButton());
            buttonPanel.add(getStopButton());
        }
        return buttonPanel;
    }

    private JSlider getSpeedSlider() {
        if (speedSlider == null) {
            speedSlider = new JSlider();
        }
        return speedSlider;
    }

    private JButton getStartButton() {
        if (startButton == null) {
            startButton = new JButton(actions.get("playRoute"));
        }
        return startButton;
    }

    private JButton getPauseButton() {
        if (pauseButton == null) {
            pauseButton = new JButton(actions.get("pauseRoute"));
        }
        return pauseButton;
    }

    private JButton getStopButton() {
        if (stopButton == null) {
            stopButton = new JButton(actions.get("resetRoute"));
        }
        return stopButton;
    }

    private void initResources() {
        ApplicationContext ctx = Application.getInstance().getContext();
        resMap = ctx.getResourceMap(RoutePanel.class);
        actions = ctx.getActionMap(RouteActions.class, new RouteActions(model));
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
