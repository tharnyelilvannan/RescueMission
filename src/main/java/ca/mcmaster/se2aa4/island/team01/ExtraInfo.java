package ca.mcmaster.se2aa4.island.team01;

import org.json.JSONObject;

public class ExtraInfo {
    private final Integer cost;
    private final JSONObject extras;
    private final String droneStatus;
    private Direction direction;

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