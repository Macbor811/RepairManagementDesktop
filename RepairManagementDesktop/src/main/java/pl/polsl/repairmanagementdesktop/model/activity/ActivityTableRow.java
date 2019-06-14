package pl.polsl.repairmanagementdesktop.model.activity;

import pl.polsl.repairmanagementdesktop.model.activitytype.ActivityTypeEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;

import java.time.LocalDateTime;

public class ActivityTableRow {

    private String id;
    private LocalDateTime registeredDate;
    private String status;
    private LocalDateTime finalizedDate;
    private String description;
   // private CustomerEntity client;
    private Integer sequenceNum;
    private String result;


    public ActivityTableRow(ActivityEntity entity) {
        String uriString = entity.getUri().toString();

        //processes URI string to get resource index(ex. api/customer/3)
        this.id = uriString.substring(uriString.lastIndexOf("/") + 1);


        this.registeredDate = entity.getRegisterDate();
        this.status = entity.getStatus();
        this.finalizedDate = entity.getEndDate();
        this.description = entity.getDescription();
        this.sequenceNum = entity.getSequenceNum();
        this.result = entity.getResult();

    }
    public String getId() { return id;}
    public LocalDateTime getRegisteredDate() { return registeredDate;}
    public String getStatus() { return status;}
    public LocalDateTime getFinalizedDate() { return finalizedDate;}
    public Integer getSequenceNum(){return sequenceNum;}
    public String getDescription() { return description;}
   // public CustomerEntity getOwner() { return client;}

}