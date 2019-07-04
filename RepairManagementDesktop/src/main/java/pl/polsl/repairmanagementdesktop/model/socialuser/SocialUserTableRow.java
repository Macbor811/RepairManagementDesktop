package pl.polsl.repairmanagementdesktop.model.socialuser;

import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;

import java.net.URI;

public class SocialUserTableRow {

    public String getEmail() {
        return email;
    }

    public String getProvider() {
        return provider;
    }

    public String getId() {
        return id;
    }

    private String email;
    private String provider;
    private String id;
    private String customer;

    public SocialUserTableRow(SocialUserEntity entity){
        this.email = entity.getEmail();
        this.provider = entity.getProvider();

        String uriString = entity.getUri().toString();
        //this.customer =  entity.getCustomer() != null ? entity.getCustomer().getFirstName() + " " + entity.getCustomer().getLastName() : null;



        //processes URI string to get resource index(ex. api/customer/3)
        this.id = uriString.substring(uriString.lastIndexOf("/") + 1);
    }
}
