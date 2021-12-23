package nz.co.willcox.reservation.controller;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.willcox.reservation.model.EventDetails;
import nz.co.willcox.reservation.service.ReservationService;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReservationController {

    private static final String ID = "id";

    private final ReservationService reservationService;
    private final ObjectMapper objectMapper;

    public ReservationController(
            ReservationService reservationService,
            ObjectMapper objectMapper
    ) {
        this.reservationService = reservationService;
        this.objectMapper = objectMapper;
    }

    public APIGatewayV2HTTPResponse post(APIGatewayV2HTTPEvent input) {
        reservationService.addPersonToEvent();
        return new APIGatewayV2HTTPResponse();
    }

    public APIGatewayV2HTTPResponse get(APIGatewayV2HTTPEvent input) throws JsonProcessingException {
        final String eventId = input.getPathParameters().get(ID);
        if (eventId == null) {
            throw new RuntimeException("Event id of null does not exists");
        }
        final EventDetails event = reservationService.getEvent(eventId);

        final APIGatewayV2HTTPResponse apiGatewayV2HTTPResponse = new APIGatewayV2HTTPResponse();
        apiGatewayV2HTTPResponse.setBody(objectMapper.writeValueAsString(event));
        return apiGatewayV2HTTPResponse;
    }
}
