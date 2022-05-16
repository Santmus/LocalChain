package com.student_projetct.diplom_project.Model.RegexMethods;

import com.student_projetct.diplom_project.Exception.InvalidEmailData;
import lombok.SneakyThrows;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexEmailFound {
    private static final String regex = "^([a-z0-9_-]+\\.)*[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("exception");

    @SneakyThrows
    public static boolean emailCheck(String email){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) return true;
        else throw new InvalidEmailData(resourceBundle.getString("invalid.emailData"));
    }
}