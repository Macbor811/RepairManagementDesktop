package pl.polsl.repairmanagementdesktop.model.customer;

import pl.polsl.repairmanagementdesktop.model.address.AddressEntity;

import java.net.URI;

public class CustomerTableRow {


    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String postCode;
    private String city;
    private String street;
    private String number;


    public CustomerTableRow(CustomerEntity entity){
        String uriString =  entity.getUri().toString();

        //processes URI string to get resource index(ex. api/customer/3)
        this.id = Integer.parseInt(uriString.substring(uriString.lastIndexOf("/") + 1));

        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.phoneNumber = entity.getPhoneNumber();

        AddressEntity address = entity.getAddress();
        this.postCode = address.getPostCode();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.number = address.getNumber();
    }


    public Integer getId() {
        return id;
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



