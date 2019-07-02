package pl.polsl.repairmanagementdesktop.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import pl.polsl.repairmanagementdesktop.model.item.ItemEntity;
import pl.polsl.repairmanagementdesktop.utils.InstantDeserializer;
import uk.co.blackpepper.bowman.InlineAssociationDeserializer;
import uk.co.blackpepper.bowman.annotation.LinkedResource;
import uk.co.blackpepper.bowman.annotation.RemoteResource;
import uk.co.blackpepper.bowman.annotation.ResourceId;

import java.net.URI;
//import java.sql.Instant;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;

@RemoteResource("/request")
public class RequestEntity {

    private  URI uri;
    private String description;
    private String result;
    private String status;
    private Instant registerDate;
    private Instant endDate;

    private Collection<ActivityEntity> activities;
    private ItemEntity item;
    private EmployeeEntity manager;

    public RequestEntity(String description, String result, String status, Instant registerDate, Instant endDate, Collection<ActivityEntity> activities, ItemEntity item, EmployeeEntity manager) {
        this.description = description;
        this.result = result;
        this.status = status;
        this.registerDate = registerDate;
        this.endDate = endDate;
        this.activities = activities;
        this.item = item;
        this.manager = manager;
    }

    public RequestEntity(){}


    @ResourceId
    public URI getUri() {
        return uri;
    }
    public void setUri(URI uri) {
        this.uri = uri;
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


    @JsonDeserialize(contentUsing = InlineAssociationDeserializer.class)
    public Collection<ActivityEntity> getActivities() {
        return activities;
    }
    public void setActivities(Collection<ActivityEntity> activities) {
        this.activities = activities;
    }

    @LinkedResource
    public ItemEntity getItem() {
        return item;
    }
    public void setItem(ItemEntity item) {
        this.item = item;
    }

    @LinkedResource
    public EmployeeEntity getManager() {
        return manager;
    }
    public void setManager(EmployeeEntity manager) {
        this.manager = manager;
    }




}
