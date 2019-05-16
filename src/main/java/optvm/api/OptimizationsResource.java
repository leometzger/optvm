package optvm.api;

import optvm.api.requests.OptimizationRequest;
import optvm.constraints.Constraint;
import optvm.constraints.ConstraintApplier;
import optvm.constraints.ConstraintContext;
import optvm.constraints.ConstraintFactory;
import optvm.entities.*;
import optvm.optimization.OptvmExecutor;
import optvm.optimization.OptvmProblem;
import optvm.repositories.*;
import org.glassfish.jersey.server.Uri;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("optimizations")
public class OptimizationsResource {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAll() {
        OptimizationsRepository repository = OptimizationsRepository.getInstance();

        return Response.ok(repository.getAll()).build();
    }


  @GET
  @Path("{id}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response get(@PathParam("id") String id, @Context UriInfo uriInfo) {
    OptimizationsRepository repository = OptimizationsRepository.getInstance();
    Optimization optimization = repository.find(id);

    Link metricsLink = Link.fromUriBuilder(uriInfo
            .getAbsolutePathBuilder()
            .path("metrics")
    ).rel("metrics").build();
    Map<String, String> links = new HashMap();
    links.put("metrics", metricsLink.getUri().toString());
    optimization.setLinks(links);

    return optimization != null
            ? Response.ok(optimization).build()
            : Response.status(404).build();
  }

  @POST
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response insert(OptimizationRequest request, @Context UriInfo uriInfo) {
    OptimizationsRepository repository = OptimizationsRepository.getInstance();
    PoliciesRepository policiesRepository = PoliciesRepository.getInstance();
    MetricsRepository metricsRepository = MetricsRepository.getInstance();

    OptimizationMetric metric = new OptimizationMetric();
    Policy policy = policiesRepository.find(request.getPolicyId());

    if(policy == null) {
        return Response.status(400).build();
    }

    ConstraintContext context = new ConstraintContext();
    context.setPossibleTargets(request.getClouds());
    context.setFromHost(request.getSourceHost());
    context.setFromCloud(request.getSourceCloud());
    context.setFromDC(request.getSourceDC());
    context.setVm(request.getVm());

    List<Constraint> constraints = new ArrayList();
    for(Restriction restriction : policy.getConstraints()) {
      Constraint constraint = ConstraintFactory
              .makeConstraint(restriction.getName(), restriction.getParameters());
      constraints.add(constraint);
    }

    metric.setInputHostsLenth(context.getPossibleHosts().size());

    long initial = System.currentTimeMillis();
    ConstraintApplier constraintApplier = new ConstraintApplier(context, constraints);
    List<Cloud> clouds = constraintApplier.applyConstraints();
    long end = System.currentTimeMillis();

    List<Host> hosts = new ArrayList();
    for(Cloud cloud : clouds) {
        hosts.addAll(cloud.getHosts());
    }

    metric.setConstraintExecutionTime(end - initial);
    metric.setRemovedByConstraints(metric.getInputHostsLenth() - hosts.size());

    Optimization optimization = new Optimization();
    optimization.setPolicyId(request.getPolicyId());

    initial = System.currentTimeMillis();
    OptvmProblem problem = new OptvmProblem(
        policy.getObjectives(),
        request.getVm(),
        hosts,
        request.getSourceHost(),
        request.getSubsetSize()
    );

    NondominatedPopulation nondominatedPopulation ;
    nondominatedPopulation = new OptvmExecutor(problem).execute();

    HashMap<String, List<HostRef>> result = new HashMap();

    for(int i = 0; i < nondominatedPopulation.size(); ++i) {
      Solution solution = nondominatedPopulation.get(i);
      List<HostRef> hostRefs = new ArrayList();

      for(int j = 0; j < solution.getNumberOfVariables(); ++j) {
        int hostIndex = EncodingUtils.getInt(solution.getVariable(j));
        Host host = hosts.get(hostIndex);

        for(Cloud cloud : clouds) {
          for(Datacenter dc : cloud.getDatacenters()) {
              for(Host dcHost : dc.getHosts()) {
                  if(dcHost.getId().equalsIgnoreCase(host.getId())) {
                      HostRef ref = new HostRef();
                      ref.setCloudId(cloud.getId());
                      ref.setDatacenterId(dc.getId());
                      ref.setHostId(host.getId());
                      hostRefs.add(ref);
                  }
              }
          }
        }
      }
      result.put("option_" + i, hostRefs);
    }

    optimization.setResults(result);
    end = System.currentTimeMillis();

    metric.setOptimizationExecutionTime(end - initial);
    metric.setOutputSolutionsLenth(nondominatedPopulation.size());

    // Salva resultados e metricas
    optimization = repository.insert(optimization);
    metric.setOptimizationId(optimization.getId());
    metricsRepository.insert(optimization.getId(), metric);

    // Adiciona links
    Link metricsLink = Link.fromUriBuilder(uriInfo
            .getAbsolutePathBuilder()
            .path(optimization.getId())
            .path("metrics")
    ).rel("metrics").build();
    Map<String, String> links = new HashMap();
    links.put("metrics", metricsLink.getUri().toString());
    optimization.setLinks(links);

    return Response.ok(optimization)
            .status(201)
            .build();
  }

  @DELETE
  @Path("{id}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response delete(@PathParam("id") String id) {
    OptimizationsRepository repository = OptimizationsRepository.getInstance();
    Optimization optimization = repository.delete(id);

    return optimization != null
            ? Response.noContent().build()
            : Response.status(404).build();
  }


  @GET
  @Path("{id}/details")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response details(@PathParam("id") String id) {
    DetailsRepository repository = DetailsRepository.getInstance();
    OptimizationDetails optimizationDetails = repository.find(id);

     return optimizationDetails != null
            ? Response.ok(optimizationDetails).build()
            : Response.status(404).build();
  }


  @GET
  @Path("{id}/metrics")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response metrics(@PathParam("id") String id) {
    MetricsRepository repository = MetricsRepository.getInstance();
    OptimizationMetric metrics = repository.find(id);

    return metrics != null
        ? Response.ok(metrics).build()
        : Response.status(404).build();
  }
}
