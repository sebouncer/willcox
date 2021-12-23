package nz.co.willcox.reservation.service;

import nz.co.willcox.reservation.model.EventDetails;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import java.util.HashMap;
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
        item.put(LOCATION_COL, AttributeValue.builder().s(eventDetails.getLocation()).build());
        item.put(START_TIME_COL, AttributeValue.builder().s(eventDetails.getStartTime()).build());

        return PutItemRequest.builder()
                .tableName(getTableName())
                .item(item)
                .build();
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
