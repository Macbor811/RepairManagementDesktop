package pl.polsl.repairmanagementdesktop.model.activity;

import pl.polsl.repairmanagementdesktop.model.activitytype.ActivityTypeDTO;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeDTO;
import pl.polsl.repairmanagementdesktop.model.request.RequestDTO;

import java.sql.Timestamp;

public class ActivityDTO {

    private Integer id;
    private Integer sequenceNum;
    private String description;
    private String result;
    private String status;
    private Timestamp registerDate;
    private Timestamp endDate;
    private RequestDTO request;
    private EmployeeDTO personnel;
    private ActivityTypeDTO activityType;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(Integer sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate = registerDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public RequestDTO getRequest() {
        return request;
    }

    public void setRequest(RequestDTO request) {
        this.request = request;
    }

    public EmployeeDTO getPersonnel() {
        return personnel;
    }

    public void setPersonnel(EmployeeDTO personnel) {
        this.personnel = personnel;
    }

    public ActivityTypeDTO getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityTypeDTO activityType) {
        this.activityType = activityType;
    }
}
