package pl.polsl.repairmanagementdesktop.model.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import pl.polsl.repairmanagementdesktop.abstr.TableRow;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;
import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Page;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class EmployeeTableRow implements TableRow {

    private String id;
    private String firstName;
    private String lastName;
    private String role;
    private String phoneNumber;
    private String username;
    private Instant deactivationDate;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");


    public EmployeeTableRow(EmployeeEntity entity) {
        String uriString = entity.getUri().toString();

        //processes URI string to get resource index(ex. api/customer/3)
        this.id = uriString.substring(uriString.lastIndexOf("/") + 1);

        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.role = entity.getRole();
        this.phoneNumber = entity.getPhoneNumber();
        this.username = entity.getUsername();
        this.deactivationDate = entity.getDeactivationDate();
    }
    public String getId() { return id;}
    public String getDeactivationDate() {
        if (deactivationDate != null ){
            return DATE_FORMATTER.format(deactivationDate.atZone(ZoneId.systemDefault()).toLocalDateTime());
        } else {
            return null;
        }
    }
    public String getFirstName() { return firstName;}
    public String getLastName() { return lastName;}
    public String getUsername() { return username;}
    public String getPhoneNumber() { return phoneNumber;}
    public String getRole() { return role;}
}
