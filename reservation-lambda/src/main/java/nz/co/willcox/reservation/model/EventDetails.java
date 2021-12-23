package nz.co.willcox.reservation.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import nz.co.willcox.reservation.service.AbstractService;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;

@RegisterForReflection
public class EventDetails {

    private String id;
    private String location;
    private String startTime;
    private String endTime;
    private String details;
    private List<Rsvp> rsvps;

    public static EventDetails from(Map<String, AttributeValue> item) {
        EventDetails eventDetails = new EventDetails();
        if (item != null && !item.isEmpty()) {
            eventDetails.setLocation(item.get(AbstractService.LOCATION_COL).s());
            eventDetails.setStartTime(item.get(AbstractService.START_TIME_COL).s());
        }
        return eventDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<Rsvp> getRsvps() {
        return rsvps;
    }

    public void setRsvps(List<Rsvp> rsvps) {
        this.rsvps = rsvps;
    }

    @Override
    public String toString() {
        return "EventDetails{" +
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", details='" + details + '\'' +
                ", rsvps=" + rsvps +
                '}';
    }
}
