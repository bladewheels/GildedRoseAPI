package com.miw.homework.gildedrose.expanded.services;

/**
 * Stand-in service for accessing Users already registered with the mothership
 * i.e. https://gildedrose.com/accounts
 */
public interface ClientRelationshipService {

    /**
     * Bogus remote service call to validate email addresses eligible for API registration.
     *
     * @param email
     * @return true for email addresses eligible for API registration
     */
    public static boolean isShippingAndBillingSetupForThisEmailAddress(String email) {
        return email.equalsIgnoreCase("customer_x@go.to") ||
                email.equalsIgnoreCase("customer_x@the.wang") ||
                email.equalsIgnoreCase("customer_x@us.online") ||
                email.equalsIgnoreCase("customer_x@cheese.pizza");
    }
}