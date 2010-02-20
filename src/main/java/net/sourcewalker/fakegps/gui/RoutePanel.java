package net.sourcewalker.fakegps.gui;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sourcewalker.fakegps.data.IDataModel;
import net.sourcewalker.fakegps.data.IRoute;
import net.sourcewalker.fakegps.data.ModelChangeListener;

public class RoutePanel extends PanelBase {

    private static final long serialVersionUID = -1202641708255370124L;

    /**
     * Conversion factor for converting speed to slider position.
     */
    private static final double SPEED_TO_SLIDER = 100000d;

    private JSlider speedSlider;

    private JToggleButton startButton;

    private JToggleButton pauseButton;

    private JToggleButton stopButton;

    private JPanel buttonPanel;

    private JLabel speedLabel;

    public RoutePanel(IDataModel dataModel) {
        super(RoutePanel.class, "routePanel", new RouteActions(dataModel),
                dataModel);
        model.addChangeListener(new ModelListener());
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
            speedSlider.setMinimum(1);
            speedSlider.setMaximum(1000);
            speedSlider.setValue(1);
            speedSlider.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(final ChangeEvent e) {
                    double newSpeed = speedSlider.getValue() / SPEED_TO_SLIDER;
                    model.setRouteSpeed(newSpeed);
                }
            });
        }
        return speedSlider;
    }

    private JToggleButton getStartButton() {
        if (startButton == null) {
            startButton = new JToggleButton(actions.get("playRoute"));
        }
        return startButton;
    }

    private JToggleButton getPauseButton() {
        if (pauseButton == null) {
            pauseButton = new JToggleButton(actions.get("pauseRoute"));
        }
        return pauseButton;
    }

    private JToggleButton getStopButton() {
        if (stopButton == null) {
            stopButton = new JToggleButton(actions.get("resetRoute"));
            stopButton.setSelected(true);
        }
        return stopButton;
    }

    /**
     * Updates the selected state of the buttons.
     */
    private void updateButtonState() {
        IRoute route = model.getRoute();
        switch (route.getState()) {
        case RUNNING:
            startButton.setSelected(true);
            pauseButton.setSelected(false);
            stopButton.setSelected(false);
            break;
        case PAUSED:
            startButton.setSelected(false);
            pauseButton.setSelected(true);
            stopButton.setSelected(false);
            break;
        default:
            startButton.setSelected(false);
            pauseButton.setSelected(false);
            stopButton.setSelected(true);
            break;
        }
    }

    /**
     * Updates the slider position.
     */
    private void updateSpeedSlider() {
        double speed = model.getRouteSpeed();
        int value = (int) (speed * SPEED_TO_SLIDER);
        speedSlider.setValue(value);
    }

    private class ModelListener implements ModelChangeListener {

        /*
         * (non-Javadoc)
         * @see net.sourcewalker.fakegps.data.ModelChangeListener#dataChanged()
         */
        @Override
        public void dataChanged() {
            updateButtonState();
            updateSpeedSlider();
        }

    }

}
