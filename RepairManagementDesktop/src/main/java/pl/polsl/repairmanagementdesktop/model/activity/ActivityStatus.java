package pl.polsl.repairmanagementdesktop.model.activity;

import java.util.Optional;

 public enum ActivityStatus {

    OPEN("OPN"),
    IN_PROGRESS("PRO"),
    CANCELLED("CAN"),
    FINISHED("FIN");

    private final String text;

    ActivityStatus(final String text) {
        this.text = text;
    }

    @Override
    public final String toString() {
        return text;
    }

    public static ActivityStatus fromString(String string){
        for (var value : ActivityStatus.values()){
            if (string.equals(value.toString())){
                return value;
            }
        }
        throw new IllegalArgumentException("Could not create activity status from string.");
    }

    public boolean hasEnded(){
        return this.equals(CANCELLED) || this.equals(FINISHED);
    }

}


