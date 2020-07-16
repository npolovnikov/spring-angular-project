package ru.student.studentSpring.tutorial.generate;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface JooqBase extends Serializable {
    Integer getId();
    void setId(Integer id);

    Integer getIdd();
    void setIdd(Integer idd);

    LocalDateTime getCreateDate();
    void setCreateDate(LocalDateTime createDate);

    LocalDateTime getDeleteDate();
    void setDeleteDate(LocalDateTime deleteDate);
}
