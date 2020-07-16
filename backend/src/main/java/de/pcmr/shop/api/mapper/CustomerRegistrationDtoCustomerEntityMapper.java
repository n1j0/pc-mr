package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.CustomerRegistrationDTO;
import de.pcmr.shop.domain.CustomerEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public class CustomerRegistrationDtoCustomerEntityMapper {
    public static @Valid CustomerEntity mapCustomerRegistrationDtoToCustomerEntity(CustomerRegistrationDTO customerRegistrationDTO) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail(customerRegistrationDTO.getEmail().trim());
        customerEntity.setFirstName(customerRegistrationDTO.getFirstName().trim());
        customerEntity.setLastName(customerRegistrationDTO.getLastName().trim());
        customerEntity.setPassword(customerRegistrationDTO.getPassword());

        return customerEntity;
    }
}
