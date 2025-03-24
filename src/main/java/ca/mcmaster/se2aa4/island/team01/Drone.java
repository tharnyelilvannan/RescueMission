package ca.mcmaster.se2aa4.island.team01;
import ca.mcmaster.se2aa4.island.team01.Actions.Stop;

public class Drone {
    private GroundDetector groundDetector;
    private FindIsland findIsland;
    private IslandLength islandLength;
    private Stop stop;
    private SearchIsland searchIsland;
    private boolean alignedWithCoastline = false;
    private boolean initalSearchDirectionSet = false;

    public Drone() {
        findIsland = new FindIsland();
        groundDetector = new GroundDetector();
        stop = new Stop();
        searchIsland = new SearchIsland();
        islandLength = new IslandLength();
    }
    public void updateInfo(ExtraInfo info) {
        if (info != null) {
            findIsland.updateGroundDetector(info);
            searchIsland.updateInfo(info);
            islandLength.updateInfo(info);
        }
    }
    
    public String initalizeExploration() {
        String decision;
        if (findIsland.isLandingPhase() == false) {
            decision = findIsland.searchForGround(); 
        } else if (findIsland.isLandingPhase() == true && islandLength.hasFoundLength() == false) {
            decision = islandLength.measureIsland();
        } else if (islandLength.hasFoundLength() == true) {
            decision = searchIsland.exploreIsland(islandLength.getIslandLength());
        } 
        
        else {
            decision = stop.returnToHeadquarters();
        }
        return decision;
    }
}
