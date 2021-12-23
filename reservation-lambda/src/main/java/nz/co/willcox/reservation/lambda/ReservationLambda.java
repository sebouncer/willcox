package nz.co.willcox.reservation.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import nz.co.willcox.reservation.controller.ReservationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named("reservation")
public class ReservationLambda implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    private static final String POST = "POST";
    private static final String GET = "GET";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    private ReservationController reservationController;

    @Override
    public APIGatewayV2HTTPResponse handleRequest(
            APIGatewayV2HTTPEvent input,
            Context context
    ) {
        log.info("Starting logger");
        final String method = input.getRequestContext().getHttp().getMethod();
        APIGatewayV2HTTPResponse apiGatewayV2HTTPResponse = new APIGatewayV2HTTPResponse();
        try {
            log.info(">>>>>>>> method = :" + method + ":");
            if (POST.equals(method)) {
                apiGatewayV2HTTPResponse = reservationController.post(input);
            } else if (GET.equals(method)) {
                apiGatewayV2HTTPResponse = reservationController.get(input);
            }
            if (apiGatewayV2HTTPResponse.getStatusCode() == 0) {
                apiGatewayV2HTTPResponse.setStatusCode(200);
            }
        } catch (Exception e) {
            log.info(">>>>>>>> Exception = :" + e.getMessage() + ":");
            e.printStackTrace();
            apiGatewayV2HTTPResponse.setStatusCode(500);
        }
        return apiGatewayV2HTTPResponse;
    }
}

/*

{
 "id": "1",
 "startTime": "4:00pm",
 "location": "Our House",
 "endTime": "4:00pm",
 "rsvps": [
  {
   "firstName": "Andrew",
   "lastName": "LastName",
   "emailAddress": "someEmailAddress@gmail.com",
   "phoneNumber": "021 123 456",
   "numberOfRsvpPeople": "2",
   "isVaxed": "true"
  }
 ],
 "details": "Come and join us"
}


 */