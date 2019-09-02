package pl.polsl.repairmanagementdesktop.model.employee;

import java.time.Instant;

public class EmployeeUserDataDto {

    private Instant deactivationDate;

    public EmployeeUserDataDto(Instant deactivationDate, String password) {
        this.deactivationDate = deactivationDate;
        this.password = password;
    }

    private String password;



    public Instant getDeactivationDate() {
        return deactivationDate;
    }

    public void setDeactivationDate(Instant deactivationDate) {
        this.deactivationDate = deactivationDate;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}


