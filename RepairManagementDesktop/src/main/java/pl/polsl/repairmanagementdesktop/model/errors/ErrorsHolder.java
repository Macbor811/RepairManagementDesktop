package pl.polsl.repairmanagementdesktop.model.errors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorsHolder {

    @JsonProperty(value = "errors")
    private ValidationErrorWrapper errors;

    public ValidationErrorWrapper getErrors() {
        return errors;
    }

    public void setErrors(ValidationErrorWrapper errors) {
        this.errors = errors;
    }
}


