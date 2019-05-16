package optvm.repositories;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class OptvmDatabase {

    private static volatile MongoClient connection;

    private OptvmDatabase() { }

    public static MongoDatabase getInstance() {
        if(connection == null) {
            synchronized (OptvmDatabase.class) {
                if(connection == null) {
                    connection = new MongoClient("127.0.0.1", 27017);
                }
            }
        }
        return connection.getDatabase("optvm");
    }
}
