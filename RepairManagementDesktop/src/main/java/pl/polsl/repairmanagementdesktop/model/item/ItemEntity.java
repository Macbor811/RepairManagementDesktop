package pl.polsl.repairmanagementdesktop.model.item;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.polsl.repairmanagementdesktop.model.itemtype.ItemTypeEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import uk.co.blackpepper.bowman.InlineAssociationDeserializer;
import uk.co.blackpepper.bowman.annotation.LinkedResource;

import java.util.Collection;
import java.util.Objects;


public class ItemEntity {
    private Integer id;
    private String name;
    private ItemTypeEntity itemType;
    private CustomerEntity owner;
    private Collection<RequestEntity> requests;

    public ItemEntity(){}

    public ItemEntity(String name, ItemTypeEntity itemType, CustomerEntity owner) {
        this.name = name;
        this.itemType = itemType;
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @LinkedResource
    public ItemTypeEntity getItemType() {
        return itemType;
    }
    public void setItemType(ItemTypeEntity itemType) {
        this.itemType = itemType;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntity that = (ItemEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


}
