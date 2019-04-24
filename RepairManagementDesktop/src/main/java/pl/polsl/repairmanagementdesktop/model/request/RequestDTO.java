package pl.polsl.repairmanagementdesktop.model.request;


import pl.polsl.repairmanagementdesktop.model.activity.ActivityDTO;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeDTO;
import pl.polsl.repairmanagementdesktop.model.item.ItemDTO;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.stream.Collectors;

public class RequestDTO {

    private Integer id;
    private String description;
    private String result;
    private String status;
    private Timestamp registerDate;
    private Timestamp endDate;
    private Collection<ActivityDTO> activities;
    private ItemDTO item;
    private EmployeeDTO manager;


    public RequestDTO(Integer id, String description, String result, String status, Timestamp registerDate, Timestamp endDate, Collection<ActivityDTO> activities, ItemDTO item, EmployeeDTO manager) {
        this.id = id;
        this.description = description;
        this.result = result;
        this.status = status;
        this.registerDate = registerDate;
        this.endDate = endDate;
        this.activities = activities;
        this.item = item;
        this.manager = manager;
    }

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

    public Collection<ActivityDTO> getActivities() {
        return activities;
    }

    public void setActivities(Collection<ActivityDTO> activities) {
        this.activities = activities;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public EmployeeDTO getManager() {
        return manager;
    }

    public void setManager(EmployeeDTO manager) {
        this.manager = manager;
    }


}
