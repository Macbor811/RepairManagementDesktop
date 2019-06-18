package pl.polsl.repairmanagementdesktop.model.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;
import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Page;

import java.net.URI;
import java.time.LocalDateTime;


public class EmployeeTableRow {

    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private LocalDateTime deactivationDate;

    public EmployeeTableRow(EmployeeEntity entity) {
        String uriString = entity.getUri().toString();

        //processes URI string to get resource index(ex. api/customer/3)
        this.id = uriString.substring(uriString.lastIndexOf("/") + 1);

        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.phoneNumber = entity.getPhoneNumber();
        this.username = entity.getUsername();
        this.deactivationDate = entity.getDeactivationDate();

    }
    public String getId() { return id;}
    public LocalDateTime getDeactivationDate() { return deactivationDate;}
    public String getFirstName() { return firstName;}
    public String getLastName() { return lastName;}
    public String getUsername() { return username;}
    public String getPhoneNumber() { return phoneNumber;}

}
