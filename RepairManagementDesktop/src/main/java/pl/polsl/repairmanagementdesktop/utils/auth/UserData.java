package pl.polsl.repairmanagementdesktop.utils.auth;

public class UserData{

    private String id;

    public void setId(String id) {
        this.id = id;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }

    private String role;
    private String usernameOrEmail;
    private Boolean active;

    public String getId() {
        return id;
    }
    public String getRole() {
        return role;
    }
    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }
    public Boolean getActive() {
        return active;
    }

    public UserData() {}

    public UserData(Boolean active, String id, String role, String usernameOrEmail) {
        this.active = active;
        this.id = id;
        this.role = role;
        this.usernameOrEmail = usernameOrEmail;
    }
}

