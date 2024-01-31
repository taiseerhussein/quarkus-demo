package org.acme.people.service;

import org.acme.people.model.StarWarsPerson;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@RegisterRestClient 
@Path("/api") 
public interface StarWarsService {

    @GET
    @Path("/people/{id}/") 
    @Produces("application/json") 
    @ClientHeaderParam(name="User-Agent", value="QuarkusLab") 
    StarWarsPerson getPerson(@PathParam("id") int id); 
}