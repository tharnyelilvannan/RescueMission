package ca.mcmaster.se2aa4.island.team01;

import org.json.JSONObject;

public class GetResponse {
    public ExtraInfo translate(JSONObject response) {
        ExtraInfo info = new ExtraInfo(getCost(response), getExtras(response), getStatus(response));
        return info;
    }
    private  Integer getCost(JSONObject response){
        Integer cost = response.getInt("cost");
        return cost;
        
    }

    private  JSONObject getExtras(JSONObject response){
        JSONObject extras = response.getJSONObject("extras");
        return extras;
    }

    private String getStatus(JSONObject response){
        String status = response.getString("status");
        return status;
    }
    
}
