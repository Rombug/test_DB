package lt.code.academy;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Scanner;

public class MoneyTransfer {

    public static void main(String[] args) {

        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("moneyTransfer");

        MongoCollection<Document> usersCollection = database.getCollection("users");
        MongoCollection<Document> transactionsCollection = database.getCollection("transactions");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Iveskite siuntejo varda:");
        String sender = scanner.nextLine();
        System.out.println("Iveskite gavejo varda:");
        String receiver = scanner.nextLine();
        System.out.println("Iveskite pinigu suma kuria norite pervesti:");
        double amount = scanner.nextDouble();

        Document senderDocument = usersCollection.find(new Document("name", sender)).first();
        Document receiverDocument = usersCollection.find(new Document("name", receiver)).first();

        if (senderDocument == null || receiverDocument == null) {
            System.out.println("Siunjtejas arba gavejas nerastas duomenu bazeje.");
            return;
        }

        double senderBalance = senderDocument.getDouble("balance");
        if (senderBalance < amount) {
            System.out.println("Nepakankamas pinigu likutis atlikti pavedima");
            return;
        }

        Document transactionDocument = new Document("sender", sender)
                .append("receiver", receiver)
                .append("amount", amount);
        transactionsCollection.insertOne(transactionDocument);

        usersCollection.updateOne(new Document("name", sender),
                new Document("$inc", new Document("balance", -amount)));
        usersCollection.updateOne(new Document("name", receiver),
                new Document("$inc", new Document("balance", amount)));

        System.out.println("Is " + sender + " " + "sekmingai pervesta " + "gavejui " + receiver + " " + amount + " Eur");
    }
}
