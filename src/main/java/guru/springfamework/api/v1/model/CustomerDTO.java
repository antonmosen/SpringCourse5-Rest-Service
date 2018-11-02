package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerDTO {

    private String firstName;
    private String lastName;
    @JsonProperty("customer_url") //set customerUrl to customer_url in Json response.
    private String customerUrl;
}