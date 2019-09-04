package com.gepardec.so.challenge.backend.service;

import com.gepardec.so.challenge.backend.db.DAOLocal;
import com.gepardec.so.challenge.backend.model.Tag;
import com.gepardec.so.challenge.backend.utils.EndpointUtils;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

import static com.gepardec.so.challenge.backend.utils.EndpointUtils.notAcceptable;
import static com.gepardec.so.challenge.backend.utils.EndpointUtils.notFound;

/**
 * REST Web Service
 *
 * @author praktikant_ankermann
 */
@Path("tag")
public class TagEndpoint {


    @Context
    private UriInfo context;

    @Inject
    private DAOLocal dao;

    /**
     * Creates a new instance of ChallengeEndpoint
     */
    public TagEndpoint() {
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTags() {
        List<Tag> t = dao.getAllTags();
        if (t.isEmpty()) {
            return notFound();
        } else {
            return Response.ok(new GenericEntity<List<Tag>>(t) {
            }).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }

    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteTag(@PathParam("id") Long id) {
        Tag t = dao.deleteTag(id);
        if (t != null) {
            return Response.ok(t).type(MediaType.TEXT_PLAIN_TYPE).build();
        } else {
            return notFound();
        }
    }

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTag(String name) {
        if (dao.isTagNameAlreadyPresent(name.toLowerCase())) {
            return notAcceptable();
        }
        JsonObject o = EndpointUtils.sendRequestAndGetJson("tags/" + name + "/info", "?site=stackoverflow", "GET");
        if (o == null) {
            return notFound();
        }

        Tag t = new Tag();
        t.setName(o.getJsonArray("items").getJsonObject(0).getString("name"));

        boolean success = dao.createTag(t);
        if (success) {
            return Response.status(Response.Status.CREATED).entity(t).type(MediaType.APPLICATION_JSON_TYPE).build();
        } else {
            return notAcceptable();
        }

    }
}
