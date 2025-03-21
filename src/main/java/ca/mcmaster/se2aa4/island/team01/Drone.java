package ca.mcmaster.se2aa4.island.team01;

public class Drone {
    private GroundDetector groundDetector;
    private CreekFinder creekFinder;
    private FindIsland findIsland;

    public Drone() {
        findIsland = new FindIsland();
        groundDetector = new GroundDetector();
        creekFinder = new CreekFinder();
    }

    public void updateInfo(ExtraInfo info) {
        findIsland.updateGroundDetector(info);  // Forward the update
    }

    public String initalizeExploration() {
        String decision;
        if (groundDetector.isGroundFound() == false) {
            decision = findIsland.searchForGround(); 
        } else {
            decision = creekFinder.detectBeach();
        }
        return decision;
    }
}
