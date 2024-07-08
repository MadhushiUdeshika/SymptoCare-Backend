package com.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/api")
public class UserController {

    @GET
    @Path("/hello")
    public Response hello() {
        String message = "Hello from User Service!";
        return Response.ok().entity(message).build();
    }
}
