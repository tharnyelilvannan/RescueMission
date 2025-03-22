package ca.mcmaster.se2aa4.island.team01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team01.Actions.Fly;
import ca.mcmaster.se2aa4.island.team01.Actions.Scan;
import ca.mcmaster.se2aa4.island.team01.Actions.Stop;

public class CreekFinder {
    private final Logger logger = LogManager.getLogger();
    private final Scan scan;
    private final Fly fly;
    private final Stop stop;
    private ExtraInfo information;
    private boolean scanningForCreek = false;
    private boolean creekFound = false;

    public CreekFinder() {
        this.scan = new Scan();
        this.fly = new Fly();
        this.stop = new Stop();
    }

    public void updateInfo(ExtraInfo info) {
        this.information = info;
    }

    public boolean isScanning() {
        return scanningForCreek;
    }

    public boolean isCreekFound() {
        return creekFound;
    }

    public String startScanning() {
        scanningForCreek = true;
        return scan.scanArea();
    }

    public String findCreeks() {
        scanningForCreek = false;
        JSONObject extras = information.getExtras();

        if (!extras.has("creeks")) {
            logger.info("No creeks found.");
            return fly.flyOneUnit();
        }

        JSONArray creeks = extras.getJSONArray("creeks");
        if (creeks.length() > 0) {
            creekFound = true;
            logger.info("Creek found! ID: " + creeks.getString(0));
            return stop.returnToHeadquarters();
        }

        return fly.flyOneUnit();
    }
}
