package pl.polsl.repairmanagementdesktop.model.request;

import pl.polsl.repairmanagementdesktop.abstr.TableRow;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.item.ItemEntity;
import pl.polsl.repairmanagementdesktop.utils.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class RequestTableRow implements TableRow {


    private final String id;
    private final LocalDateTime registeredDate;
    private final String status;
    private final LocalDateTime finalizedDate;
    private final String description;
    private final String result;
    private final ItemEntity item;
    private final CustomerEntity client;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");


    public RequestTableRow(RequestEntity entity) {
        String uriString = entity.getUri().toString();

        //processes URI string to get resource index(ex. api/customer/3)
        this.id = uriString.substring(uriString.lastIndexOf("/") + 1);


        this.registeredDate = entity.getRegisterDate() != null ? entity.getRegisterDate().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
        this.status = entity.getStatus();
        this.finalizedDate = entity.getEndDate() != null ? entity.getEndDate().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
        this.description = entity.getDescription();
        this.result = entity.getResult();
        this.client = entity.getItem().getOwner();
        this.item = entity.getItem();

    }
    public String getId() { return id;}
    public String getRegisteredDate() { return registeredDate != null ? DATE_FORMATTER.format(registeredDate) : null;}
    public String getStatus() { return status;}
    public String getFinalizedDate() {  return finalizedDate != null ? DATE_FORMATTER.format(finalizedDate) : null;}
    public String getDescription() {  return StringUtils.trim(description, 15);}
    public String getResult() {  return StringUtils.trim(result, 15);}
    public String getClient() { return client.getFirstName() +" "+ client.getLastName();}
    public String getItem() {
        return item.getType().getType() + " " + item.getName();
    }
}
