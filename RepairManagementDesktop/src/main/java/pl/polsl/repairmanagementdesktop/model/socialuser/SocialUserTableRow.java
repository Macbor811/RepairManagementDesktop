package pl.polsl.repairmanagementdesktop.model.socialuser;

import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;

import java.net.URI;

public class SocialUserTableRow {

    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String provider;
    private String id;

    public SocialUserTableRow(SocialUserEntity entity){
        this.email = entity.getEmail();
        this.provider = entity.getProvider();

        String uriString = entity.getUri().toString();

        //processes URI string to get resource index(ex. api/customer/3)
        this.id = uriString.substring(uriString.lastIndexOf("/") + 1);
    }
}
