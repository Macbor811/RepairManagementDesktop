package pl.polsl.repairmanagementdesktop.model.request;

public class RequestUpdateDto {

    private String description;
    private String result;
    private String status;

    public RequestUpdateDto(String description, String result, String status) {
        this.description = description;
        this.result = result;
        this.status = status;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
