package guru.springfamework.controllers.v1;

import guru.springfamework.model.CustomerDTO;
import guru.springfamework.model.CustomerListDTO;
import guru.springfamework.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(description = "Custom controller for Customer") //@Api is swagger annotation for documentation.
@RestController //@RestController contains @Controller and @ResponseBody. No need for ResponseEntity in methods.
//ReponseEntity can be used for more control.
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {


    /*
     * We do not put this in propertiesfile since we have to bring up spring context in out tests.
     * */
    public static final String BASE_URL = "/api/v1/customers/";

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "This will get a list of customers", notes = "Some notes1") //@ApiOperation is swagger annotation for documentation
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO getAllCustomers() {

        CustomerListDTO customerListDTO = new CustomerListDTO();
        customerListDTO.getCustomers().addAll(customerService.getAllCustomers());
        return customerListDTO;
    }

    @ApiOperation(value = "Create new customer", notes = "Some notes2") //@ApiOperation is swagger annotation for documentation
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createNewCustomer(customerDTO);
    }

    @ApiOperation(value = "This will get customer base on id", notes = "Some notes3") //@ApiOperation is swagger annotation for documentation
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @ApiOperation(value = "This will update customer by id", notes = "Some notes4") //@ApiOperation is swagger annotation for documentation
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.saveCustomerDTO(id, customerDTO);
    }

    @ApiOperation(value = "This will patch customer base on id", notes = "Some notes") //@ApiOperation is swagger annotation for documentation
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(id, customerDTO);
    }

    @ApiOperation(value = "This will delete customer base on id", notes = "Some notes") //@ApiOperation is swagger annotation for documentation
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }

}
