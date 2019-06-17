package pl.polsl.repairmanagementdesktop.model.request;

import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;

import java.time.LocalDateTime;

public class RequestTableRow {


    private String id;
    private LocalDateTime registeredDate;
    private String status;
    private LocalDateTime finalizedDate;
    private String description;
    private String result;
    private CustomerEntity client;

    public RequestTableRow(RequestEntity entity) {
        String uriString = entity.getUri().toString();

        //processes URI string to get resource index(ex. api/customer/3)
        this.id = uriString.substring(uriString.lastIndexOf("/") + 1);


        this.registeredDate = entity.getRegisterDate();
        this.status = entity.getStatus();
        this.finalizedDate = entity.getEndDate();
        this.description = entity.getDescription();
        this.result = entity.getResult();
        this.client = entity.getItem().getOwner();

    }
    public String getId() { return id;}
    public LocalDateTime getRegisteredDate() { return registeredDate;}
    public String getStatus() { return status;}
    public LocalDateTime getFinalizedDate() { return finalizedDate;}
    public String getDescription() { return description;}
    public String getResult() { return result;}
     public String getClient() { return client.getFirstName()+" "+client.getLastName();}

}
