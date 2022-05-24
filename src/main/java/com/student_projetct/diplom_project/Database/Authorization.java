package com.student_projetct.diplom_project.Database;

import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import com.student_projetct.diplom_project.Enums.Database;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

@Slf4j
public class Authorization {

    private final static Scanner scanner = new Scanner(System.in);

    public static void authorization(String loginOrEmail, String password, ResourceBundle resourceBundle){
        try (var mongoClient = MongoClients.create()) {
            var database = mongoClient.getDatabase("data");
            var collection = database.getCollection(Database.USERS.getNameDatabase());
            var doc = collection.find(Filters.and(Filters.or
                            (Filters.eq("email", loginOrEmail), Filters.eq("login", loginOrEmail)),
                    Filters.eq("password", password)));
            while (doc.cursor().hasNext()) {
                System.out.println(doc.cursor().next());
                System.out.println(resourceBundle.getString("auth.authorization_success"));
                for (int i = 1; i <= 100; i++){
                    System.out.println(resourceBundle.getString("auth.authorization_process") + i + " %");
                    Thread.sleep(500);
                }
                log.info("User success authorization in system");
                return;
            }
            log.info("Wrong data about login/email or password. Please try again.");
            System.out.println(resourceBundle.getString("auth.invalid_data"));
            enterDataUser(resourceBundle);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void enterDataUser(ResourceBundle resourceBundle){
        System.out.println(resourceBundle.getString("auth.log_in"));
        System.out.println(resourceBundle.getString("auth.log_in_message"));

        String emailOrPassword = scanner.nextLine(),
                password = scanner.nextLine();
        log.info("login: \n\t" + emailOrPassword, "password: \n\t" + password);
        authorization(emailOrPassword, password, resourceBundle);
    }


    public static void main(String[] args) {
        enterDataUser(ResourceBundle.getBundle("language", Locale.FRANCE));
    }
}
