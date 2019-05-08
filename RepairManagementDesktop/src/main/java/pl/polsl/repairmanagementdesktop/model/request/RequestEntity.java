package pl.polsl.repairmanagementdesktop.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityEntity;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import pl.polsl.repairmanagementdesktop.model.item.ItemEntity;
import uk.co.blackpepper.bowman.InlineAssociationDeserializer;
import uk.co.blackpepper.bowman.annotation.LinkedResource;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;


public class RequestEntity {
    private Integer id;
    private String description;
    private String result;
    private String status;
    private Timestamp registerDate;
    private Timestamp endDate;

    private Collection<ActivityEntity> activities;
    private ItemEntity item;
    private EmployeeEntity manager;

    public RequestEntity(String description, String result, String status, Timestamp registerDate, Timestamp endDate, Collection<ActivityEntity> activities, ItemEntity item, EmployeeEntity manager) {
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


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestEntity that = (RequestEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(result, that.result) &&
                Objects.equals(status, that.status) &&
                Objects.equals(registerDate, that.registerDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, result, status, registerDate, endDate);
    }


}
