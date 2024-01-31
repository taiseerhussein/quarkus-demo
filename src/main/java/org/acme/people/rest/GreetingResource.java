package org.acme.people.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.common.annotation.NonBlocking;
import jakarta.inject.Inject;
import org.acme.people.service.GreetingService;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.util.Optional;
import io.micrometer.core.instrument.MeterRegistry;

@Path("/hello")
public class GreetingResource {

    public static final Logger log = LoggerFactory.getLogger(GreetingResource.class);

   
    @ConfigProperty(name = "greeting.message")
    String message;

    @ConfigProperty(name = "greeting.suffix", defaultValue="!")
    String suffix;

    @ConfigProperty(name = "greeting.name")
    Optional<String> name;

    @Inject
    GreetingService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/greeting/{name}")
    @NonBlocking
    public String greeting(@PathParam("name") String name) {
        return service.greeting(name);
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @NonBlocking
    public String hello() {
        registry.counter("greeting.hello.counter").increment();
        return "hello";
    }

        @GET
    @Path("/lastletter/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String lastLetter(@PathParam("name") String name) {
        int len = name.length() - 1;
        String lastLetter = name.substring(len);
        log.info("Got last letter: " + lastLetter);
        return lastLetter;
    }

    private final MeterRegistry registry;

    GreetingResource(MeterRegistry registry) {
        this.registry = registry;
    }
}