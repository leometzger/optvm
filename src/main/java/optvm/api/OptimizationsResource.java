package optvm.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("optimizations")
public class OptimizationsResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        List<Integer> resp = new ArrayList();
        resp.add(1);
        resp.add(2);
        resp.add(3);

        return Response.ok(resp).build();
    }
}
