package ca.mcmaster.se2aa4.island.team01;

import org.json.JSONArray;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team01.Actions.Echo;
import ca.mcmaster.se2aa4.island.team01.Actions.Fly;
import ca.mcmaster.se2aa4.island.team01.Actions.Heading;
import ca.mcmaster.se2aa4.island.team01.Actions.Scan;

public class CreekFinder extends Terrain {
    private ExtraInfo information;
    private GroundDetector groundDetector;
    private Scan scan;
    private Fly fly;
    private Heading heading;
    private Echo echo;
    private Direction direction;

    public CreekFinder() {
        scan = new Scan();
        fly = new Fly();
        heading = new Heading();
        echo = new Echo();
    }

    public void updateInfo(ExtraInfo info) {
        this.information = info;
        detectBeach();
    }

    // move drone up so more than 20% of scan is beach (??)
    public String detectBeach() {
        JSONObject extras = information.getExtras();
        if (extras.has("biomes")) {
            JSONArray biomes = extras.getJSONArray("biomes");;
            if (biomes.length() > 0) {
                for (int i = 0; i < biomes.length(); i++) {
                    String biome = biomes.getString(i);
                    if (biome.equals("OCEAN")) {
                        return fly.flyOneUnit();
                    } 
                }
            }
        }
        return fly.flyOneUnit();
    }

    public String findCreek() {
        JSONObject extras = information.getExtras();
        boolean creekFound = false;
        JSONArray biomes;
        int fly_count = 0;
        
        while(creekFound == false) {
            groundDetector.detectGround();
            
            while (fly_count < 3) {
                if (groundDetector.isGroundFound() == true) {
                    fly_count++;
                    return fly.flyOneUnit();
                } else {
                    turn();
                    fly_count++;
                    return fly.flyOneUnit();
                }
            }
            if (fly_count == 3){
                fly_count = 0;
                return scan.scanArea();
            }

            biomes = extras.getJSONArray("biomes");


            JSONObject extras = information.getExtras();
        if (extras.has("biomes")) {
            JSONArray biomes = extras.getJSONArray("biomes");;
            if (biomes.length() > 0) {
                for (int i = 0; i < biomes.length(); i++) {
                    String biome = biomes.getString(i);

            if (biome == "land") {
                for (int i = 0; i < 3; i++) {
                    groundDetector.fly();
                }
                isGround = groundDetector.echoStraight();
                if (isGround.equals("GROUND")) {
                    String scan = groundDetector.scan();
                    biome = scan.optString("biomes", "UNKNOWN");
                    checkForCreek(s);

                } else {
                    turn();
                }
            } else if (biome == "ocean") {
                turn();
                groundDetector.echoStraight();
                if (landDetected()) {
                    groundDetector.fly();
                    checkForCreek();
                }
            }
            return scan.scanArea();

        }
        groundDetector.returnToHeadquarters();      // stop
    }



    private boolean checkForCreek(GroundDetector scan_response) {
        for 
        return true;
    }

    private void turn() {
        Direction current_direction = groundDetector.getCurrentDirection();
        Direction new_direction = current_direction.turnRight();
        groundDetector.changeHeading(new_direction);
    }

}