package com.esdo.bepilot.Service.Implement;

import com.esdo.bepilot.Model.Entity.Customer;
import com.esdo.bepilot.Model.Entity.Mission;
import com.esdo.bepilot.Model.Entity.User;
import com.esdo.bepilot.Model.Request.CustomerRequest;
import com.esdo.bepilot.Model.Response.CustomerResponse;
import com.esdo.bepilot.Model.Response.UserResponse;
import com.esdo.bepilot.Repository.CustomerRepository;
import com.esdo.bepilot.Service.CustomerService;
import com.esdo.bepilot.Service.Mapper.CustomerMapper;
import com.esdo.bepilot.Specification.CustomerSpecification;
import com.esdo.bepilot.Specification.MissionSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    public CustomerRepository customerRepository ;

    @Autowired
    CustomerMapper customerMapper ;

    public CustomerResponse create(CustomerRequest request){
        log.info("Inside create of Customer Service ");
        Customer customer = customerMapper.mapToCustomerRequest(request) ;
        customer.setCustomerKey("KH");
        customer.setMissions(new ArrayList<>());
        customer.setTransaction(new ArrayList<>());
        customer.setMoneyRemaining(BigDecimal.valueOf(100000));
        customer.setMoneyAvailable(BigDecimal.valueOf(100000));
        customerRepository.save(customer) ;
        List<Customer> customerClones = customerRepository.getCustomerClone() ;
        customerClones.get(0).setCustomerKey("KH" + customerClones.get(0).getId());
        customerRepository.save(customerClones.get(0)) ;
        return null ;
    }

    public List<CustomerResponse> getAllCustomer(int pageIndex , int pageSize){
        log.info("Inside getAllCustomer of User Service ");
        Pageable paging = PageRequest.of(pageIndex, pageSize);
        Page<Customer> page = customerRepository.findAll(paging);
        List<Customer> customers = page.getContent();
        List<CustomerResponse> responses = customerMapper.mapToListCustomerEntity(customers) ;
        return responses ;

    }

    public CustomerResponse getCustomerById(Long id) {
        log.info("Inside getCustomerById of Customer Service ");
        Customer customer = customerRepository.findById(id).get() ;
        CustomerResponse customerResponse = customerMapper.mapToCustomerEntity(customer) ;
        return customerResponse ;
    }

    public String deleteCustomerById(Long id) {
        log.info("Inside deleteCustomerById of Customer Service ");
        customerRepository.deleteById(id);
        return "Delete Completed" ;
    }

    public CustomerResponse updateCustomerById(Long id, String name , String phone) {
        log.info("Inside updateCustomerById of Customer Service ");
        Customer customer = customerRepository.findById(id).get() ;
        if(customer == null) {

        }
        customerRepository.saveCustomer(id,name,phone);
        return customerMapper.mapToCustomerEntity(customerRepository.findById(id).get()) ;
    }

    public List<CustomerResponse> searchCustomer(int pageIndex , int pageSize,String keyWord){
        log.info("Inside searchCustomer of User Service ");
        Pageable paging = PageRequest.of(pageIndex, pageSize);
//        Page<Customer> page = customerRepository.searchCustomer(paging,keyWord);
        Page<Customer> page = customerRepository.findAll(CustomerSpecification.filterCustomer(keyWord), paging);
        List<Customer> customers = page.getContent();
        List<CustomerResponse> responses = customerMapper.mapToListCustomerEntity(customers) ;
        return responses ;

    }
}

