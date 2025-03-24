package ca.mcmaster.se2aa4.island.team01;

public class CreekCalculator {
    Site site = Site.get();
    CreekList creeks = CreekList.get();
    int creekX;
    int creekY;
    Creek closestCreek;
    Double minDistance = Double.MAX_VALUE;
    Double d;

    public Creek calculateCreek() {
        
        int siteX = site.getXCoordinate();
        int siteY = site.getYCoordinate();

        for (Creek creek : creeks.getCreekList()) {
            
            creekX = creek.getXCoordinate();
            creekY = creek.getYCoordinate();

            d = Math.sqrt((Math.pow((siteX - creekX), 2)) + (Math.pow((siteY - creekY), 2)));

            if (d < minDistance) {
                minDistance = d;
                closestCreek = creek;
            }
        }

        return closestCreek;
    }
}