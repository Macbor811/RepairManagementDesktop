package pl.polsl.repairmanagementdesktop.model.activity;

import pl.polsl.repairmanagementdesktop.model.activitytype.ActivityTypeEntity;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;
import uk.co.blackpepper.bowman.annotation.LinkedResource;
import uk.co.blackpepper.bowman.annotation.RemoteResource;
import uk.co.blackpepper.bowman.annotation.ResourceId;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Objects;

@RemoteResource("/activity")
public class ActivityEntity  {
    private URI uri;

    private Integer sequenceNum;
    private String description;
    private String result;
    private String status;
    private Timestamp registerDate;
    private Timestamp endDate;
    private ActivityTypeEntity activityType;
    private RequestEntity request;
    private EmployeeEntity worker;

    public ActivityEntity(){};

    public ActivityEntity(Integer sequenceNum, String description, String result, String status, Timestamp registerDate, Timestamp endDate, ActivityTypeEntity activityType, RequestEntity request, EmployeeEntity worker) {
        this.sequenceNum = sequenceNum;
        this.description = description;
        this.result = result;
        this.status = status;
        this.registerDate = registerDate;
        this.endDate = endDate;
        this.activityType = activityType;
        this.request = request;
        this.worker = worker;
    }


    @ResourceId
    public URI getUri() {
        return uri;
    }
    public void setUri(URI uri) {
        this.uri = uri;
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


    @LinkedResource
    public RequestEntity getRequest() {
        return request;
    }
    public void setRequest(RequestEntity request) {
        this.request = request;
    }

    @LinkedResource
    public EmployeeEntity getWorker() {
        return worker;
    }
    public void setWorker(EmployeeEntity worker) {
        this.worker = worker;
    }

    @LinkedResource
    public ActivityTypeEntity getActivityType() {
        return activityType;
    }
    public void setActivityType(ActivityTypeEntity activityType) {
        this.activityType = activityType;
    }



}
