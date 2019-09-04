package com.gepardec.so.challenge.backend.service;

import com.gepardec.so.challenge.backend.db.DAOLocal;
import com.gepardec.so.challenge.backend.model.State;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.List;

import static com.gepardec.so.challenge.backend.utils.EndpointUtils.notFound;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/state")
public class StateEndpoint {

    @Context
    private UriInfo context;

    @Inject
    private DAOLocal dao;

    @GET
    @Path("all")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getState() {
        List<State> states = dao.getAllStates();
        if (states.isEmpty()) {
            return notFound();
        } else {
            return Response.ok(new GenericEntity<List<State>>(states) {
            }).type(MediaType.TEXT_PLAIN_TYPE).build();
        }
    }

    @GET
    @Path("startStates")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCreateStates() {
        List<State> states = dao.getAvailableStates();
        if(states.isEmpty()) {
            return notFound();
        } else {
            return Response.ok(new GenericEntity<List<State>>(states) {
            }).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }

    @GET
    @Path("available")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAvailableStates(State s) {
        List<State> states = dao.getAvailableStates();
        if(states == null || states.isEmpty()) {
            return notFound();
        } else {
            return Response.ok(new GenericEntity<List<State>>(states) {
            }).type(MediaType.TEXT_PLAIN_TYPE).build();
        }
    }


}
