package pl.polsl.repairmanagementdesktop;

public class UserData {

    private String id;
    private String role;

    public UserData(){}

    public UserData(String id, String role) {
        this.id = id;
        this.role = role;
    }

    public String getId() {
        return id;
    }
    public String getRole() {
        return role;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setRole(String role) {
        this.role = role;
    }
}

