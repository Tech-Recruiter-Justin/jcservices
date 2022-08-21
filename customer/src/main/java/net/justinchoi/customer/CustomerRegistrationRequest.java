package net.justinchoi.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
