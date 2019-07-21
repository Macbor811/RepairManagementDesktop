package pl.polsl.repairmanagementdesktop.model.activity;

import pl.polsl.repairmanagementdesktop.model.activitytype.ActivityTypeEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;
import pl.polsl.repairmanagementdesktop.utils.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ActivityTableRow {

    private String id;
    private LocalDateTime registeredDate;
    private String status;
    private LocalDateTime finalizedDate;
    private String description;
    private Integer sequenceNum;
    private String result;
    private EmployeeEntity worker;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private ActivityTableRow(){}
    public static ActivityTableRow sizeSetter(){
        var row = new ActivityTableRow();
        row.id = "123";
        row.result = "aaaaaaaaaaaaa";
        row.status = row.result;
        row.registeredDate = Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
        row.finalizedDate = row.registeredDate;
        row.sequenceNum = 11;
        var employee = new EmployeeEntity();
        employee.setFirstName("aaaaaaaaaa");
        employee.setLastName("aaaaaaaaaa");
        return row;
    }


    public ActivityTableRow(ActivityEntity entity) {
        String uriString = entity.getUri().toString();

        //processes URI string to get resource index(ex. api/customer/3)
        this.id = uriString.substring(uriString.lastIndexOf("/") + 1);

        var requestId = entity.getRequest().getUri().toString().substring(uriString.lastIndexOf("/") + 1);

        this.registeredDate = entity.getRegisterDate() != null ? entity.getRegisterDate().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
        this.status = entity.getStatus();
        this.finalizedDate = entity.getEndDate() != null ? entity.getEndDate().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
        this.description = entity.getDescription();
        this.sequenceNum = entity.getSequenceNum();
        this.result = entity.getResult();
        this.worker = entity.getWorker();
    }
    public String getId() { return id;}

    public String getRegisteredDate() { return registeredDate != null ? DATE_FORMATTER.format(registeredDate) : null;}
    public String getStatus() { return status;}
    public String getFinalizedDate() {  return finalizedDate != null ? DATE_FORMATTER.format(finalizedDate) : null;}
    public Integer getSequenceNum(){return sequenceNum;}
    public String getDescription() { return StringUtils.trim(description, 20);}
    public String getResult() {
        return StringUtils.trim(result, 20);
    }
    public String getWorker(){return worker.getFirstName() + " " + worker.getLastName();}

}