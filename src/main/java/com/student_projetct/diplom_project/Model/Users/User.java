package com.student_projetct.diplom_project.Model.Users;

import com.student_projetct.diplom_project.Enums.Role;
import com.student_projetct.diplom_project.Enums.Sex;
import com.student_projetct.diplom_project.Exception.InvalidAgeData;
import com.student_projetct.diplom_project.Exception.InvalidLocalDateTime;
import com.student_projetct.diplom_project.Model.RegexMethods.RegexEmailFound;
import com.student_projetct.diplom_project.Model.RegexMethods.RegexPasswordFound;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ResourceBundle;

@Data
@Slf4j
public class User {
    private String login;
    private String password;
    private String email;
    private String name;
    private String surname;
    private String message;

    private int age;

    private LocalDateTime beginDate;
    private LocalDateTime endDate;

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

    public User(String email, LocalDateTime beginDate,
                LocalDateTime endDate, String message) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.message = message;
        setEmail(email);
        setBeginDate(beginDate);
        setEndDate(endDate);
        log.info("New block user" + this + " - added in database");
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

    @SneakyThrows
    public void setEndDate(LocalDateTime endDate) {
        if (endDate.isEqual(LocalDateTime.now()) || endDate.isBefore(LocalDateTime.now())) throw new InvalidLocalDateTime(resourceBundle.getString("invalid.localDateTimeEnd"));
        else this.endDate = endDate;
    }

    @SneakyThrows
    public void setBeginDate(LocalDateTime beginDate) {
        if (beginDate.isAfter(endDate) || beginDate.isEqual(endDate))
            throw new InvalidLocalDateTime(resourceBundle.getString("invalid.localDateTimeBegin"));
        else this.beginDate = beginDate;
    }
}
