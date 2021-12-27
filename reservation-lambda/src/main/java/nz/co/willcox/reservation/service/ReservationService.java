package nz.co.willcox.reservation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.willcox.reservation.model.EventDetails;
import nz.co.willcox.reservation.model.Rsvp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ReservationService extends AbstractService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    DynamoDbClient dynamoDB;

    public ReservationService() {}

    public EventDetails getEvent(String eventId) {
        return EventDetails.from(dynamoDB.getItem(getRequest(eventId)).item());
    }

    public void addPeopleToEvent(
            String eventId,
            List<Rsvp> rsvpInput
    ) {
        final EventDetails eventDetails = EventDetails.from(dynamoDB.getItem(getRequest(eventId)).item());
        final List<Rsvp> rsvps = eventDetails.getRsvps();
        for (Rsvp rsvp : rsvpInput) {
            rsvps.add(rsvp);
        }

        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info("=================== Logging all the data ===================");
            log.info("eventId " + eventId);
            log.info(objectMapper.writeValueAsString(eventDetails));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        final PutItemRequest putItemRequest = putRequest(eventDetails);

        final PutItemResponse putItemResponse = dynamoDB.putItem(putItemRequest);
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
