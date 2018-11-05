package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerDTO {

    @ApiModelProperty(value = "firstname of customer", required = true) //swagger documentation
    private String firstName;
    @ApiModelProperty(value = "lastname of customer", required = true)
    private String lastName;
    @ApiModelProperty(value = "customers url", required = true)
    @JsonProperty("customer_url") //set customerUrl to customer_url in Json response.
    private String customerUrl;
}