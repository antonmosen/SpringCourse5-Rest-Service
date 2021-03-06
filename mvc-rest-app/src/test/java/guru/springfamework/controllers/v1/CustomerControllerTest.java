package guru.springfamework.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springfamework.model.CustomerDTO;
import guru.springfamework.service.CustomerService;
import guru.springfamework.service.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Ignore;
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

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()) // Needed for custom exceptionhandling. controlleradvice.
                .build();
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

        mockMvc.perform(get(getCustomerUrlWithoutId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2))); // $-sign is the root and we are searching for customers.
    }

    @Test
    public void getCustomerById() throws Exception {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Tom");
        customerDTO.setLastName("Cruise");
        customerDTO.setCustomerUrl(getCustomerUrlWithId(1L));

        when(customerService.getCustomerById(1L)).thenReturn(customerDTO);

        mockMvc.perform(get(getCustomerUrlWithId(1L))
                .accept(MediaType.APPLICATION_JSON)
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
        returnedCustomerDto.setCustomerUrl(getCustomerUrlWithId(1L));

        when(customerService.createNewCustomer(any())).thenReturn(returnedCustomerDto);

        mockMvc.perform(post(getCustomerUrlWithoutId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("Tom")))
                .andExpect(jsonPath("$.lastName", equalTo("Cruise")))
                .andExpect(jsonPath("$.customerUrl", equalTo(getCustomerUrlWithId(1L)))); //Look at CustomerDTO and @JsonProperty("customer_url")
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
        returnDTO.setCustomerUrl(getCustomerUrlWithId(1L));

        when(customerService.saveCustomerDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when
        mockMvc.perform(put(getCustomerUrlWithId(1L))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Tom")))
                .andExpect(jsonPath("$.lastName", equalTo("Cruise")))
                .andExpect(jsonPath("$.customerUrl", equalTo(getCustomerUrlWithId(1L))));
    }

    @Test
    public void testPatchCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Tom");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName("Cruise");
        returnDTO.setCustomerUrl(getCustomerUrlWithId(1L));

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when
        mockMvc.perform(patch(getCustomerUrlWithId(1L))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Tom")))
                .andExpect(jsonPath("$.lastName", equalTo("Cruise")))
                .andExpect(jsonPath("$.customerUrl", equalTo(getCustomerUrlWithId(1L))));
    }

    @Test
    public void testDeleteCustomerById() throws Exception {
        mockMvc.perform(delete(getCustomerUrlWithId(1L))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }

    @Test
    public void testGetCustomerByByIdNotFound() throws Exception {

        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(getCustomerUrlWithId(100L))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getCustomerUrlWithId(Long id) {
        return CustomerController.BASE_URL + "/" + id;
    }

    private String getCustomerUrlWithoutId() {
        return CustomerController.BASE_URL;
    }

}