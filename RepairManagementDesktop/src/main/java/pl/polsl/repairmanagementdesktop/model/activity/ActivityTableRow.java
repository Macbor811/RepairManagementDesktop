package pl.polsl.repairmanagementdesktop.model.activity;

import pl.polsl.repairmanagementdesktop.abstr.TableRow;
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

public class ActivityTableRow implements TableRow {

    private Integer id;
    private LocalDateTime registeredDate;
    private String status;
    private LocalDateTime finalizedDate;
    private String description;
    private Integer sequenceNum;
    private String result;
    private String type;
    private EmployeeEntity worker;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private ActivityTableRow(){}


    public ActivityTableRow(ActivityEntity entity) {
        String uriString = entity.getUri().toString();

        //processes URI string to get resource index(ex. api/customer/3)
        this.id = Integer.parseInt(uriString.substring(uriString.lastIndexOf("/") + 1));

        var requestId = entity.getRequest().getUri().toString().substring(uriString.lastIndexOf("/") + 1);

        this.registeredDate = entity.getRegisterDate() != null ? entity.getRegisterDate().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
        this.status = entity.getStatus();
        this.finalizedDate = entity.getEndDate() != null ? entity.getEndDate().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
        this.description = entity.getDescription();
        this.sequenceNum = entity.getSequenceNum();
        this.result = entity.getResult();
        this.worker = entity.getWorker();
        this.type = entity.getActivityType().getType();
    }
    public Integer getId() { return id;}

    public String getRegisteredDate() { return registeredDate != null ? DATE_FORMATTER.format(registeredDate) : null;}
    public String getStatus() { return status;}
    public String getFinalizedDate() {  return finalizedDate != null ? DATE_FORMATTER.format(finalizedDate) : null;}
    public Integer getSequenceNum(){return sequenceNum;}
    public String getDescription() { return description; }
    public String getResult() {
        return result;
    }
    public String getWorker(){return  worker != null ? worker.getFirstName() + " " + worker.getLastName() : null;}

    public EmployeeEntity getWorkerEntity(){
        return worker;
    }

    public String getWorkerId(){
        if (worker != null){
            var id = worker.getUri().toString();
            return id.substring(id.lastIndexOf("/") + 1);
        } else {
            return null;
        }
    }
    public String getType() {
        return type;
    }


    public void reorder(Integer delta){
        this.sequenceNum += delta;
    }
}