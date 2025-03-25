package ca.mcmaster.se2aa4.island.team01;

public class CreekCalculator {
    private Site site = Site.get();
    private CreekList creeks = CreekList.get();
    private int creekX;
    private int creekY;
    private Creek closestCreek;
    private Double minDistance = Double.MAX_VALUE;
    private Double distance;

    public Creek calculateCreek() {
        
        int siteX = site.getXCoordinate();
        int siteY = site.getYCoordinate();

        for (Creek creek : creeks.getCreekList()) {
            
            creekX = creek.getXCoordinate();
            creekY = creek.getYCoordinate();

            distance = Math.sqrt((Math.pow((siteX - creekX), 2)) + (Math.pow((siteY - creekY), 2)));

            if (distance < minDistance) {
                minDistance = distance;
                closestCreek = creek;
            }
        }

        return closestCreek;
    }
}
