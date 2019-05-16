package optvm.api;

import optvm.repositories.PoliciesRepository;
import optvm.entities.Policy;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("policies")
public class PoliciesResource {

  @GET
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response getAll() {
    PoliciesRepository repository = PoliciesRepository.getInstance();
    return Response.ok(repository.getAll()).build();
  }

  @GET
  @Path("{id}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response get(@PathParam("id") String id) {
    PoliciesRepository repository = PoliciesRepository.getInstance();
    Policy policy = repository.find(id);

    return policy != null
            ? Response.ok(policy).build()
            : Response.status(404).build();
  }

  @POST
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response insert(Policy policy) {
    PoliciesRepository repository = PoliciesRepository.getInstance();
    policy = repository.insert(policy);

    return Response.ok(policy).status(201).build();
  }

  @PUT
  @Path("{id}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response update(@PathParam("id") String id, Policy policy) {
    PoliciesRepository repository = PoliciesRepository.getInstance();
    policy = repository.update(id, policy);

    return policy != null
            ? Response.ok(policy).build()
            : Response.status(404).build();
  }

  @DELETE
  @Path("{id}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response delete(@PathParam("id") String id) {
    PoliciesRepository repository = PoliciesRepository.getInstance();
    Policy policy = repository.delete(id);

    return policy != null
            ? Response.noContent().build()
            : Response.status(404).build();
  }
}
