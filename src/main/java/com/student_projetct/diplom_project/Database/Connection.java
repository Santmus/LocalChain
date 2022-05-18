package com.student_projetct.diplom_project.Database;

import com.mongodb.client.MongoClients;
import com.student_projetct.diplom_project.Enums.Role;
import com.student_projetct.diplom_project.Exception.InvalidDataAccessOperation;
import com.student_projetct.diplom_project.Exception.InvalidLoginData;
import com.student_projetct.diplom_project.Model.Users.User;
import lombok.SneakyThrows;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class Connection {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("exception");

    public static void getListOfCollections() {
        try (var mongoClient = MongoClients.create()) {
            var database = mongoClient.getDatabase("data");
            database.listCollectionNames().
                    forEach((Consumer<String>) System.out::println);
        }
    }

    public static boolean findEqualsData(String data, String str) {
        try (var mongoClient = MongoClients.create()) {
            var database = mongoClient.getDatabase("data");
            var collection = database.getCollection("users");
            var doc = collection.find(new Document(data, str));
            while (doc.cursor().hasNext()) {
                System.out.println(doc.cursor().next());
                return true;
            }
        }
        return false;
    }

    @SneakyThrows
    public static void addUserToUserAndWaitUserDatabase(User user) {
        if (findEqualsData("email", user.getEmail())) throw new InvalidLoginData(resourceBundle.getString("invalid.emailDataExist"));
        else if (findEqualsData("login", user.getLogin())) throw new InvalidLoginData(resourceBundle.getString("invalid.loginDataExist"));
        else {
            try (var mongoClient = MongoClients.create()) {
                var database = mongoClient.getDatabase("data");
                long objectId = new ObjectId().getDate().getTime();
                var collection = database.getCollection("users");
                var addDocument = new Document(Map.of("_id", objectId, "name", user.getName(),
                        "surname", user.getSurname(), "age", user.getAge(),
                        "sex", user.getSex().getSex(), "email", user.getEmail(),
                        "login", user.getLogin(), "password", user.getPassword(),
                        "status", user.getRole().getNameRole()));
                collection.insertOne(addDocument);
                collection = database.getCollection("wait_users");
                addDocument = new Document(Map.of("_id", objectId, "email", user.getEmail(),
                        "startDate", LocalDateTime.now()));
                collection.insertOne(addDocument);
            }
        }
    }

    @SneakyThrows
    public static void changeStatusUser(Role role, User user) {
        if (role.getNameRole().equals(Role.ADMIN.getNameRole()) || role.getNameRole().equals(Role.SUPER_ADMIN.getNameRole())) {
            try (var mongoClient = MongoClients.create()) {
                var database = mongoClient.getDatabase("data");
                var collection = database.getCollection("users");
                var doc = collection.findOneAndUpdate(new Document(Map.of("email", user.getEmail(),
                        "status", "Waituser")), new Document("status", role.getNameRole()));
            }
        } else throw new InvalidDataAccessOperation(resourceBundle.getString("invalid.databaseAcsessException"));
    }
    public static void main(String[] args) {
    }
}
