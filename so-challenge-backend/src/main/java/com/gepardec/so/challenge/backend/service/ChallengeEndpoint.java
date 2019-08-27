/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gepardec.so.challenge.backend.service;

import com.gepardec.so.challenge.backend.db.DAOLocal;
import com.gepardec.so.challenge.backend.model.Challenge;
import com.gepardec.so.challenge.backend.model.Participant;
import com.gepardec.so.challenge.backend.model.Tag;

import static com.gepardec.so.challenge.backend.utils.EndpointUtils.notAcceptable;
import static com.gepardec.so.challenge.backend.utils.EndpointUtils.notFound;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.core.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author praktikant_ankermann
 */
@Path("challenge")
public class ChallengeEndpoint {

    @Context
    private UriInfo context;

    @Inject
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
        if ((challenge = dao.getChallengeById(id)) == null) {
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

    @GET
    @Path("{chId}/participants/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getParticipantsOfChallenge(@PathParam("chId") Long chId) {
        return Response.ok(
                new GenericEntity<List<Participant>>(
                        dao.getParticipantsOfChallenge(chId)){})
                .build();
    }

//    @PUT
//    @Path("participants/add/{chId}/{profileIds}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response addParticipantsToChallenge(@PathParam("chId") Long chId,@PathParam("profileIds") String profileIds) {
//        if( profileIds == null || profileIds.isEmpty()) {
//            return notAcceptable();
//        }
//        String[] profileIdsArray = profileIds.split(":");
//
//        List<Long> profileIdsList = new ArrayList<>();
//        for (String profileId : profileIdsArray) {
//            try {
//                profileIdsList.add(Long.parseLong(profileId));
//            } catch (NumberFormatException nfe) {
//                return notAcceptable();
//            }
//        }
//
//        for (Long profileId : profileIdsList) {
//            System.err.println("profileIds: " + profileId + " |chid:" + chId);
//            dao.addParticipantToChallenge(chId, profileId);
//        }
//        return Response.ok().build();
//    }

    @PUT
    @Path("participants/add/{name}/{profileIds}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addParticipantsToChallenge(@PathParam("name") String name,@PathParam("profileIds") String profileIds) {
        if (profileIds == null || profileIds.isEmpty()) {
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
            dao.addParticipantToChallenge(name, profileId);
        }
        return Response.ok().build();
    }

    @PUT
    @Path("{chId}/tags/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTagsToChallenge(@PathParam("chId") Long chId,List<Tag> tags) {
        if (tags == null || tags.isEmpty()) {
            return notAcceptable();
        }
        Challenge c = dao.getChallengeById(chId);
        for(Tag tag:tags) {
            dao.addTagsToChallenge(c.getId(), tag.getId());
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("participants/remove/{chId}")
    @Consumes({MediaType.TEXT_PLAIN})
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
