package optvm.repositories;

import com.mongodb.client.MongoCollection;
import optvm.entities.OptimizationMetric;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;

import static com.mongodb.client.model.Filters.eq;

public class MetricsRepository {

    private static volatile MetricsRepository instance;

    private static final String COLECTION_NAME = "optimizations";
    private final MongoCollection<Document> collection;

    private MetricsRepository() {
        this.collection = OptvmDatabase
                .getInstance()
                .getCollection(COLECTION_NAME);
    }

    public static MetricsRepository getInstance() {
        if(instance == null) {
            synchronized (MetricsRepository.class) {
                if(instance == null) {
                        instance = new MetricsRepository();
                }
            }
        }
        return instance;
    }

    public OptimizationMetric find(String id) {
        Document document = this.collection
                .find(eq("_id", new ObjectId(id)))
                .first();

        if(document == null) return null;

        Document metricDoc = (Document) document.get("metrics");
        return this.fromDBToMetric(metricDoc);
    }

    public OptimizationMetric insert(String id, OptimizationMetric metric) {
        Document document = this.fromMetricToDB(metric);
        Document result = this.collection.findOneAndUpdate(
                eq("_id", new ObjectId(id)),
                new Document("$set", new Document("metrics", document))
        );
        return result != null ? this.fromDBToMetric(document) : null;
    }

    public OptimizationMetric remove(String id) {
        return null;
    }

    private OptimizationMetric fromDBToMetric(Document document) {
        OptimizationMetric metric = new OptimizationMetric();
        metric.setInputHostsLenth((int) document.get("inputHostsLength"));
        metric.setConstraintExecutionTime((long) document.get("constraintExecutionTime"));
        metric.setOptimizationExecutionTime((long) document.get("optimizationExecutionTime"));
        metric.setRemovedByConstraints((int) document.get("removedByConstraints"));
        metric.setOutputSolutionsLenth((int) document.get("outputSolutionsLength"));
        return metric;
    }

    private Document fromMetricToDB(OptimizationMetric metrics) {
        Document document = new Document()
                .append("inputHostsLength", metrics.getInputHostsLenth())
                .append("outputSolutionsLength", metrics.getOutputSolutionsLenth())
                .append("constraintExecutionTime", metrics.getConstraintExecutionTime())
                .append("optimizationExecutionTime", metrics.getOptimizationExecutionTime())
                .append("removedByConstraints", metrics.getRemovedByConstraints());

        return document;
    }
}
