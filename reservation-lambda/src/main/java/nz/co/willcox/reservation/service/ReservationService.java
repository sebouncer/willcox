package nz.co.willcox.reservation.service;

import nz.co.willcox.reservation.model.EventDetails;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ReservationService extends AbstractService {

    @Inject
    DynamoDbClient dynamoDB;

    public ReservationService() {}

    public EventDetails getEvent(String eventId) {
        return EventDetails.from(dynamoDB.getItem(getRequest(eventId)).item());
    }

    public void addPersonToEvent() {
    }
}
