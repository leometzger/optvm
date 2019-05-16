package optvm.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("policies")
public class PoliciesResource {

  @GET
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response getAll() {
    return Response.ok().build();
  }

  @GET
  @Path("{id}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response get(@PathParam("id") int id) {
    return Response.ok().build();
  }

  @POST
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response insert(@PathParam("id") int id) {
    return Response.ok().build();
  }

  @DELETE
  @Path("{id}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response delete(@PathParam("id") int id) {
    return Response.ok().build();
  }

  /*@PUT
  @Path("{id}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response update(@PathParam("id") int id) {
    return Response.ok().build();
  }*/
}
