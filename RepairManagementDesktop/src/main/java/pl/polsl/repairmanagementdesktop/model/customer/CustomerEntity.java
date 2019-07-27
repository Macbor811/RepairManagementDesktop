package pl.polsl.repairmanagementdesktop.model.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.polsl.repairmanagementdesktop.abstr.Entity;
import pl.polsl.repairmanagementdesktop.model.item.ItemEntity;
import pl.polsl.repairmanagementdesktop.model.address.AddressEntity;
import pl.polsl.repairmanagementdesktop.model.socialuser.SocialUserEntity;
import uk.co.blackpepper.bowman.InlineAssociationDeserializer;
import uk.co.blackpepper.bowman.annotation.LinkedResource;
import uk.co.blackpepper.bowman.annotation.RemoteResource;
import uk.co.blackpepper.bowman.annotation.ResourceId;

import java.net.URI;
import java.util.Collection;
import java.util.Objects;


@RemoteResource("/customer")
public class CustomerEntity implements Entity {


    private URI uri;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private AddressEntity address;

    @LinkedResource
    public SocialUserEntity getSocialUserEntity() {
        return socialUserEntity;
    }

    public void setSocialUserEntity(SocialUserEntity socialUserEntity) {
        this.socialUserEntity = socialUserEntity;
    }

    private SocialUserEntity socialUserEntity;
    private Collection<ItemEntity> items;

    public CustomerEntity(){}
    public CustomerEntity(String firstName, String lastName, String phoneNumber, AddressEntity address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }


    @ResourceId
    public URI getUri() {
        return uri;
    }
    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getPostCode(){
        return getAddress().getPostCode();
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

    @LinkedResource
    public AddressEntity getAddress() {
        return address;
    }
    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    @JsonDeserialize(contentUsing = InlineAssociationDeserializer.class)
    public Collection<ItemEntity> getItems() {
        return items;
    }
    public void setItems(Collection<ItemEntity> items) {
        this.items = items;
    }

}
