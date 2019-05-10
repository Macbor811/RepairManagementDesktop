package pl.polsl.repairmanagementdesktop.model.itemtype;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.polsl.repairmanagementdesktop.model.item.ItemEntity;
import uk.co.blackpepper.bowman.InlineAssociationDeserializer;
import uk.co.blackpepper.bowman.annotation.ResourceId;

import java.net.URI;
import java.util.Collection;
import java.util.Objects;


public class ItemTypeEntity{

    private URI uri;
    private String type;
    private Collection<ItemEntity> items;

    @ResourceId
    public URI getUri() {
        return uri;
    }
    public void setUri(URI uri) {
        this.uri = uri;
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




}
