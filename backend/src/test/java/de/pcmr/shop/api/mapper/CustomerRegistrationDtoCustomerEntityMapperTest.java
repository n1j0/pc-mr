package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.CustomerRegistrationDTO;
import de.pcmr.shop.domain.CustomerEntity;
import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerRegistrationDtoCustomerEntityMapperTest {
    private final static String CUSTOMER_EMAIL = "test@test.com";
    private final static String CUSTOMER_FIRSTNAME = "John";
    private final static String CUSTOMER_FIRSTNAME_HTML = "<script>John</script>";
    private final static String CUSTOMER_LASTNAME = "Smith";
    private final static String CUSTOMER_PASSWORD = "MyS3cr3tP@ssw0rd";

    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();

    CustomerRegistrationDTO customerRegistrationDTO;
    CustomerEntity customerEntity;

    @Test
    public void testMapCustomerEntityToCustomerDetailsDtoSuccess() {
        given.aCustomerRegistrationDtoWith(CUSTOMER_EMAIL, CUSTOMER_FIRSTNAME, CUSTOMER_LASTNAME, CUSTOMER_PASSWORD);
        when.aCustomerRegistrationDtoIsMappedToEntity(customerRegistrationDTO);
        then.theAttributesOfTheCustomerAreEqualTo(customerRegistrationDTO);
    }

    @Test
    public void testMapCustomerEntityToCustomerDetailsDtoFailHtml() {
        given.aCustomerRegistrationDtoWith(CUSTOMER_EMAIL, CUSTOMER_FIRSTNAME_HTML, CUSTOMER_LASTNAME, CUSTOMER_PASSWORD);
        assertThrows(ValidationException.class, () -> when.aCustomerRegistrationDtoIsMappedToEntity(customerRegistrationDTO));
    }

    class Given {
        public void aCustomerRegistrationDtoWith(String email, String firstname, String lastname, String password) {
            customerRegistrationDTO = new CustomerRegistrationDTO();
            customerRegistrationDTO.setEmail(email);
            customerRegistrationDTO.setFirstName(firstname);
            customerRegistrationDTO.setLastName(lastname);
            customerRegistrationDTO.setPassword(password);
        }
    }

    class When {
        public void aCustomerRegistrationDtoIsMappedToEntity(CustomerRegistrationDTO customerRegistrationDTO) {
            customerEntity = CustomerRegistrationDtoCustomerEntityMapper.mapCustomerRegistrationDtoToCustomerEntity(customerRegistrationDTO);
        }
    }

    class Then {
        public void theAttributesOfTheCustomerAreEqualTo(CustomerRegistrationDTO customerRegistrationDTO) {
            assertEquals(customerRegistrationDTO.getEmail(), customerEntity.getEmail());
            assertEquals(customerRegistrationDTO.getFirstName(), customerEntity.getFirstName());
            assertEquals(customerRegistrationDTO.getLastName(), customerEntity.getLastName());
            assertEquals(customerRegistrationDTO.getPassword(), customerEntity.getPassword());
        }
    }
}
