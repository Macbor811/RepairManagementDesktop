package pl.polsl.repairmanagementdesktop.model.request;

import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.item.ItemEntity;

import java.time.LocalDateTime;

public class RequestTableRow {


    private final String id;
    private final LocalDateTime registeredDate;
    private final String status;
    private final LocalDateTime finalizedDate;
    private final String description;
    private final String result;
    private final ItemEntity item;
    private final CustomerEntity client;

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
        this.item = entity.getItem();

    }
    public String getId() { return id;}
    public LocalDateTime getRegisteredDate() { return registeredDate;}
    public String getStatus() { return status;}
    public LocalDateTime getFinalizedDate() { return finalizedDate;}
    public String getDescription() { return description;}
    public String getResult() { return result;}
    public String getClient() { return client.getFirstName() +" "+ client.getLastName();}
    public String getItem() {
        return item.getType().getType() + " " + item.getName();
    }
}
