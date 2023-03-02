package lt.code.academy;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

public class CreateDatabase {

    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient("localhost", 27017);

        MongoDatabase database = mongoClient.getDatabase("moneyTransfer");

        MongoCollection<Document> usersCollection = database.getCollection("users");

        Document user1 = new Document("name", "Romanas")
                .append("balance", 1000.0);
        Document user2 = new Document("name", "Jonas")
                .append("balance", 500.0);
        Document user3 = new Document("name", "Ona")
                .append("balance", 2000.0);
        usersCollection.insertMany(Arrays.asList(user1, user2, user3));

        MongoCollection<Document> transactionsCollection = database.getCollection("transactions");

        Document transaction1 = new Document("sender", "Romanas")
                .append("receiver", "Jonas")
                .append("amount", 100.0);
        Document transaction2 = new Document("sender", "Ona")
                .append("receiver", "Romanas")
                .append("amount", 500.0);
        transactionsCollection.insertMany(Arrays.asList(transaction1, transaction2));

        System.out.println("Duomenu baze ir kolekcijos sekmingai sukurtos.");
    }
}
