package optvm.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import optvm.entities.HostRef;
import optvm.entities.Optimization;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class OptimizationsRepository {

    private static volatile OptimizationsRepository instance;

    private static final String COLECTION_NAME = "optimizations";
    private final MongoCollection<Document> collection;

    private OptimizationsRepository() {
        this.collection = OptvmDatabase
                .getInstance()
                .getCollection(COLECTION_NAME);
    }

    public static OptimizationsRepository getInstance() {
        if(instance == null) {
            synchronized (MetricsRepository.class) {
                if(instance == null) {
                        instance = new OptimizationsRepository();
                }
            }
        }
        return instance;
    }

    public List<Optimization> getAll() {
        MongoCursor<Document> cursor = this.collection.find().iterator();

        List<Optimization> optmizations = new ArrayList();
        try {
            while(cursor.hasNext()) {
                optmizations.add(this.fromDBToOptimization(cursor.next()));
            }
        } finally {
            cursor.close();
        }
        return optmizations;
    }

    public Optimization find(String id) {
        Document document = this.collection.find(eq("_id", new ObjectId(id))).first();
        return document == null ? null : this.fromDBToOptimization(document);
    }

    public Optimization insert(Optimization optimization) {
        Document document = this.fromOptimizationToDB(optimization);
        this.collection.insertOne(document);
        optimization.setId(document.getObjectId("_id").toString());
        return optimization;
    }

    public Optimization delete(String id) {
        Document document = this.collection.findOneAndDelete(eq("_id", new ObjectId(id)));
        return document == null ? null : this.fromDBToOptimization(document);
    }

    private Optimization fromDBToOptimization(Document document) {
        Optimization optimization = new Optimization();

        optimization.setId(document.getObjectId("_id").toString());
        optimization.setPolicyId((String) document.get("policyId"));

        Map<String, List<Document>> resultDocuments = (Map<String, List<Document>>) document.get("results");
        Map<String, List<HostRef>> results = new HashMap();

        for(String key : resultDocuments.keySet()) {
            List<HostRef> hostRefs = new ArrayList();

            for(Document resultDoc : resultDocuments.get(key)) {
                HostRef result = new HostRef();
                result.setCloudId((String) resultDoc.get("cloudId"));
                result.setDatacenterId((String) resultDoc.get("datacenterId"));
                result.setHostId((String) resultDoc.get("hostId"));
                hostRefs.add(result);
            }
            results.put(key, hostRefs);
        }
        optimization.setResults(results);

        return optimization;
    }

    private Document fromOptimizationToDB(Optimization optimization) {
        Document document = new Document().append("policyId", optimization.getPolicyId());

        Map<String, List<Document>> results = new HashMap();
        for(String key : optimization.getResults().keySet()) {
            List<Document>  hostResults = new ArrayList();
            List<HostRef> hosts = optimization.getResults().get(key);

            for(HostRef result : hosts) {
                Document doc = new Document();
                doc.append("cloudId", result.getCloudId())
                    .append("datacenterId", result.getDatacenterId())
                    .append("hostId", result.getHostId());
                hostResults.add(doc);
            }
            results.put(key, hostResults);
        }

        document.append("results", results);
        return document;
    }
}
