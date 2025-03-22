package ca.mcmaster.se2aa4.island.team01;

enum EchoStatus {
    GROUND, OUT_OF_RANGE, UNKNOWN;

    static EchoStatus fromString(String value) {
        switch (value) {
            case "GROUND": return GROUND;
            case "OUT_OF_RANGE": return OUT_OF_RANGE;
            default: return UNKNOWN;
        }
    }
}