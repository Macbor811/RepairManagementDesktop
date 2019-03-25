package pl.polsl.repairmanagementdesktop.model.client;



import pl.polsl.repairmanagementdesktop.model.address.AddressDTO;
import pl.polsl.repairmanagementdesktop.model.item.ItemDTO;

import java.util.Collection;

public class ClientDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private AddressDTO address;
    private Collection<ItemDTO> items;

    public ClientDTO(String firstName, String lastName, String phoneNumber, AddressDTO address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public Collection<ItemDTO> getItems() {
        return items;
    }

    public void setItems(Collection<ItemDTO> items) {
        this.items = items;
    }

}

