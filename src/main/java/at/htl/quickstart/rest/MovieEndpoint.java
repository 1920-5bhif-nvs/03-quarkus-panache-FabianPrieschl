package at.htl.quickstart.rest;


import at.htl.quickstart.business.MovieRepository;
import at.htl.quickstart.model.CrewMember;
import at.htl.quickstart.model.Movie;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

@Path("movie")
public class MovieEndpoint {

    @Inject
    MovieRepository movieRepository;

    @Path("findAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Movie> list = movieRepository.getAll();
        GenericEntity entity = new GenericEntity<List<Movie>>(list){};
        if(!list.isEmpty())
            return Response.ok(entity).build();
        else
            return Response.noContent().build();
    }

    @Path("find/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovie(@PathParam("id") long id) {
        Movie m = movieRepository.findById(id);
        if(m != null){
            return Response.ok().entity(m).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMovie(@PathParam("id") long id){
        Movie m = movieRepository.findById(id);
        try {
            if(m != null){
                movieRepository.delete(m);
            }
        }
        catch (Exception e){
            return Response.status(404).build();
        }
        return Response.noContent().entity(m).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postMovie(Movie m){
        try {
            movieRepository.save(m);
        }catch(PersistenceException e){
            return Response.status(400).build();
        }
        return Response.ok().entity(m).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putMovie(Movie m){
        movieRepository.save(m);
        return Response.ok().entity(m).build();
    }
}
