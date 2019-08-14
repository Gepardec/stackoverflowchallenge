/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gepardec.so.challenge.backend.service;

import com.gepardec.so.challenge.backend.db.DAOLocal;
import com.gepardec.so.challenge.backend.model.Challenge;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.gepardec.so.challenge.backend.utils.EndpointUtils.notAcceptable;
import static com.gepardec.so.challenge.backend.utils.EndpointUtils.notFound;

/**
 * REST Web Service
 *
 * @author praktikant_ankermann
 */
@Path("challenge")
public class ChallengeEndpoint {

    @Context
    private UriInfo context;

    @EJB
    private DAOLocal dao;

    /**
     * Creates a new instance of ChallengeEndpoint
     */
    public ChallengeEndpoint() {
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChallengeById(@PathParam("id") long id) {
        Challenge challenge;
        if ((challenge = dao.findChallenge(id)) == null) {
            return notFound();
        } else {
            return Response.ok(challenge).build();
        }
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllChallenges() {
        List<Challenge> c = dao.readAllChallenges();
        if (c.isEmpty()) {
            return notFound();
        } else {
            return Response.ok(new GenericEntity<List<Challenge>>(c) {
            }).build();
        }
    }

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createChallenge(Challenge c) {
        c.setFromDate(new Date());
        c.setToDate(Date.from(c.getFromDate().toInstant().plus(70, ChronoUnit.DAYS)));

        if (c.getFromDate().after(c.getToDate()) || c.getFromDate().after(new Date())
                || c.getFromDate().equals(c.getToDate())
                || !dao.createChallenge(c)) {
            return notAcceptable();
        } else {
            return Response.status(Response.Status.CREATED).entity(c).build();
        }
    }

    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateChallenge(Challenge challenge) {
        if (challenge.getFromDate().after(challenge.getToDate())
                || challenge.getFromDate().equals(challenge.getToDate())) {
            return notAcceptable();
        }

        challenge = dao.updateChallenge(challenge);
        if (challenge != null) {
            return Response.ok(challenge).build();
        } else {
            return notFound();
        }
    }

    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteChallenge(@PathParam("id") Long id) {
        Challenge challenge = dao.deleteChallenge(id);
        if (challenge != null) {
            return Response.ok(challenge).build();
        } else {
            return notFound();
        }
    }

    @PUT
    @Path("participants/add/{chId}/{profileIds}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addParticipantsToChallenge(@PathParam("chId") Long chId,@PathParam("profileIds") String profileIds) {
        if( profileIds == null || profileIds.isEmpty()) {
            return notAcceptable();
        }
        String[] profileIdsArray = profileIds.split(":");

        List<Long> profileIdsList = new ArrayList<>();
        for (String profileId : profileIdsArray) {
            try {
                profileIdsList.add(Long.parseLong(profileId));
            } catch (NumberFormatException nfe) {
                return notAcceptable();
            }
        }

        for (Long profileId : profileIdsList) {
            System.err.println("profileIds: " + profileId + " |chid:" + chId);
           dao.addParticipantToChallenge(chId, profileId);
        }
        return Response.ok().build();

    }

    @DELETE
    @Path("participants/remove/{chId}")
    @Consumes("text/plain")
    public Response removeParticipantsFromChallenge(@PathParam("chId") Long challengeId, String profileIdsCSVString) {
        String[] profileIdsArray = profileIdsCSVString.split(";");
        if (profileIdsArray.length == 0) {
            return notAcceptable();
        }

        List<Long> profileIdsList = new ArrayList<>();
        for (String profileId : profileIdsArray) {
            try {
                profileIdsList.add(Long.parseLong(profileId));
            } catch (NumberFormatException nfe) {
                return notAcceptable();
            }
        }

        // trying to add all valid integers, if it does not work, all valid one are still added
        for (Long profileId : profileIdsList) {
            dao.removeParticipantFromChallenge(challengeId, profileId);
        }

        return Response.ok().build();
    }
}
