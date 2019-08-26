package pl.polsl.repairmanagementdesktop.model.employee;


import java.util.Optional;

public enum EmployeeRole {
    ADMIN("ADM"),
    MANAGER("MAN"),
    WORKER("WRK");

    private final String text;

    EmployeeRole(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static EmployeeRole fromString(String string){
        for (var value : EmployeeRole.values()){
            if (string.equals(value.toString())){
                return value;
            }
        }
        throw new IllegalArgumentException("Can't convert string to EmployeeRole.");
    }
}
