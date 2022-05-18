package com.student_projetct.diplom_project.Model.Users;

import com.student_projetct.diplom_project.Database.Connection;
import com.student_projetct.diplom_project.Enums.Role;
import com.student_projetct.diplom_project.Enums.Sex;
import com.student_projetct.diplom_project.Exception.InvalidAgeData;
import com.student_projetct.diplom_project.Exception.InvalidLoginData;
import com.student_projetct.diplom_project.Model.RegexMethods.RegexEmailFound;
import com.student_projetct.diplom_project.Model.RegexMethods.RegexPasswordFound;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ResourceBundle;

@Data
@Slf4j
public class User {
    private String login;
    private String password;
    private String email;
    private String name;
    private String surname;
    private int age;
    private Sex sex;
    private Role role;
    ResourceBundle resourceBundle = ResourceBundle.getBundle("exception");

    public User(String name, String surname, int age,
                Sex sex, String email, String login,
                String password){
        this.name = name;
        this.surname = surname;
        this.role = Role.WAIT_USER;
        this.age = age;

        setAge(age);
        setSex(sex);
        setEmail(email);
        setLogin(login);
        setPassword(password);
        log.info("The new user: " + this + " - create");
    }

    public void setEmail(String email) { if (RegexEmailFound.emailCheck(email)) this.email = email; }

    public void setPassword(String password) { if (RegexPasswordFound.passwordCheck(password)) this.password = password; }

    public void setSex(Sex sex) {
        if (sex == null){ this.sex = Sex.UNKNOWN; }
        else this.sex = sex;
    }

    @SneakyThrows
    public void setAge(int age) {
        if (this.age >= 18 && this.age <= 99) this.age = age;
        else throw new InvalidAgeData(resourceBundle.getString("invalid.ageData"));
    }

    @SneakyThrows
    public void setLogin(String login) {
        this.login = login;
    }

}
