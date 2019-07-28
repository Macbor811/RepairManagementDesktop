package pl.polsl.repairmanagementdesktop.model.employee;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import pl.polsl.repairmanagementdesktop.abstr.Entity;
import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityEntity;
import pl.polsl.repairmanagementdesktop.model.address.AddressEntity;
import pl.polsl.repairmanagementdesktop.utils.InstantDeserializer;
import uk.co.blackpepper.bowman.InlineAssociationDeserializer;
import uk.co.blackpepper.bowman.annotation.LinkedResource;
import uk.co.blackpepper.bowman.annotation.RemoteResource;
import uk.co.blackpepper.bowman.annotation.ResourceId;

import java.net.URI;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;

@RemoteResource("/employee")
public class EmployeeEntity implements Entity {

    private URI uri;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String role;
    private String username;
    private String password;
    private Instant deactivationDate;
    private Collection<ActivityEntity> activities;
    private AddressEntity address;
    private Collection<RequestEntity> requests;

    public EmployeeEntity(){}
    public EmployeeEntity(String firstName, String lastName, String phoneNumber, String role, String username, String password, AddressEntity address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.username = username;
        this.password = password;
        this.address = address;
    }

    @ResourceId
    public URI getUri() {
        return uri;
    }
    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    public Instant getDeactivationDate() {
        return deactivationDate;
    }
    public void setDeactivationDate(Instant deactivationDate) {
        this.deactivationDate = deactivationDate;
    }

    @JsonDeserialize(contentUsing = InlineAssociationDeserializer.class)
    public Collection<ActivityEntity> getActivities() {
        return activities;
    }
    public void setActivities(Collection<ActivityEntity> activities) {
        this.activities = activities;
    }

    @LinkedResource
    public AddressEntity getAddress() {
        return address;
    }
    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    @JsonDeserialize(contentUsing = InlineAssociationDeserializer.class)
    public Collection<RequestEntity> getRequests() {
        return requests;
    }
    public void setRequests(Collection<RequestEntity> requests) {
        this.requests = requests;
    }



}
