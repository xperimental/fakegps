package net.sourcewalker.fakegps.data;

/**
 * This class implements the {@link IRoute} interface and is used to signify the
 * absence of a real route controller.
 * 
 * @author Xperimental
 */
public final class NullRoute implements IRoute {

    /**
     * Create a new instance of this class.
     */
    protected NullRoute() {
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IRoute#isPaused()
     */
    @Override
    public boolean isPaused() {
        return false;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IRoute#pause()
     */
    @Override
    public void pause() {
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IRoute#stopRoute()
     */
    @Override
    public void stop() {
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IRoute#unpause()
     */
    @Override
    public void unpause() {
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IRoute#getSpeed()
     */
    @Override
    public double getSpeed() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.data.IRoute#setSpeed(double)
     */
    @Override
    public void setSpeed(final double newSpeed) {
    }

}
