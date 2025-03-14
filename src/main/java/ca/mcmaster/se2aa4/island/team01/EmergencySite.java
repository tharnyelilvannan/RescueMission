package ca.mcmaster.se2aa4.island.team01;
import org.json.JSONObject;
import org.json.JSONTokener;
import netscape.javascript.JSObject;

class EmergencySite extends LocationCoordinates {

    LocationCoordinates closestCreek = null;

    public EmergencySite(String coordinates) {
        super(coordinates);
    }

    public void setClosestCreek(LocationCoordinates creek) {
        this.closestCreek = creek;
    }

}