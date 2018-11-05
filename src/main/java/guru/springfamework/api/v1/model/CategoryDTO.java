package guru.springfamework.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by jt on 9/24/17.
 */
@Data
public class CategoryDTO {
    @ApiModelProperty(value = "Categories id", required = true)
    private Long id;
    @ApiModelProperty(value = "Name of category", required = true)
    private String name;
}
