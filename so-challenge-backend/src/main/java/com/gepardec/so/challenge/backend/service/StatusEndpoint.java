package com.gepardec.so.challenge.backend.service;

import com.gepardec.so.challenge.backend.db.DAOLocal;
import com.gepardec.so.challenge.backend.model.Status;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.List;

import static com.gepardec.so.challenge.backend.utils.EndpointUtils.notFound;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/status")
public class StatusEndpoint {

    @Context
    private UriInfo context;

    @EJB
    private DAOLocal dao;
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getState() {
        List<Status> states = dao.getAllStates();
        if (states.isEmpty()) {
            return notFound();
        } else {
            return Response.ok(new GenericEntity<List<Status>>(states) {
            }).build();
        }
    }

    @GET
    @Path("startStates")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatesForAddChallenge() {
        List<Status> states = dao.getCreateStates();
        if(states.isEmpty()) {
            return notFound();
        } else {
            return Response.ok(new GenericEntity<List<Status>>(states) {
            }).build();
        }
    }


}
