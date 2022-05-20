package com.student_projetct.diplom_project.Enums;

public enum Database {
    USERS("users"),
    WAIT_USERS("wait_users"),
    BAN_USERS("ban_users");

    private String nameDatabase;

    Database(String nameDatabase) {
        this.nameDatabase = nameDatabase;
    }

    public String getNameDatabase() {
        return nameDatabase;
    }
}
