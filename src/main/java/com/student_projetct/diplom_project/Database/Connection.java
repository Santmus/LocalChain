package com.student_projetct.diplom_project.Database;

import com.mongodb.client.MongoClients;
import org.bson.Document;

import java.util.function.Consumer;

public class Connection {

    public static void getCollections(){
        try (var mongoClient = MongoClients.create()){
            var database = mongoClient.getDatabase("data");
            database.listCollectionNames().
                    forEach((Consumer<String>) System.out::println);
        }
    }

    public static boolean findEqualsData(String data, String str){
        try (var mongoClient = MongoClients.create()){
            var database = mongoClient.getDatabase("data");
            var collection = database.getCollection("users");
            var doc = collection.find(new Document(data, str));
            while (doc.cursor().hasNext()){
                System.out.println(doc.cursor().next());
                return true;
            }
        }
        return false;
    }

}
