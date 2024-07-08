package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/api")
public class ReportController {

    @GET
    @Path("/hello")
    public Response hello() {
        String message = "Hello from Report Generate Service!";
        return Response.ok().entity(message).build();
    }
}
