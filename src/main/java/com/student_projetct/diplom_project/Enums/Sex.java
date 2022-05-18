package com.student_projetct.diplom_project.Enums;

public enum Sex {
    MALE("male"),
    FEMALE("female"),
    UNKNOWN("unknown");

    private String sex;

    Sex(String sex){
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }
}
