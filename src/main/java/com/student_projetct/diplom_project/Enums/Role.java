package com.student_projetct.diplom_project.Enums;

public enum Role {

    SUPER_ADMIN("Superadmin"),
    ADMIN("Admin"),
    USER("User"),
    WAIT_USER("Waituser"),
    BANNED_USER("Banuser"),
    DEVELOPER_USER("Developer"),
    SERVER("Server");

    private final String nameRole;

    Role(String nameRole){
        this.nameRole = nameRole;
    }

    public String getNameRole() {
        return nameRole;
    }
}
