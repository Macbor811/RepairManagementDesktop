package pl.polsl.repairmanagementdesktop.model.item;


import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.itemtype.ItemTypeEntity;

public class ItemTableRow {

    private String id;
    private String name;
    private ItemTypeEntity type;
    private CustomerEntity client;

    public ItemTableRow(ItemEntity entity){
        String uriString =  entity.getUri().toString();

        //processes URI string to get resource index(ex. api/customer/3)
        this.id = uriString.substring(uriString.lastIndexOf("/") + 1);

        this.name = entity.getName();
        this.type = entity.getItemType();
        this.client = entity.getOwner();

    }


    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type.getType();
    }
    public String getClient() {
        return client.getFirstName() + " " + client.getLastName();
    }

}
