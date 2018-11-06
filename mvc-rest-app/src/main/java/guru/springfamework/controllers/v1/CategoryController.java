package guru.springfamework.controllers.v1;


import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CategoryListDTO;
import guru.springfamework.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(description = "Custom controller for Category") //@Api is swagger annotation for documentation.
@RestController //@RestController contains @Controller and @ResponseBody. No need for ResponseEntity in methods.
//ReponseEntity can be used for more control.
//@RequestMapping(value = CategoryController.BASE_URL, produces = "application/json") --> if you want response in Json instead of xml
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

    /*
     * We do not put this in propertiesfile since we have to bring up spring context in out tests.
     * */
    public static final String BASE_URL = "/api/v1/categories/";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "This will get a list of categories", notes = "Some notes1") //@ApiOperation is swagger annotation for documentation
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO getAllCategories() {
        return new CategoryListDTO(categoryService.getAllCategories());
    }

    @ApiOperation(value = "This will get category by its name", notes = "Some notes1") //@ApiOperation is swagger annotation for documentation
    @GetMapping("{name}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoriesByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }
}

//Old method implementation.

//    @GetMapping("{name}")
//    public ResponseEntity<CategoryDTO> getCategoriesByName(@PathVariable String name) {
//        return new ResponseEntity<>(
//                categoryService.getCategoryByName(name), HttpStatus.OK);
//    }
