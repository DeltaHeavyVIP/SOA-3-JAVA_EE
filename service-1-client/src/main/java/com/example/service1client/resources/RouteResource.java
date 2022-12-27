package com.example.service1client.resources;

import com.example.objects.basic.request.ProductRequestDto;
import com.example.objects.common.ExceptionDto;
import com.example.objects.common.FilterDto;
import com.example.service1server.service.BasicOperationService;
import org.wildfly.naming.client.WildFlyInitialContextFactory;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Properties;

@Path("/api/v1")
public class RouteResource {

    @EJB
    BasicOperationService basicOperationService = getFromEJBPool("ejb:/service-1-server-1.0-SNAPSHOT-jar-with-dependencies/BasicOperationServiceImpl!com.example.service1server.service.BasicOperationService");

    public RouteResource() throws NamingException {
    }

    private BasicOperationService getFromEJBPool(String name) throws NamingException {
        return (BasicOperationService) new InitialContext().lookup(name);
    }

    @GET
    @Path("/ping")
    @Consumes("application/json")
    @Produces("application/json")
    public Response isAlive() {
        return Response.ok("pong").build();
    }

    @GET
    @Path("/products/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getProductById(@PathParam("id") Integer id) {
        try {
            return Response.ok().entity(basicOperationService.getProductById(id)).build();
        } catch (EJBException ex) {
            return Response.status(Response.Status.BAD_REQUEST).header("Content-Type", MediaType.APPLICATION_JSON).entity(new ExceptionDto(ex.getCause().getMessage(), LocalDateTime.now())).build();
        }
    }

    @POST
    @Path("/products/filter")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getProductsByFilter(FilterDto filter) {
        return Response.ok().entity(basicOperationService.getProductsByFilter(filter)).build();
    }

    @POST
    @Path("/products")
    @Consumes("application/json")
    @Produces("application/json")
    public Response createProduct(ProductRequestDto product) {
        return Response.ok().entity(basicOperationService.createProduct(product)).build();
    }

    @PUT
    @Path("/products/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateProductById(@PathParam("id") Integer id, ProductRequestDto product) {
        try {
            return Response.ok().entity(basicOperationService.updateProductById(id, product)).build();
        } catch (EJBException ex) {
            return Response.status(Response.Status.BAD_REQUEST).header("Content-Type", MediaType.APPLICATION_JSON).entity(new ExceptionDto(ex.getCause().getMessage(), LocalDateTime.now())).build();
        }
    }

    @DELETE
    @Path("/products/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deleteProductById(@PathParam("id") Integer id) {
        try {
            basicOperationService.deleteProductById(id);
            return Response.ok().build();
        } catch (EJBException ex) {
            return Response.status(Response.Status.BAD_REQUEST).header("Content-Type", MediaType.APPLICATION_JSON).entity(new ExceptionDto(ex.getCause().getMessage(), LocalDateTime.now())).build();
        }
    }

    @GET
    @Path("/count/products/price_high_parameter")
    @Consumes("application/json")
    @Produces("application/json")
    public Response countProductsWherePriceHigher(@QueryParam("price") Long price) {
        try {
            return Response.ok().entity(basicOperationService.countProductsWherePriceHigher(price)).build();
        } catch (EJBException ex) {
            return Response.status(Response.Status.BAD_REQUEST).header("Content-Type", MediaType.APPLICATION_JSON).entity(new ExceptionDto(ex.getCause().getMessage(), LocalDateTime.now())).build();
        }
    }

    @GET
    @Path("/search/products/name/include/substring")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getArrayProductsWhereNameIncludeSubstring(@QueryParam("subString") String subString) {
        try {
            return Response.ok().entity(basicOperationService.getArrayProductsWhereNameIncludeSubstring(subString)).build();
        } catch (EJBException ex) {
            return Response.status(Response.Status.BAD_REQUEST).header("Content-Type", MediaType.APPLICATION_JSON).entity(new ExceptionDto(ex.getCause().getMessage(), LocalDateTime.now())).build();
        }
    }

    @GET
    @Path("/search/products/name/unique")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getArrayProductsWhereNameUnique() {
        return Response.ok().entity(basicOperationService.getArrayProductsWhereNameUnique()).build();
    }
}
