package com.gepardec.so.challenge.backend.service;

import com.gepardec.so.challenge.backend.db.DAOLocal;
import com.gepardec.so.challenge.backend.model.State;

import javax.inject.Inject;

import javax.ws.rs.*;
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

    /**
     *
     * @return valid states for creating a new challenge (active|planned)
     */
    @GET
    @Path("create")
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

    /**
     *
     *
     * @return available states based on param according to stateflow
     */
    @GET
    @Path("{stateId}/available")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableStates(@PathParam("stateId") int stateId) {
        List<State> states = dao.getCreateStates(stateId);
        if(states == null || states.isEmpty()) {
            return notFound();
        } else {
            return Response.ok(new GenericEntity<List<State>>(states) {
            }).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }


}
