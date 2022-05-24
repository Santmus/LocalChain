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

    private static final Scanner stout = new Scanner(System.in);

    // нахождение одинаковый данных в БД, при регистрации пользователя в системе
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

    // добавление пользователя в систему, в одну из БД
    private static Document addDataToDatabase(Database database, User user){
        if (database.getNameDatabase().equals(Database.USERS.getNameDatabase())) return new Document(Map.of("_id", new ObjectId().getDate().getTime(),
                "name", user.getName(),
                "surname", user.getSurname(), "age", user.getAge(),
                "sex", user.getSex().getSex(), "email", user.getEmail(),
                "login", user.getLogin(), "password", user.getPassword(),
                "status", user.getRole().getNameRole()));
        else return new Document(Map.of("_id", new ObjectId().getDate().getTime(), "email", user.getEmail(),
                "startDate", LocalDateTime.now()));
    }

    private static Document addDataToDatabase(BlockUser user){
        return new Document(Map.of("_id", new ObjectId().getDate().getTime(), "email", user.getEmail(),
                "startDate", user.getBeginDate(), "endDate", user.getEndDate(),
                "comment", user.getMessage()));
    }

    //получить список всех коллекции в БД
    public static void getListOfCollections() {
        try (var mongoClient = MongoClients.create()) {
            var database = mongoClient.getDatabase("data");
            database.listCollectionNames().
                    forEach((Consumer<String>) System.out::println);
        }
    }

    // добавить пользователя в БД осн. пол, и ожид. подтв пол
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

    // изменить статус пользователя + изменить один параметр на пользователя администратора + добавить проверку на состояние пред
    @SneakyThrows
    public static void changeStatusUser(Role privRole, User changeStatusUser, Role role) {
        if (privRole.getNameRole().equals(Role.ADMIN.getNameRole()) || privRole.getNameRole().equals(Role.SUPER_ADMIN.getNameRole())) {
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

                if (changeStatusUser.getRole().equals(Role.USER) && prevStatus.equals(Role.WAIT_USER)){
                    collection = database.getCollection(Database.WAIT_USERS.getNameDatabase());
                    var deleteDoc = collection.deleteOne(new Document("email", changeStatusUser.getEmail()));
                    log.info(changeStatusUser + " - in wait database delete");
                }

               else if (changeStatusUser.getRole().equals(Role.BANNED_USER) && (prevStatus.equals(Role.USER))) {
                    collection = database.getCollection(Database.BAN_USERS.getNameDatabase());

                    System.out.println("Please print time when users is unbanned in formant (YY-MM-DD-HH-MM)?: ");
                    LocalDateTime endTime = LocalDateTime.of(stout.nextInt(), stout.nextInt(),
                            stout.nextInt(), stout.nextInt(), stout.nextInt());

                    log.info("User unbanned is : " + endTime);
                    stout.nextLine();

                    System.out.println("[Optional]: Write comment about why do blocked user?\n");

                    BlockUser blockUser = new BlockUser(changeStatusUser.getEmail(), LocalDateTime.now(), endTime, stout.nextLine());
                    collection.insertOne(addDataToDatabase(blockUser));

                    log.info("The user: " + changeStatusUser + "add to ban_database");


                }
            }
        } else throw new InvalidDataAccessOperation(resourceBundle.getString("invalid.databaseAcsessException"));
    }

    /* Вх. данные
    * User yauheni = new User("Yauheni", "Kazachenka", 21, Sex.MALE, "stalker12345600@gmail.com", "santmusLive", "7893480Qw");
    * User petrovich =new User("Pert", "Petrovich", 45, null, "evangeluin123@gmaul.com", "evangele", "7893480wE00");
    */
    public static void main(String[] args) {
        User yauheni = new User("Yauheni", "Kazachenka", 21, Sex.MALE, "stalker12345600@gmail.com", "santmusLive", "7893480Qw");
        changeStatusUser(Role.ADMIN, yauheni, Role.USER);
    }

}
