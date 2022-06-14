package com.student_projetct.diplom_project.Database;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.student_projetct.diplom_project.Enums.Database;
import com.student_projetct.diplom_project.Enums.Role;
import com.student_projetct.diplom_project.Exception.InvalidDataAccessOperation;
import com.student_projetct.diplom_project.Exception.InvalidLoginData;
import com.student_projetct.diplom_project.Model.Users.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

@Slf4j
public class Connection {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("exception");

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

    public static void getListOfCollections() {
        try (var mongoClient = MongoClients.create()) {
            var database = mongoClient.getDatabase("data");
            database.listCollectionNames().
                    forEach(System.out::println);
        }
    }

    @SneakyThrows
    public static void addUserToUserAndWaitUserDatabase(User user) {
        if (findEqualsData("email", user.getEmail())) throw new InvalidLoginData(resourceBundle.getString("invalid.emailDataExist"));
        else if (findEqualsData("login", user.getLogin())) throw new InvalidLoginData(resourceBundle.getString("invalid.loginDataExist"));
        try (var mongoClient = MongoClients.create()) {
            var database = mongoClient.getDatabase("data");
            var collection = database.getCollection(Database.USERS.getNameDatabase());
            collection.insertOne(addDataToDatabase(Database.USERS, user));
            collection = database.getCollection(Database.WAIT_USERS.getNameDatabase());
            collection.insertOne(addDataToDatabase(Database.WAIT_USERS, user));
            log.info(("The new member of the system" + user + "added to database"));
        }
    }

    private static Document addDataToDatabase(Database database, User user){
        if (database.getNameDatabase().equals(Database.USERS.getNameDatabase())) return new Document(Map.of("_id", new ObjectId().getDate().getTime(),
                "name", user.getName(),
                "surname", user.getSurname(), "age", user.getAge(),
                "sex", user.getSex().getSex(), "email", user.getEmail(),
                "login", user.getLogin(), "password", user.getPassword(),
                "status", user.getRole().getNameRole()));
        else if (database.getNameDatabase().equals(Database.WAIT_USERS.getNameDatabase())) return new Document(Map.of("_id", new ObjectId().getDate().getTime(), "email", user.getEmail(),
                "startDate", LocalDateTime.now()));
        else return new Document(Map.of("_id", new ObjectId().getDate().getTime(), "email", user.getEmail(),
                "startDate", user.getBeginDate(), "endDate", user.getEndDate(),
                "comment", user.getMessage()));
    }


    // изменить статус пользователя + изменить один параметр на пользователя администратора
    @SneakyThrows
    public static void changeStatusUser(Role privRole, User changeStatusUser, Role role) {
        if (privRole.getNameRole().equals(Role.ADMIN.getNameRole()) || privRole.getNameRole().equals(Role.SUPER_ADMIN.getNameRole())
                || privRole.getNameRole().equals(Role.SERVER.getNameRole()) || privRole.getNameRole().equals(Role.DEVELOPER_USER.getNameRole())) {
            if (!findEqualsData("email", changeStatusUser.getEmail())) throw new InvalidLoginData(resourceBundle.getString("invalid.emailDataDontExist"));
            else if (!findEqualsData("login", changeStatusUser.getLogin())) throw new InvalidLoginData(resourceBundle.getString("invalid.loginDataDontExist"));
            try (var mongoClient = MongoClients.create()) {
                var database = mongoClient.getDatabase("data");
                var collection = database.getCollection(Database.USERS.getNameDatabase());

                var doc = collection.findOneAndUpdate(Filters.eq("email", changeStatusUser.getEmail()),
                        Updates.set("status", role.getNameRole()));
                log.info("The new status of user: \nBefore:  " + changeStatusUser.getRole().getNameRole() + "\nAfter: " + role.getNameRole());

                Role prevStatus = changeStatusUser.getRole();
                System.out.println(prevStatus);
                changeStatusUser.setRole(role);

                // удаление пользователя из БД ожидания
                if (changeStatusUser.getRole().equals(Role.USER) && prevStatus.equals(Role.WAIT_USER)){
                    collection = database.getCollection(Database.WAIT_USERS.getNameDatabase());
                    var deleteDoc = collection.deleteOne(new Document("email", changeStatusUser.getEmail()));
                    log.info(changeStatusUser + " - in wait database delete");
                }

                // блокирока пользователя
               else if (changeStatusUser.getRole().equals(Role.BANNED_USER)) {
                    collection = database.getCollection(Database.BAN_USERS.getNameDatabase());
                    bannedUser(collection, changeStatusUser);
                }
            }
        } else throw new InvalidDataAccessOperation(resourceBundle.getString("invalid.databaseAcsessException"));
    }


    public static void bannedUser(MongoCollection<Document> collection, User user){
        Scanner stout = new Scanner(System.in);
        System.out.println("Please print time when users is unbanned in formant (YY-MM-DD-HH-MM)?: ");
        LocalDateTime endTime = LocalDateTime.of(stout.nextInt(), stout.nextInt(),
                stout.nextInt(), stout.nextInt(), stout.nextInt());

        log.info("User unbanned is : " + endTime);
        stout.nextLine();

        System.out.println("[Optional]: Write comment about why do blocked user?\n");

        User blockUser = new User(user.getEmail(), LocalDateTime.now(), endTime, stout.nextLine());
        collection.insertOne(addDataToDatabase(Database.BAN_USERS, blockUser));

        log.info("The user: " + user + "add to ban_database");
    }

    public static void main(String[] args) {

    }

}
