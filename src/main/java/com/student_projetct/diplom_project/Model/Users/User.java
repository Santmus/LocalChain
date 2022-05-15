package com.student_projetct.diplom_project.Model.Users;

import com.student_projetct.diplom_project.Enums.Role;
import com.student_projetct.diplom_project.Enums.Sex;

public class User {
    private String login;
    private String password;
    private String email;
    private String name;
    private String surname;

    private int age;
    private Sex sex;

    private Role role;

    public User(String name, String surname, int age,
                Sex sex, String email, String login,
                String password, Role role){
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.sex = sex;
        this.role = role;
        //
    }



}
