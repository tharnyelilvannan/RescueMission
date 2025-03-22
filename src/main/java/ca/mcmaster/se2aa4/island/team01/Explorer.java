// to run: mvn exec:java -q -Dexec.args="./maps/map03.json"
package ca.mcmaster.se2aa4.island.team01;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import eu.ace_design.island.bot.IExplorerRaid;


public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    FindIsland findIsland; 
    // CreekFinder creekFinder;
    GetResponse getResponse = new GetResponse();
    Drone drone = new Drone();
    private int battery;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}", info.toString(2));

        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", direction);
        battery = batteryLevel;
        logger.info("Battery level is {}", batteryLevel);
        findIsland = new FindIsland();
        
    }

    @Override
    public String takeDecision() {
        logger.info("** Taking a decision"); 
        return drone.initalizeExploration();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        ExtraInfo information = getResponse.translate(response);

        if (information == null) {
            logger.error("Received null ExtraInfo in acknowledgeResults. Skipping update.");
            return;
        }
        Integer cost = information.getCost();
        logger.info("The cost of the action was {}", cost);
        battery = battery - cost;
        logger.info("The battery remaining is {}", battery);
        String status = information.getStatus();
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = information.getExtras();
        logger.info("Additional information received: {}", extraInfo);
        drone.updateInfo(information);

    }

    @Override
    public String deliverFinalReport() {
        return "Exploration complete.";
    }
}