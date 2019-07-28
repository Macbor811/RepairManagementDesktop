package pl.polsl.repairmanagementdesktop.model.activity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.InstantKeyDeserializer;
import org.springframework.format.annotation.DateTimeFormat;
import pl.polsl.repairmanagementdesktop.abstr.Entity;
import pl.polsl.repairmanagementdesktop.model.activitytype.ActivityTypeEntity;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;
import pl.polsl.repairmanagementdesktop.utils.InstantDeserializer;
import uk.co.blackpepper.bowman.annotation.LinkedResource;
import uk.co.blackpepper.bowman.annotation.RemoteResource;
import uk.co.blackpepper.bowman.annotation.ResourceId;

import java.net.URI;
import java.time.Instant;
import java.util.Objects;

@RemoteResource("/activity")
public class ActivityEntity implements Entity {
    private URI uri;

    private Integer sequenceNum;
    private String description;
    private String result;
    private String status;
    private Instant registerDate;
    private Instant endDate;
    private ActivityTypeEntity activityType;
    private RequestEntity request;
    private EmployeeEntity worker;

    public ActivityEntity(){};

    public ActivityEntity(Integer sequenceNum, String description, String result, String status, Instant registerDate, Instant endDate, ActivityTypeEntity activityType, RequestEntity request, EmployeeEntity worker) {
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

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    public Instant getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(Instant registerDate) {
        this.registerDate = registerDate;
    }

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    public Instant getEndDate() {
        return endDate;
    }
    public void setEndDate(Instant endDate) {
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
