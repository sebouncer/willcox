package nz.co.willcox.reservation.controller;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.willcox.reservation.model.EventDetails;
import nz.co.willcox.reservation.model.Rsvp;
import nz.co.willcox.reservation.model.RsvpList;
import nz.co.willcox.reservation.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;

@ApplicationScoped
public class ReservationController {

    private static final String ID = "id";
    private static final String API_KEY_VALUE = "Engagement";
    private static final String S_PASSWORD_VALUE = "Spedding";
    private static final String W_PASSWORD_VALUE = "Willcox";
    private static final String APIKEY_HEADER_KEY = "apikey"; // Headers must be lowercase
    private static final String PASSWORD_HEADER_KEY = "password";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ReservationService reservationService;
    private final ObjectMapper objectMapper;

    public ReservationController(
            ReservationService reservationService,
            ObjectMapper objectMapper
    ) {
        this.reservationService = reservationService;
        this.objectMapper = objectMapper;
    }

    public APIGatewayV2HTTPResponse post(APIGatewayV2HTTPEvent input) throws JsonProcessingException {
        final APIGatewayV2HTTPResponse securityCheckResponse = securityCheck(input);
        if (securityCheckResponse != null) {
            return securityCheckResponse;
        }

        final RsvpList rsvpList = objectMapper.readValue(input.getBody(), RsvpList.class);
        final String eventId = input.getPathParameters().get(ID);

        if (rsvpList.size() == 0) {
            final APIGatewayV2HTTPResponse apiGatewayV2HTTPResponse = new APIGatewayV2HTTPResponse();
            apiGatewayV2HTTPResponse.setStatusCode(500);
            apiGatewayV2HTTPResponse.setBody("No body that can be parsed");
            return apiGatewayV2HTTPResponse;
        }

        if (eventId == null || eventId.isEmpty()) {
            final APIGatewayV2HTTPResponse apiGatewayV2HTTPResponse = new APIGatewayV2HTTPResponse();
            apiGatewayV2HTTPResponse.setStatusCode(500);
            apiGatewayV2HTTPResponse.setBody("No event");
            return apiGatewayV2HTTPResponse;
        }

        reservationService.addPeopleToEvent(eventId, rsvpList);
        return new APIGatewayV2HTTPResponse();
    }

    public APIGatewayV2HTTPResponse get(APIGatewayV2HTTPEvent input) throws JsonProcessingException {
        final APIGatewayV2HTTPResponse securityCheckResponse = securityCheck(input);
        if (securityCheckResponse != null) {
            return securityCheckResponse;
        }

        final String eventId = input.getPathParameters().get(ID);
        if (eventId == null) {
            throw new RuntimeException("Event id of null does not exists");
        }
        final EventDetails event = reservationService.getEvent(eventId);

        final APIGatewayV2HTTPResponse apiGatewayV2HTTPResponse = new APIGatewayV2HTTPResponse();
        apiGatewayV2HTTPResponse.setBody(objectMapper.writeValueAsString(event));
        return apiGatewayV2HTTPResponse;
    }

    private APIGatewayV2HTTPResponse securityCheck(APIGatewayV2HTTPEvent input) {
        final Map<String, String> headers = input.getHeaders();
        final String apiKey = headers.get(APIKEY_HEADER_KEY);
        final String password = headers.get(PASSWORD_HEADER_KEY);
        if (!API_KEY_VALUE.equals(apiKey) || !(S_PASSWORD_VALUE.equals(password) || W_PASSWORD_VALUE.equals(password))) {
            logHeaders(headers);
            final APIGatewayV2HTTPResponse apiGatewayV2HTTPResponse = new APIGatewayV2HTTPResponse();
            apiGatewayV2HTTPResponse.setStatusCode(401);
            return apiGatewayV2HTTPResponse;
        }
        return null;
    }

    private void logHeaders(Map<String, String> headers) {
        for (Map.Entry entry : headers.entrySet()) {
            log.info(entry.getKey() + " = :" + entry.getValue() + ":");
        }
    }
}
