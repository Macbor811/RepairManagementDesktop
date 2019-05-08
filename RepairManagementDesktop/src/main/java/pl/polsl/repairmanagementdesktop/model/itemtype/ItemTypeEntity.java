package pl.polsl.repairmanagementdesktop.model.itemtype;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.polsl.repairmanagementdesktop.model.item.ItemEntity;
import uk.co.blackpepper.bowman.InlineAssociationDeserializer;

import java.util.Collection;
import java.util.Objects;


public class ItemTypeEntity{
    private Integer id;
    private String type;
    private Collection<ItemEntity> items;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @JsonDeserialize(contentUsing = InlineAssociationDeserializer.class)
    public Collection<ItemEntity> getItems() {
        return items;
    }
    public void setItems(Collection<ItemEntity> items) {
        this.items = items;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemTypeEntity that = (ItemTypeEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }


}
