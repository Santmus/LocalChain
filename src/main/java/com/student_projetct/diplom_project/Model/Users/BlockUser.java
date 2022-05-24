package com.student_projetct.diplom_project.Model.Users;

import com.student_projetct.diplom_project.Exception.InvalidLocalDateTime;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ResourceBundle;

@Data
@Slf4j
public class BlockUser {
    String email;
    String message;
    LocalDateTime beginDate;
    LocalDateTime endDate;
    ResourceBundle resourceBundle = ResourceBundle.getBundle("exception");

    public BlockUser(String email, LocalDateTime beginDate,
                     LocalDateTime endTime,  String message){
        this.beginDate = beginDate;
        this.endDate = endTime;
        this.message = message;
        setEmail(email);
        setBeginDate(beginDate);
        setEndTime(endTime);
        log.info("New block user" + this + " - added in database");
    }

    @SneakyThrows
    public void setEmail(String email) {
        this.email = email;
    }

    @SneakyThrows
    public void setBeginDate(LocalDateTime beginDate) {
        if (beginDate.isAfter(endDate) || beginDate.isEqual(endDate))
            throw new InvalidLocalDateTime(resourceBundle.getString("invalid.localDateTimeBegin"));
        else this.beginDate = beginDate;
    }

    @SneakyThrows
    public void setEndTime(LocalDateTime endTime) {
        if (endTime.isEqual(LocalDateTime.now()) || endTime.isBefore(LocalDateTime.now())) throw new InvalidLocalDateTime(resourceBundle.getString("invalid.localDateTimeEnd"));
        else this.endDate = endTime;
    }
}
