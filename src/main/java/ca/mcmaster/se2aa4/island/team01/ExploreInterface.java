package ca.mcmaster.se2aa4.island.team01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.island.team01.Actions.Echo;
import ca.mcmaster.se2aa4.island.team01.Actions.Fly;
import ca.mcmaster.se2aa4.island.team01.Actions.Heading;
import ca.mcmaster.se2aa4.island.team01.Actions.Scan;
import ca.mcmaster.se2aa4.island.team01.Actions.Stop;

public abstract class ExploreInterface {
    protected final Logger logger = LogManager.getLogger();
    protected Echo echo;
    protected Fly fly;
    protected Heading heading;
    protected Scan scan;
    protected Stop stop;
    protected Direction currentDirection;

    public ExploreInterface() {
        this.echo = new Echo();
        this.fly = new Fly();
        this.heading = new Heading();
        this.scan = new Scan();
        this.stop = new Stop();
        this.currentDirection = Direction.EAST;
    }

    public abstract void updateInfo(ExtraInfo info);

    public abstract String explore();
}
