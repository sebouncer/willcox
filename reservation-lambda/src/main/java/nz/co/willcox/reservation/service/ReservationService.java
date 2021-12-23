package nz.co.willcox.reservation.service;

import nz.co.willcox.reservation.model.EventDetails;
import nz.co.willcox.reservation.model.Rsvp;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ReservationService extends AbstractService {

    @Inject
    DynamoDbClient dynamoDB;

    public ReservationService() {}

    public EventDetails getEvent(String eventId) {
        return EventDetails.from(dynamoDB.getItem(getRequest(eventId)).item());
    }

    public void addPersonToEvent(
            String eventId,
            Rsvp rsvp
    ) {
        final EventDetails eventDetails = EventDetails.from(dynamoDB.getItem(getRequest(eventId)).item());
        final List<Rsvp> rsvps = eventDetails.getRsvps();
        rsvps.add(rsvp);
        final PutItemResponse putItemResponse = dynamoDB.putItem(putRequest(eventDetails));
    }
}
