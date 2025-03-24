package ca.mcmaster.se2aa4.island.team01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CreekCalculator {
    Site site = Site.get();
    CreekList creeks = CreekList.get();
    int creekX;
    int creekY;
    Creek closestCreek;
    Double minDistance = Double.MAX_VALUE;
    Double d;
    private final Logger logger = LogManager.getLogger();

    public Creek calculateCreek() {
        int siteX = site.getXCoordinate();
        int siteY = site.getYCoordinate();
        logger.error(siteX);
        logger.error(siteY);

        for (Creek creek : creeks.getCreekList()) {
            creekX = creek.getXCoordinate();
            creekY = creek.getYCoordinate();

            d = Math.sqrt((Math.pow((siteX - creekX), 2)) + (Math.pow((siteY - creekY), 2)));
            logger.error(creek.getID());
            logger.error(creekX);
            logger.error(creekY);
            logger.error("Distance: " + d);

            if (d < minDistance) {
                minDistance = d;
                closestCreek = creek;
            }
        }

        return closestCreek;
    }
}