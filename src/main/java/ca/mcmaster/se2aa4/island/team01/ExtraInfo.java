package ca.mcmaster.se2aa4.island.team01;

import org.json.JSONObject;

/**
 * The ExtraInfo class encapsulates the drone's state information.
 * It processes the cost, direction, cost, and current status of the drone.
 */
public class ExtraInfo {
    private final Integer cost;
    private final JSONObject extras;
    private final String droneStatus;
    private Direction direction;

    /**
     * Constructs an ExtraInfo object with the cost, extras, and drone status.
     * @param cost The cost of the current action or task.
     * @param extras Additional information related to the action in JSON format.
     * @param droneStatus The current status of the drone (e.g., "idle", "exploring").
     */
    public ExtraInfo(Integer cost, JSONObject extras, String droneStatus) {
        this.cost = cost;
        this.extras = extras;
        this.droneStatus = droneStatus;
    }

    public Direction getDirection(){
        return direction;
    }

    public JSONObject getExtras() {
        return extras;
    }

    public Integer getCost() {
        return cost;
    }

    public String getStatus(){
        return droneStatus;
    }
}
