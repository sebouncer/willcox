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
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@ApplicationScoped
public class ReservationService extends AbstractService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Inject
    DynamoDbClient dynamoDB;

    public ReservationService() {}

    public EventDetails getEvent(String eventId) {
        final EventDetails eventDetails = EventDetails.from(dynamoDB.getItem(getRequest(eventId)).item());
        eventDetails.setRsvps(null);
        return eventDetails;
    }

    private String getCurrentNZDateTime() {
        final OffsetDateTime now = OffsetDateTime.now(ZoneId.of("Pacific/Auckland"));
        return fmt.format(now);
    }

    public void addPeopleToEvent(
            String eventId,
            List<Rsvp> rsvpInput
    ) {
        final EventDetails eventDetails = EventDetails.from(dynamoDB.getItem(getRequest(eventId)).item());
        final List<Rsvp> rsvps = eventDetails.getRsvps();
        final String currentNZDateTime = getCurrentNZDateTime();

        for (Rsvp rsvp : rsvpInput) {
            rsvp.setCreatedAt(currentNZDateTime);
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
