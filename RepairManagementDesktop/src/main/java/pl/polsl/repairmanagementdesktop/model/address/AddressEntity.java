package pl.polsl.repairmanagementdesktop.model.address;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import uk.co.blackpepper.bowman.InlineAssociationDeserializer;

import java.util.Collection;
import java.util.Objects;


public class AddressEntity {
    private Integer id;
    private String postCode;
    private String city;
    private String street;
    private String number;
    private Collection<CustomerEntity> customers;
    private Collection<EmployeeEntity> employees;

    public AddressEntity(String postCode, String city, String street, String number, Collection<CustomerEntity> customers, Collection<EmployeeEntity> employees) {
        this.postCode = postCode;
        this.city = city;
        this.street = street;
        this.number = number;
        this.customers = customers;
        this.employees = employees;
    }

    public AddressEntity(){

    }


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPostCode() {
        return postCode;
    }
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(postCode, that.postCode) &&
                Objects.equals(city, that.city) &&
                Objects.equals(street, that.street) &&
                Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postCode, city, street, number);
    }

    @JsonDeserialize(contentUsing = InlineAssociationDeserializer.class)
    public Collection<CustomerEntity> getCustomers() {
        return customers;
    }
    public void setCustomers(Collection<CustomerEntity> customers) {
        this.customers = customers;
    }

    @JsonDeserialize(contentUsing = InlineAssociationDeserializer.class)
    public Collection<EmployeeEntity> getEmployees() {
        return employees;
    }
    public void setEmployees(Collection<EmployeeEntity> employees) {
        this.employees = employees;
    }


}
