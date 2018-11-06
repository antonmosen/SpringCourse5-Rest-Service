package guru.springfamework.service;



import guru.springfamework.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);

    CustomerDTO saveCustomerDTO(Long id, CustomerDTO customerDTO);

    CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);

    void deleteCustomerById(Long id);
}
