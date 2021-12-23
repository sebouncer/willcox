package nz.co.willcox.reservation.lambda;

import javax.inject.Inject;
import javax.inject.Named;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import nz.co.willcox.reservation.model.InputObject;
import nz.co.willcox.reservation.model.OutputObject;
import nz.co.willcox.reservation.service.GreetService;

@Named("unused")
public class UnusedLambda implements RequestHandler<InputObject, OutputObject> {

    @Inject
    GreetService service;

    @Override
    public OutputObject handleRequest(InputObject input, Context context) {
        throw new RuntimeException("Should be unused");
    }
}
