package hmm.architecturestudio.management.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hmm.architecturestudio.management.dto.CustomerDto;
import hmm.architecturestudio.management.exception.PrivilegesException;
import hmm.architecturestudio.management.exception.ValidationServiceException;
import hmm.architecturestudio.management.model.Customer;
import hmm.architecturestudio.management.service.CustomersService;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomersService customersService;

    @GetMapping
    @ResponseBody
    public List<CustomerDto> getCustomers() throws Exception {
        List<Customer> customers = customersService.findAll();
        return customers.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
	@ResponseBody
	public CustomerDto getCustomer(@PathVariable("id") Long id) throws Exception {
	    Optional<Customer> customer = customersService.findById(id);
	    return convertToDto(customer.get());
	}
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CustomerDto createCustomer(@Valid @RequestBody CustomerDto customerDto) throws Exception {
        Customer customer = convertToEntity(customerDto);
        Customer createdCustomer = customersService.createCustomer(customer);

        return convertToDto(createdCustomer);
    }
    
    private CustomerDto convertToDto(Customer customer) {
        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        return customerDto;
    }
    
    private Customer convertToEntity(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        return customer;
    }
}
