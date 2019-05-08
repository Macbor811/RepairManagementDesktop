package pl.polsl.repairmanagementdesktop.model.activitytype;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityEntity;
import uk.co.blackpepper.bowman.InlineAssociationDeserializer;

import java.util.Collection;
import java.util.Objects;


public class ActivityTypeEntity {
    private Integer id;
    private String type;
    private Collection<ActivityEntity> activities;

    public ActivityTypeEntity(String type) {
        this.type = type;
    }


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityTypeEntity that = (ActivityTypeEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @JsonDeserialize(contentUsing = InlineAssociationDeserializer.class)
    public Collection<ActivityEntity> getActivities() {
        return activities;
    }
    public void setActivities(Collection<ActivityEntity> activities) {
        this.activities = activities;
    }


}
