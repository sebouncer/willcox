package nz.co.willcox.reservation.model;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;

public class Rsvp {

    private static final String FULL_NAME = "fullName";
    private static final String ATTENDING = "attending";
    private static final String EMAIL_ADDRESS = "emailAddress";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String VACCINATED = "vaccinated";
    private static final String CREATED_AT = "createdAt";

    private String fullName;
    private String attending;
    private String emailAddress;
    private String phoneNumber;
    private String vaccinated;
    private String createdAt;

    public static Rsvp from(Map<String, AttributeValue> item) {
        final Rsvp rsvp = new Rsvp();
        if (item != null && !item.isEmpty()) {
            rsvp.setFullName(getAttributeValue(item, FULL_NAME));
            rsvp.setAttending(getAttributeValue(item, ATTENDING));
            rsvp.setEmailAddress(getAttributeValue(item, EMAIL_ADDRESS));
            rsvp.setPhoneNumber(getAttributeValue(item, PHONE_NUMBER));
            rsvp.setVaccinated(getAttributeValue(item, VACCINATED));
            rsvp.setCreatedAt(getAttributeValue(item, CREATED_AT));
        }
        return rsvp;
    }

    private static String getAttributeValue(Map<String, AttributeValue> item, String key) {
        if (item.containsKey(key)) {
            return item.get(key).s();
        } else {
            return null;
        }
    }

    public static Map<String, AttributeValue> to(Rsvp rsvp) {
        final Map<String, AttributeValue> rsvpMap = new HashMap<>();
        if (rsvp != null) {
            addNonEmptyValue(rsvpMap, FULL_NAME, rsvp.getFullName());
            addNonEmptyValue(rsvpMap, ATTENDING, rsvp.getAttending());
            addNonEmptyValue(rsvpMap, EMAIL_ADDRESS, rsvp.getEmailAddress());
            addNonEmptyValue(rsvpMap, PHONE_NUMBER, rsvp.getPhoneNumber());
            addNonEmptyValue(rsvpMap, VACCINATED, rsvp.getVaccinated());
            addNonEmptyValue(rsvpMap, CREATED_AT, rsvp.getCreatedAt());
        }
        return rsvpMap;
    }

    private static void addNonEmptyValue(Map<String, AttributeValue> map, String column, String value) {
        if (value != null && !value.isEmpty()) {
            map.put(column, AttributeValue.builder().s(value).build());
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAttending() {
        return attending;
    }

    public void setAttending(String attending) {
        this.attending = attending;
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

    public String getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(String vaccinated) {
        this.vaccinated = vaccinated;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
