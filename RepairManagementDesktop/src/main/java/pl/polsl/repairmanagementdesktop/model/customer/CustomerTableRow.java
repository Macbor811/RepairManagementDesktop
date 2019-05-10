package pl.polsl.repairmanagementdesktop.model.customer;

import pl.polsl.repairmanagementdesktop.model.address.AddressEntity;

import java.net.URI;

public class CustomerTableRow {

    private Integer id;
    private URI uri;
    private String firstName;

    private String lastName;
    private String phoneNumber;
    private String postCode;
    private String city;
    private String street;
    private String number;


    public CustomerTableRow(CustomerEntity entity){
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.phoneNumber = entity.getPhoneNumber();

        AddressEntity address = entity.getAddress();
        this.postCode = address.getPostCode();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.number = address.getNumber();
    }


    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getPostCode() {
        return postCode;
    }
    public String getCity() {
        return city;
    }
    public String getStreet() {
        return street;
    }
    public String getNumber() {
        return number;
    }

}



