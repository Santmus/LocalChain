package com.student_projetct.diplom_project;

import java.util.Date;

public class Class {

    public static void main(String[] args) {
        System.out.println("[\n" +
                "\t{" +
                "\n\t\t\"index:\" 0, " +
                "\n\t\t\"hash:\" \"000002559a8c431f429f815a9d11760ed3946fd5d1f4bf11b3872925bacdf2\"," +
                "\n\t\t\"previousBlock:\" \"0\"," +
                "\n\t\t\"timestamp:\" " + new Date().getTime() +  "," +
                "\n\t\t\"data:\"[" +
                "\n\t\t" +
                "\n\t\t],\n" +
                "}");
        System.out.println("[\n" +
                "\t{" +
                "\n\t\t\"index:\" 1, " +
                "\n\t\t\"hash:\" \"000006c8a6271d3aa6f07237fb3f20d91dd0a40e4019fda6c6c69376d9dd5738\"," +
                "\n\t\t\"previousBlock:\" \"000002559a8c431f429f815a9d11760ed3946fd5d1f4bf11b3872925bacdf2\"," +
                "\n\t\t\"timestamp:\" " + new Date().getTime() +  "," +
                "\n\t\t\"data:\"[" +
                "\n\t\t" +
                "\n\t\t],\n" +
                "}");
        System.out.println("[\n" +
                "\t{" +
                "\n\t\t\"index:\" 2, " +
                "\n\t\t\"hash:\" \"000002559a8c431f429f8155a9d11760ed3946fd5daf4bf11b3872925bacdbf2\"," +
                "\n\t\t\"previousBlock:\" \"000006c8a6271d3aa6f07237fb3f20d91dd0a40e4019fda6c6c69376d9dd5738\"," +
                "\n\t\t\"timestamp:\" " + new Date().getTime() +  "," +
                "\n\t\t\"data:\"[" +
                "\n\t\t\t\"email_user\": \"82170044@bsuir.by\"," +
                "\n\t\t\t\"operation\": \"tranfer to user\"," +
                "\n\t\t\t\"email_user_get_money\": \"ivanovskiyIvan@gmail.com\"," +
                "\n\t\t\t\"money\": \"1.45cLC\"," +
                "\n\t\t\t\"money_left\": \"1.83cLC\"," +
                "\n\t\t],\n" +
                "}");
        System.out.println("[\n" +
                "\t{" +
                "\n\t\t\"index:\" 3, " +
                "\n\t\t\"hash:\" \"000006c8a6271d3aa6f07237fb3f20d91dd0a40e4019fda6c6c69376d9dd5738\"," +
                "\n\t\t\"previousBlock:\" \"000002559a8c431f429f8155a9d11760ed3946fd5daf4bf11b3872925bacdbf2\"," +
                "\n\t\t\"timestamp:\" " + new Date().getTime() +  "," +
                "\n\t\t\"data:\"[" +
                "\n\t\t\t\"email_user\": \"ivanovskiyIvan@gmail.com\"," +
                "\n\t\t\t\"operation\": \"status wallet\"," +
                "\n\t\t\t\"before_money\": \"0.0cLC\"," +
                "\n\t\t\t\"money_left\": \"1.45cLC\"," +
                "\n\t\t],\n" +
                "}");

    }
}
