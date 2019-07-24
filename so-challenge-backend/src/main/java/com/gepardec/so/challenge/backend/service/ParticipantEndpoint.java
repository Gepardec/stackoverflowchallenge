/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gepardec.so.challenge.backend.service;

import com.gepardec.so.challenge.backend.db.DAOLocal;
import com.gepardec.so.challenge.backend.model.Participant;
import com.gepardec.so.challenge.backend.utils.EndpointUtils;
import java.util.List;
import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author praktikant_ankermann
 */
@Path("participant")
public class ParticipantEndpoint {

    @Context
    private UriInfo context;

    @EJB
    private DAOLocal dao;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getParticipantById(@PathParam("id") Long id) {
        Participant p = dao.findParticipant(id);

        if (p == null) {
            JsonObject o = EndpointUtils.sendRequestAndGetJson("users/" + id, "GET");
            if (o != null) {
                return Response.ok(o).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } else {
            return Response.ok(p).build();
        }
    }

    @POST
    @Path("add")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createParticipant(Long profileId) {
        JsonObject o = EndpointUtils.sendRequestAndGetJson("users/" + profileId, "GET");

        if (o == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Participant p = new Participant(profileId);
        p.setUsername(o.getJsonArray("items").getJsonObject(0).getString("display_name"));
        p.setLink(o.getJsonArray("items").getJsonObject(0).getString("link"));
        p.setImageURL(o.getJsonArray("items").getJsonObject(0).getString("profile_image"));

        boolean success = dao.createParticipant(p);
        if (success) {
            return Response.status(Response.Status.CREATED).entity(p).build();
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllParticipants() {
        List<Participant> p = dao.readAllParticipants();

        if (p.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.ok(p).build();
        }
    }

    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteParticipant(@PathParam("id") Long id) {
        Participant p = dao.deleteParticipant(id);
        if (p != null) {
            return Response.ok(p).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
