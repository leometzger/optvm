package optvm.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import optvm.entities.Policy;
import optvm.entities.Restriction;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class PoliciesRepository {

    private static volatile PoliciesRepository instance;

    private static final String COLECTION_NAME = "policies";
    private final MongoCollection<Document> collection;

    private PoliciesRepository() {
        this.collection = OptvmDatabase
                .getInstance()
                .getCollection(COLECTION_NAME);
    }

    public static PoliciesRepository getInstance() {
        if(instance == null) {
            synchronized (PoliciesRepository.class) {
                if(instance == null) {
                        instance = new PoliciesRepository();
                }
            }
        }
        return instance;
    }

    public List<Policy> getAll() {
        MongoCursor<Document> cursor = this.collection
                .find()
                .iterator();

        List<Policy> policies = new ArrayList();
        try {
            while (cursor.hasNext()) {
                Policy p = this.fromDBToPolicy(cursor.next());
                policies.add(p);
            }
        } finally {
            cursor.close();
        }
        return policies;
    }

    public Policy find(String id) {
        Document document = this.collection
                .find(eq("_id", new ObjectId(id)))
                .first();

        return document == null ? null : this.fromDBToPolicy(document);
    }

    public Policy insert(Policy policy) {
        Document document = this.fromPolicyToDB(policy);
        this.collection.insertOne(document);
        policy.setId(document.getObjectId("_id").toString());
        return policy;
    }

    public Policy delete(String id) {
        Document document = this.collection.findOneAndDelete(eq("_id", new ObjectId(id)));
        return document == null ? null : this.fromDBToPolicy(document);
    }

    public Policy update(String id, Policy policy) {
        Document document = this.collection.findOneAndUpdate(
                eq("_id", new ObjectId(id)),
                new Document("$set", this.fromPolicyToDB(policy))
        );
        policy.setId(document.getObjectId("_id").toString());
        return policy;
    }


    private Policy fromDBToPolicy(Document document) {
        Policy policy = new Policy();

        policy.setId(document.getObjectId("_id").toString());
        policy.setName((String) document.get("name"));
        policy.setStrategy((String) document.get("strategy"));
        policy.setnHostsToOptimize((int) document.get("nHostsToOptimize"));

        List<Document> objectivesDocs = (List<Document>) document.get("objectives");
        List<String> objectives = new ArrayList();
        for(Document doc : objectivesDocs) {
            objectives.add((String) doc.get("name"));
        }

        List<Restriction> restrictions = new ArrayList();
        List<Document> restrictionDocs = (List<Document>) document.get("constraints");
        for(Document doc : restrictionDocs) {
            String name = (String) doc.get("name");
            Map<String, String> params = (Map<String, String>) doc.get("params");
            restrictions.add(new Restriction(name, params));
        }

        policy.setConstraints(restrictions);
        policy.setObjectives(objectives);

        return policy;
    }

    private Document fromPolicyToDB(Policy policy) {
        List<Document> constraints = new ArrayList();
        List<Document> objectives = new ArrayList();

        for(String objective : policy.getObjectives()) {
            objectives.add(new Document().append("name", objective));
        }

        for(Restriction restriction : policy.getConstraints()) {
            Document doc = new Document();
            doc.append("name", restriction.getName());
            doc.append("params", restriction.getParameters());
            constraints.add(doc);
        }

        Document document = new Document();
        document.append("name", policy.getName())
                .append("objectives", objectives)
                .append("constraints", constraints)
                .append("nHostsToOptimize", policy.getnHostsToOptimize())
                .append("strategy", policy.getStrategy());

        return document;
    }
}
