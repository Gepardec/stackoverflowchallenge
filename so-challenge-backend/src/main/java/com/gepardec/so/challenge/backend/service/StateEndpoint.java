package com.gepardec.so.challenge.backend.service;

import com.gepardec.so.challenge.backend.db.DAOLocal;
import com.gepardec.so.challenge.backend.model.State;

import javax.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.List;

import static com.gepardec.so.challenge.backend.utils.EndpointUtils.notFound;

@Path("/state")
public class StateEndpoint {

    @Context
    private UriInfo context;

    @Inject
    private DAOLocal dao;

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getState() {
        List<State> states = dao.getAllStates();
        if (states.isEmpty()) {
            return notFound();
        } else {
            return Response.ok(new GenericEntity<List<State>>(states) {
            }).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }

    @GET
    @Path("startStates")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCreateStates() {
        List<State> states = dao.getCreateStates();
        if(states.isEmpty()) {
            return notFound();
        } else {
            return Response.ok(new GenericEntity<List<State>>(states) {
            }).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }

    @GET
    @Path("available")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableStates(State s) {
        List<State> states = dao.getCreateStates();
        if(states == null || states.isEmpty()) {
            return notFound();
        } else {
            return Response.ok(new GenericEntity<List<State>>(states) {
            }).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }


}
