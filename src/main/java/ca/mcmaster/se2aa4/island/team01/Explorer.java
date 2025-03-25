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
    private FindIsland findIsland; 
    private GetResponse getResponse = new GetResponse();
    private Drone drone = new Drone();
    private CreekCalculator calculator = new CreekCalculator();

    @Override
    public void initialize(String s) {
        logger.trace("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.trace("** Initialization info:\n {}", info.toString(2));
        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");
        logger.trace("The drone is facing {}", direction);
        logger.trace("Battery level is {}", batteryLevel);
        findIsland = new FindIsland();
        
    }

    @Override
    public String takeDecision() {
        logger.trace("** Taking a decision"); 
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
        logger.trace("The cost of the action was {}", cost);
        String status = information.getStatus();
        logger.trace("The status of the drone is {}", status);
        JSONObject extraInfo = information.getExtras();
        logger.trace("Additional information received: {}", extraInfo);
        drone.updateInfo(information);

    }

    @Override
    public String deliverFinalReport() {
        Creek closestCreek = calculator.calculateCreek();
        logger.info("The closest creek is " + closestCreek.getID());
        return "Exploration complete.";
    }
}
