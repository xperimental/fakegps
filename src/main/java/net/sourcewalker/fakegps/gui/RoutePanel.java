package net.sourcewalker.fakegps.gui;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import net.sourcewalker.fakegps.data.IDataModel;

public class RoutePanel extends PanelBase {

    private static final long serialVersionUID = -1202641708255370124L;

    private JSlider speedSlider;

    private JButton startButton;

    private JButton pauseButton;

    private JButton stopButton;

    private JPanel buttonPanel;

    private JLabel speedLabel;

    public RoutePanel(IDataModel dataModel) {
        super(RoutePanel.class, "routePanel", new RouteActions(dataModel),
                dataModel);
    }

    @Override
    protected void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(getSpeedLabel());
        add(getSpeedSlider());
        add(getButtonPanel());
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

    public void setTitle(String title) {
        getPanelBorder().setTitle(title);
    }

}
