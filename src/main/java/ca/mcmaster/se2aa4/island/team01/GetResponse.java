package ca.mcmaster.se2aa4.island.team01;

import org.json.JSONObject;

public class GetResponse {
    public ExtraInfo translate(JSONObject response) {
        if (response == null || response.isEmpty()) {
            return null; // Prevents null or empty responses from causing crashes
        }
        ExtraInfo info = new ExtraInfo(getCost(response), getExtras(response), getStatus(response));
        return info;
    }
    private  Integer getCost(JSONObject response){
        Integer cost = response.getInt("cost");
        return cost;
        
    }

    private JSONObject getExtras(JSONObject response) {
        if (response.has("extras") && !response.isNull("extras")) {
            return response.getJSONObject("extras");
        } else {
            return new JSONObject(); // Return an empty object if "extras" is missing or null
        }
    }  

    private String getStatus(JSONObject response){
        String status = response.getString("status");
        return status;
    }
    
}
