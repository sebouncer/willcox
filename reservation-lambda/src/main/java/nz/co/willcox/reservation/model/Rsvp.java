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

    private String fullName;
    private String attending;
    private String emailAddress;
    private String phoneNumber;
    private String vaccinated;

    public static Rsvp from(Map<String, AttributeValue> item) {
        final Rsvp rsvp = new Rsvp();
        if (item != null && !item.isEmpty()) {
            rsvp.setFullName(item.get(FULL_NAME).s());
            rsvp.setAttending(item.get(ATTENDING).s());
            rsvp.setEmailAddress(item.get(EMAIL_ADDRESS).s());
            rsvp.setPhoneNumber(item.get(PHONE_NUMBER).s());
            rsvp.setVaccinated(item.get(VACCINATED).s());
        }
        return rsvp;
    }

    public static Map<String, AttributeValue> to(Rsvp rsvp) {
        final Map<String, AttributeValue> rsvpMap = new HashMap<>();
        if (rsvp != null) {
            rsvpMap.put(FULL_NAME, AttributeValue.builder().s(rsvp.getFullName()).build());
            rsvpMap.put(ATTENDING, AttributeValue.builder().s(rsvp.getAttending()).build());
            rsvpMap.put(EMAIL_ADDRESS, AttributeValue.builder().s(rsvp.getEmailAddress()).build());
            rsvpMap.put(PHONE_NUMBER, AttributeValue.builder().s(rsvp.getPhoneNumber()).build());
            rsvpMap.put(VACCINATED, AttributeValue.builder().s(rsvp.getVaccinated()).build());
        }
        return rsvpMap;
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

    public void setPhoneNumber(String contactPhoneNumber) {
        this.phoneNumber = contactPhoneNumber;
    }

    public String getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(String vaccinated) {
        this.vaccinated = vaccinated;
    }
}
