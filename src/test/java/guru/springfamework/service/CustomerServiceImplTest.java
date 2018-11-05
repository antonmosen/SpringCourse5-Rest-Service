package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;
    private CustomerService customerService;
    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    private List<Customer> customers = new ArrayList<>();

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, customerMapper);
        customers = initCustomers();
    }

    @Test
    public void getAllCustomers() {

        //given
        when(customerRepository.findAll()).thenReturn(customers);

        //when
        List<CustomerDTO> customers = customerService.getAllCustomers();

        //then
        assertEquals(2, customers.size());
    }


    @Test
    public void getCustomerById() {

        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(customers.get(0)));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        assertEquals("Tom", customerDTO.getFirstName());
    }

    @Test
    public void createNewCustomer() {

        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Tom");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedCustomerDTO = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getFirstName(), savedCustomerDTO.getFirstName());
        assertEquals("api/v1/customers/1", savedCustomerDTO.getCustomerUrl());

    }

    @Test
    public void deleteCustomerById() {

        Long customerId = 1L;

        customerRepository.deleteById(customerId);

        verify(customerRepository, times(1)).deleteById(anyLong());

    }

    private List<Customer> initCustomers() {

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Tom");
        customer1.setLastName("Cruise");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Michael");
        customer2.setLastName("Bolton");

        return Arrays.asList(customer1, customer2);
    }
}