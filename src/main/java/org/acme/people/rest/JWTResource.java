package org.acme.people.rest;

import java.security.Principal;
import java.util.Optional;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonString;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/secured")
@RequestScoped 
public class JWTResource {

    @Inject
    JsonWebToken jwt;  

    @Inject
    @Claim(standard = Claims.iss)
    Optional<JsonString> issuer; 

    @GET
    @Path("/me")
    @RolesAllowed("user")
    @Produces(MediaType.TEXT_PLAIN)
    public String me(@Context SecurityContext ctx) {  
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();
        boolean hasJWT = jwt != null;
        return String.format("hello %s, isSecure: %s, authScheme: %s, hasJWT: %s\n", name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJWT);
    }

    @GET
    @Path("/me/admin")
    @RolesAllowed("admin")
    @Produces(MediaType.TEXT_PLAIN)
    public String meJwt(@Context SecurityContext ctx) {   
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();
        boolean hasJWT = jwt != null;

        final StringBuilder helloReply = new StringBuilder(String.format("hello %s, isSecure: %s, authScheme: %s, hasJWT: %s\n", name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJWT));
        if (hasJWT && (jwt.getClaimNames() != null)) {
            helloReply.append("Injected issuer: [" + issuer.get() + "]\n"); 
            jwt.getClaimNames().forEach(n -> {
                helloReply.append("\nClaim Name: [" + n + "] Claim Value: [" + jwt.getClaim(n) + "]");
            });
        }
        return helloReply.toString();
    }
}