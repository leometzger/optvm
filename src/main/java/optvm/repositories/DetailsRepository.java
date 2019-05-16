package optvm.repositories;

import com.mongodb.client.MongoCollection;
import optvm.entities.OptimizationDetails;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class DetailsRepository {

    private static volatile DetailsRepository instance;

    private static final String COLECTION_NAME = "optimizations";
    private final MongoCollection<Document> collection;

    private DetailsRepository() {
        this.collection = OptvmDatabase
                .getInstance()
                .getCollection(COLECTION_NAME);
    }

    public static DetailsRepository getInstance() {
        if(instance == null) {
            synchronized (MetricsRepository.class) {
                if(instance == null) {
                        instance = new DetailsRepository();
                }
            }
        }
        return instance;
    }

    public OptimizationDetails find(String id) {
        this.collection.find(eq("", id));
        return null;
    }

    public OptimizationDetails insert(OptimizationDetails optimizationDetails) {
        return optimizationDetails;
    }

    public OptimizationDetails remove(String id) {
        return null;
    }
}
