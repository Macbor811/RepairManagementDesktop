package pl.polsl.repairmanagementdesktop.model.address;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDTO {

    private Integer id;
    private String postCode;
    private String city;
    private String street;
    private Integer number;

    public AddressDTO(){

    }

    public AddressDTO(Integer id, String postCode, String city, String street, Integer number) {
        this.id = id;
        this.postCode = postCode;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "id=" + id +
                ", postCode='" + postCode + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number +
                '}';
    }
}
