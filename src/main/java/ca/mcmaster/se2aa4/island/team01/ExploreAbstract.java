package ca.mcmaster.se2aa4.island.team01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.island.team01.Actions.Echo;
import ca.mcmaster.se2aa4.island.team01.Actions.Fly;
import ca.mcmaster.se2aa4.island.team01.Actions.Heading;
import ca.mcmaster.se2aa4.island.team01.Actions.Scan;
import ca.mcmaster.se2aa4.island.team01.Actions.Stop;

/**
 * The ExploreAbstract class provides the base explore function for all search algorithm classes.
 * It encapsulates common class references such as sending commands for echoing, flying, 
 * heading, scanning, and stopping. It also defines an abstract method `explore` 
 * that must be implemented by subclasses to carry out their specific search algorithm.
 */
public abstract class ExploreAbstract {
    protected final Logger logger = LogManager.getLogger();
    protected Echo echo;
    protected Fly fly;
    protected Heading heading;
    protected Scan scan;
    protected Stop stop;
    protected Direction currentDirection;

    public ExploreAbstract() {
        this.echo = new Echo();
        this.fly = new Fly();
        this.heading = new Heading();
        this.scan = new Scan();
        this.stop = new Stop();
        this.currentDirection = Direction.EAST;
    }

    /**
     * Updates exploration classes with extra information.
     * @param info The response information for the classes.
     */
    public abstract void updateInfo(ExtraInfo info);

    /**
     * Defines the specific exploration algorithm to be implemented by subclasses.
     * @return A string indicating the action request to be taken.
     */
    public abstract String explore();
}
