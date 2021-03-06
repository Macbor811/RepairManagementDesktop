package pl.polsl.repairmanagementdesktop.model.activitytype;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityEntity;
import uk.co.blackpepper.bowman.InlineAssociationDeserializer;
import uk.co.blackpepper.bowman.annotation.RemoteResource;
import uk.co.blackpepper.bowman.annotation.ResourceId;

import java.net.URI;
import java.util.Collection;
import java.util.Objects;


@RemoteResource("/activity-type")
public class ActivityTypeEntity {
    private URI uri;
    private String type;
    private Collection<ActivityEntity> activities;

    public ActivityTypeEntity () {}

    public ActivityTypeEntity(String type) {
        this.type = type;
    }

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
    public Collection<ActivityEntity> getActivities() {
        return activities;
    }
    public void setActivities(Collection<ActivityEntity> activities) {
        this.activities = activities;
    }


}
