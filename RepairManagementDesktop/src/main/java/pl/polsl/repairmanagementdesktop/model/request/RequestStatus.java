package pl.polsl.repairmanagementdesktop.model.request;

public enum RequestStatus {
    OPEN("OPN"),
    IN_PROGRESS("PRO"),
    CANCELLED("CAN"),
    FINISHED("FIN");

    private final String text;

    RequestStatus(final String text) {
        this.text = text;
    }

    @Override
    public final String toString() {
        return text;
    }

    public static RequestStatus fromString(String string){
        for (var value : RequestStatus.values()){
            if (string.equals(value.toString())){
                return value;
            }
        }
        throw new IllegalArgumentException("Could not create request status from string.");
    }

    public boolean hasEnded(){
        return this.equals(CANCELLED) || this.equals(FINISHED);
    }
}
