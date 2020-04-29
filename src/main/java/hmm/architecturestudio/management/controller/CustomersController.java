package hmm.architecturestudio.management.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hmm.architecturestudio.management.dto.CustomerDto;
import hmm.architecturestudio.management.exception.PrivilegesException;
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

    private CustomerDto convertToDto(Customer customer) {
        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        return customerDto;
    }
}
