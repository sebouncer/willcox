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
        addNonEmptyValue(item, ID_COL, eventDetails.getId());
        addNonEmptyValue(item, LOCATION_COL, eventDetails.getLocation());
        addNonEmptyValue(item, START_TIME_COL, eventDetails.getStartTime());
        addNonEmptyValue(item, END_TIME_COL, eventDetails.getEndTime());
        addNonEmptyValue(item, DETAILS_COL, eventDetails.getDetails());
        addNonEmptyList(item, RSVPS_COL, eventDetails.getRsvps());

        return PutItemRequest.builder()
                .tableName(getTableName())
                .item(item)
                .build();
    }

    private void addNonEmptyList(
            Map<String, AttributeValue> item,
            String column,
            List<Rsvp> rsvps
    ) {
        item.put(column, AttributeValue.builder().l(createRsvpAttributeList(rsvps)).build());
    }

    private void addNonEmptyValue(Map<String, AttributeValue> item, String column, String value) {
        if (value != null && !value.isEmpty()) {
            item.put(column, AttributeValue.builder().s(value).build());
        }
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
