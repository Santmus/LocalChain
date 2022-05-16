package com.student_projetct.diplom_project.Model.RegexMethods;

import com.student_projetct.diplom_project.Exception.InvalidPasswordData;
import lombok.SneakyThrows;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPasswordFound {

    private static final String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!]{8,}";
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("exception");

    @SneakyThrows
    public static boolean passwordCheck(String password){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if (matcher.matches()) return true;
        else throw new InvalidPasswordData(resourceBundle.getString("invalid.passwordData"));
    }
}
