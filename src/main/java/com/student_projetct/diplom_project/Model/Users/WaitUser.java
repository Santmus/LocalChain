package com.student_projetct.diplom_project.Model.Users;

import com.student_projetct.diplom_project.Model.RegexMethods.RegexEmailFound;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@Slf4j
public class WaitUser {

    private String email;
    private LocalDateTime beginDate;

    public WaitUser(String email){
        this.beginDate = LocalDateTime.now();
        setEmail(email);
        log.info("New wait user" + this + " - added in database");
    }
    @SneakyThrows
    public void setEmail(String email) {
        if (RegexEmailFound.emailCheck(email)) this.email = email;
    }
}
