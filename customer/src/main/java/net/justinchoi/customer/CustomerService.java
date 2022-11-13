package net.justinchoi.customer;

import lombok.AllArgsConstructor;
import net.justinchoi.clients.fraud.FraudCheckResponse;
import net.justinchoi.clients.fraud.FraudClient;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo: check if email is valid
        // todo: check if email is not taken yet
        customerRepository.saveAndFlush(customer);
        // todo: check if customer is fraudster
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse != null && fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster detected, blocking registration");
        }
        // todo: send notification
    }
}
