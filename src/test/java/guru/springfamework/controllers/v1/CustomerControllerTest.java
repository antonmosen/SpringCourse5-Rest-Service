package guru.springfamework.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    private static final String API_V1_CUSTOMER = "/api/v1/customers/";

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        //Not needed since we are doing @InjectMocks
//        customerController = new CustomerController(customerService);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getAllCustomers() throws Exception {

        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstName("Flash");
        customerDTO1.setLastName("Gordon");

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFirstName("Tom");
        customerDTO2.setLastName("Cruise");

        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customerDTO1, customerDTO2));

        mockMvc.perform(get(API_V1_CUSTOMER)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2))); // $-sign is the root and we are searching for customers.
    }

    @Test
    public void getCustomerById() throws Exception {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Tom");
        customerDTO.setLastName("Cruise");
        customerDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.getCustomerById(1L)).thenReturn(customerDTO);

        mockMvc.perform(get(API_V1_CUSTOMER + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Tom")));
    }

    @Test
    public void createNewCustomer() throws Exception {

        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Tom");
        customer.setLastName("Cruise");

        CustomerDTO returnedCustomerDto = new CustomerDTO();
        returnedCustomerDto.setFirstName(customer.getFirstName());
        returnedCustomerDto.setLastName(customer.getLastName());
        returnedCustomerDto.setCustomerUrl("/api/v1/customers/1");

        when(customerService.createNewCustomer(customer)).thenReturn(returnedCustomerDto);

        mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("Tom")))
                .andExpect(jsonPath("$.lastName", equalTo("Cruise")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1"))); //Look at CustomerDTO and @JsonProperty("customer_url")
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Tom");
        customer.setLastName("Cruise");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName(customer.getLastName());
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.saveCustomerDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when
        mockMvc.perform(put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Tom")))
                .andExpect(jsonPath("$.lastName", equalTo("Cruise")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));

    }

    @Test
    public void testPatchCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Tom");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName("Cruise");
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when
        mockMvc.perform(patch("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Tom")))
                .andExpect(jsonPath("$.lastName", equalTo("Cruise")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testDeleteCustomerById() throws Exception {
        mockMvc.perform(delete("/api/v1/customers/1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}