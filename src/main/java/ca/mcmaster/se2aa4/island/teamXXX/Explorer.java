package ca.mcmaster.se2aa4.island.teamXXX;
// to run: mvn exec:java -q -Dexec.args="./maps/map03.json"
import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    // private GroundDetector groundDetector;

    // private String nextAction; 
    // private String currentAction; 
    
    
    MakeDecision decision; 

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}", info.toString(2));

        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);

        decision = new MakeDecision();
        
    }

    @Override
    public String takeDecision() {
        logger.info("** Taking a decision"); 

        return decision.makeDecision(); 
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n" + response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);

        decision.sendResponse(extraInfo);
    }

    @Override
    public String deliverFinalReport() {
        return "Exploration complete.";
    }
}