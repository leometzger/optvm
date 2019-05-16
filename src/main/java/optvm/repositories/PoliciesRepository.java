package optvm.repositories;

import optvm.api.PoliciesResource;

import java.util.List;

public class PoliciesRepository {

    private List<PoliciesResource> policies;

    public List<PoliciesResource> getAll() {
        return this.policies;
    }
}
