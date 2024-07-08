package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/api")
public class PaymentController {

    @GET
    @Path("/hello")
    public Response hello() {
        String message = "Hello from Payment Processing Service!";
        return Response.ok().entity(message).build();
    }
}
