package nz.co.willcox.reservation.model;

import nz.co.willcox.reservation.service.AbstractService;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Rsvp {

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL_ADDRESS = "emailAddress";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String NUMBER_OF_RSVP_PEOPLE = "numberOfRsvpPeople";
    private static final String IS_VAXED = "isVaxed";

    private String numberOfRsvpPeople;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String isVaxed;

    public static Rsvp from(Map<String, AttributeValue> item) {
        final Rsvp rsvp = new Rsvp();
        if (item != null && !item.isEmpty()) {
            rsvp.setFirstName(item.get(FIRST_NAME).s());
            rsvp.setLastName(item.get(LAST_NAME).s());
            rsvp.setEmailAddress(item.get(EMAIL_ADDRESS).s());
            rsvp.setPhoneNumber(item.get(PHONE_NUMBER).s());
            rsvp.setNumberOfRsvpPeople(item.get(NUMBER_OF_RSVP_PEOPLE).s());
            rsvp.setIsVaxed(item.get(IS_VAXED).s());
        }
        return rsvp;
    }

    public String getNumberOfRsvpPeople() {
        return numberOfRsvpPeople;
    }

    public void setNumberOfRsvpPeople(String numberOfRsvpPeople) {
        this.numberOfRsvpPeople = numberOfRsvpPeople;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIsVaxed() {
        return isVaxed;
    }

    public void setIsVaxed(String isVaxed) {
        this.isVaxed = isVaxed;
    }
}
