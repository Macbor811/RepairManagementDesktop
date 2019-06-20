package pl.polsl.repairmanagementdesktop.model.item;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.polsl.repairmanagementdesktop.model.itemtype.ItemTypeEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import uk.co.blackpepper.bowman.InlineAssociationDeserializer;
import uk.co.blackpepper.bowman.annotation.LinkedResource;
import uk.co.blackpepper.bowman.annotation.RemoteResource;
import uk.co.blackpepper.bowman.annotation.ResourceId;

import java.net.URI;
import java.util.Collection;

@RemoteResource("/item")
public class ItemEntity {
    private URI uri;

    private String name;
    private ItemTypeEntity type;
    private CustomerEntity owner;
    private Collection<RequestEntity> requests;

    public ItemEntity(){}

    public ItemEntity(String name, ItemTypeEntity type, CustomerEntity owner) {
        this.name = name;
        this.type = type;
        this.owner = owner;
    }

    @ResourceId
    public URI getUri() {
        return uri;
    }
    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @LinkedResource
    public ItemTypeEntity getType() {
        return type;
    }
    public void setType(ItemTypeEntity type) {
        this.type = type;
    }

    @LinkedResource
    public CustomerEntity getOwner() {
        return owner;
    }
    public void setOwner(CustomerEntity owner) {
        this.owner = owner;
    }

    @JsonDeserialize(contentUsing = InlineAssociationDeserializer.class)
    public Collection<RequestEntity> getRequests() {
        return requests;
    }
    public void setRequests(Collection<RequestEntity> requests) {
        this.requests = requests;
    }



}
