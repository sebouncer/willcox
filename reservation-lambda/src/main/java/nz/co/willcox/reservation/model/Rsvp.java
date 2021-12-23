package nz.co.willcox.reservation.model;

public class Rsvp {

    private int numberOfRsvpPeople;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private int isVaxed;

    public int getNumberOfRsvpPeople() {
        return numberOfRsvpPeople;
    }

    public void setNumberOfRsvpPeople(int numberOfRsvpPeople) {
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

    public int getIsVaxed() {
        return isVaxed;
    }

    public void setIsVaxed(int isVaxed) {
        this.isVaxed = isVaxed;
    }
}
