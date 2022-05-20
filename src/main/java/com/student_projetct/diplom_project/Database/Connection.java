package com.student_projetct.diplom_project.Database;

import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.student_projetct.diplom_project.Enums.Database;
import com.student_projetct.diplom_project.Enums.Role;
import com.student_projetct.diplom_project.Enums.Sex;
import com.student_projetct.diplom_project.Exception.InvalidDataAccessOperation;
import com.student_projetct.diplom_project.Exception.InvalidLoginData;
import com.student_projetct.diplom_project.Model.Users.BlockUser;
import com.student_projetct.diplom_project.Model.Users.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.Consumer;
@Slf4j
public class Connection {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("exception");

    public static void getListOfCollections() {
        try (var mongoClient = MongoClients.create()) {
            var database = mongoClient.getDatabase("data");
            database.listCollectionNames().
                    forEach((Consumer<String>) System.out::println);
        }
    }

    private static boolean findEqualsData(String data, String str) {
        try (var mongoClient = MongoClients.create()) {
            var database = mongoClient.getDatabase("data");
            var collection = database.getCollection(Database.USERS.getNameDatabase());
            var doc = collection.find(new Document(data, str));
            while (doc.cursor().hasNext()) {
                System.out.println(doc.cursor().next());
                return true;
            }
        }
        return false;
    }

    private static Document addDataToDatabase(Database database, User user){
        if (database.getNameDatabase().equals(Database.USERS.getNameDatabase())) return new Document(Map.of("_id", new ObjectId().getDate().getTime(),
                "name", user.getName(),
                "surname", user.getSurname(), "age", user.getAge(),
                "sex", user.getSex().getSex(), "email", user.getEmail(),
                "login", user.getLogin(), "password", user.getPassword(),
                "status", user.getRole().getNameRole()));
        else return  new Document(Map.of("_id", new ObjectId().getDate().getTime(), "email", user.getEmail(),
                "startDate", LocalDateTime.now()));
    }

    private static Document addDataToDatabase(BlockUser user){
        return new Document(Map.of("_id", new ObjectId().getDate().getTime(), "email", user.getEmail(),
                "startDate", user.getBeginDate(), "endDate", user.getEndDate(),
                "comment", user.getMessage()));
    }

    @SneakyThrows
    public static void addUserToUserAndWaitUserDatabase(User user) {
        if (findEqualsData("email", user.getEmail())) throw new InvalidLoginData(resourceBundle.getString("invalid.emailDataExist"));
        else if (findEqualsData("login", user.getLogin())) throw new InvalidLoginData(resourceBundle.getString("invalid.loginDataExist"));
        else {
            try (var mongoClient = MongoClients.create()) {
                var database = mongoClient.getDatabase("data");
                var collection = database.getCollection(Database.USERS.getNameDatabase());
                collection.insertOne(addDataToDatabase(Database.USERS, user));
                collection = database.getCollection(Database.WAIT_USERS.getNameDatabase());
                collection.insertOne(addDataToDatabase(Database.WAIT_USERS, user));
                log.info(("The new member of the system" + user + "added to database"));
            }
        }
    }

    // поменять на пользователя
    @SneakyThrows
    public static void changeStatusUser(Role privRole, User changeStatusUser, Role role) {
        if (privRole.getNameRole().equals(Role.ADMIN.getNameRole()) || privRole.getNameRole().equals(Role.SUPER_ADMIN.getNameRole())) {
            try (var mongoClient = MongoClients.create()) {
                var database = mongoClient.getDatabase("data");
                var collection = database.getCollection(Database.USERS.getNameDatabase());
                var doc = collection.findOneAndUpdate(Filters.eq("email", changeStatusUser.getEmail()), Updates.set("status", role.getNameRole()));

                log.info("The new status of user: \nBefore:  " + changeStatusUser.getRole().getNameRole() + "\nAfter: " + role.getNameRole());
                changeStatusUser.setRole(role);

                if (changeStatusUser.getRole().equals(Role.USER)){
                    collection = database.getCollection(Database.WAIT_USERS.getNameDatabase());
                    var deleteDoc = collection.deleteOne(new Document("email", changeStatusUser.getEmail()));
                    log.info(changeStatusUser + " - in wait database delete");
                }

                if (changeStatusUser.getRole().equals(Role.BANNED_USER)){
                    collection = database.getCollection(Database.BAN_USERS.getNameDatabase());

                    Scanner sout = new Scanner(System.in);
                    System.out.println("Please print time when users is unbanned?: ");
                    LocalDateTime endTime = LocalDateTime.of(sout.nextInt(), sout.nextInt(), sout.nextInt(), sout.nextInt(), sout.nextInt());
                    log.info("User unbanned is : " + endTime);
                    sout.nextLine();
                    System.out.println("[Optional]: Write comment about why do blocked user?\n");
                    BlockUser blockUser = new BlockUser(changeStatusUser.getEmail(), LocalDateTime.now(), endTime, sout.nextLine());
                    collection.insertOne(addDataToDatabase(blockUser));
                    log.info("The user: " + changeStatusUser + "add to ban_database");
                }
            }
        } else throw new InvalidDataAccessOperation(resourceBundle.getString("invalid.databaseAcsessException"));
    }


    public static void main(String[] args) {
    User yauheni = new User("Yauheni", "Kazachenka", 21, Sex.MALE, "stalker12345600@gmail.com", "santmusLive", "7893480Qw");
    changeStatusUser(Role.ADMIN, yauheni, Role.BANNED_USER);
    }
}
