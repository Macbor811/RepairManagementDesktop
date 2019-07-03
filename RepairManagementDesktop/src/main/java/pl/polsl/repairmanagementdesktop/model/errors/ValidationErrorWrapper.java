package pl.polsl.repairmanagementdesktop.model.errors;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidationErrorWrapper {

    private ValidationError[] errors;


    public ValidationError[] getErrors() {
        return errors;
    }

    public void setErrors(ValidationError[] errors) {
        this.errors = errors;
    }


}
