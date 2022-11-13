package net.justinchoi.customer;

import lombok.AllArgsConstructor;
import net.justinchoi.clients.fraud.FraudCheckResponse;
import net.justinchoi.clients.fraud.FraudClient;
import net.justinchoi.clients.notification.NotificationClient;
import net.justinchoi.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;
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

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                "Successfully registered!"
        );
        notificationClient.sendNotification(notificationRequest);
    }
}
