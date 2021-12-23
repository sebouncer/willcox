package nz.co.willcox.reservation.service;

import nz.co.willcox.reservation.model.EventDetails;
import nz.co.willcox.reservation.model.Rsvp;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractService {

    public final static String ID_COL = "id";
    public final static String LOCATION_COL = "location";
    public final static String START_TIME_COL = "startTime";
    public final static String END_TIME_COL = "endTime";
    public final static String RSVPS_COL = "rsvps";
    public static final String DETAILS_COL = "details";

    public String getTableName() {
        return "reservation";
    }

    protected ScanRequest scanRequest() {
        return ScanRequest.builder().tableName(getTableName())
                .attributesToGet(LOCATION_COL, START_TIME_COL).build();
    }

    protected PutItemRequest putRequest(EventDetails eventDetails) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put(ID_COL, AttributeValue.builder().s(eventDetails.getId()).build());
        item.put(LOCATION_COL, AttributeValue.builder().s(eventDetails.getLocation()).build());
        item.put(START_TIME_COL, AttributeValue.builder().s(eventDetails.getStartTime()).build());
        item.put(END_TIME_COL, AttributeValue.builder().s(eventDetails.getEndTime()).build());
        item.put(DETAILS_COL, AttributeValue.builder().s(eventDetails.getDetails()).build());
        item.put(RSVPS_COL, AttributeValue.builder().l(createRsvpAttributeList(eventDetails.getRsvps())).build());

        return PutItemRequest.builder()
                .tableName(getTableName())
                .item(item)
                .build();
    }

    private List<AttributeValue> createRsvpAttributeList(List<Rsvp> rsvps) {
        final List<AttributeValue> attributeValues = new ArrayList<>();
        for (Rsvp rsvp : rsvps) {
            attributeValues.add(AttributeValue.builder().m(Rsvp.to(rsvp)).build());
        }
        return attributeValues;
    }

    protected GetItemRequest getRequest(String eventId) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put(ID_COL, AttributeValue.builder().s(eventId).build());

        return GetItemRequest.builder()
                .tableName(getTableName())
                .key(key)
                .attributesToGet(
                        ID_COL,
                        LOCATION_COL,
                        START_TIME_COL,
                        END_TIME_COL,
                        RSVPS_COL,
                        DETAILS_COL
                )
                .build();
    }
}
