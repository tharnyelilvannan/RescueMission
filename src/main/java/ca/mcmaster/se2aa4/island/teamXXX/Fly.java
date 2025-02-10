package ca.mcmaster.se2aa4.island.teamXXX;

import java.io.StringReader;

import org.json.JSONObject;
import org.json.JSONTokener;


public class Fly {
    Direction direction; 

    public String flyRight(String s, Integer x, Integer y){ // { "action": "heading", "parameters": { "direction": "N" } }
        JSONObject command = new JSONObject(new JSONTokener(new StringReader(s)));
        //String action = command.getString("action");
        String parameterString = command.getString("parameters");

        JSONObject parameter = new JSONObject(new JSONTokener(new StringReader(parameterString)));
        String dir = parameter.getString("direction"); 

       
        if (dir.equals("N")){
            x = x + 1; 
            y = y + 1; 
            direction = Direction.EAST;
        }
        else if (dir.equals("E")){
            x = x + 1; 
            y = y - 1; 
            direction = Direction.SOUTH;
        }
        else if (dir.equals("S")){
            x = x - 1; 
            y = y - 1;
            direction = Direction.WEST; 
        }
        else if (dir.equals("W")){
            x = x - 1; 
            y = y + 1; 
            direction = Direction.NORTH; 
        }

        
        JSONObject result = new JSONObject(); 
        result.put("currentHeading", direction); 

        JSONObject newPos = new JSONObject(); 
        newPos.put("x", x); 
        newPos.put("y", y);

        result.put("currentLocation", newPos); 

        return result.toString(); 

    }

    
}
