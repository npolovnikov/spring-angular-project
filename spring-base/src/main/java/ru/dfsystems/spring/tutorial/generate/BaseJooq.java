package ru.dfsystems.spring.tutorial.generate;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface BaseJooq extends Serializable {
    Integer getId();
    void setId(Integer id);

    Integer getIdd();
    void setIdd(Integer idd);

    LocalDateTime getCreatedAt();
    void setCreatedAt(LocalDateTime createdAt);

    LocalDateTime getDeletedAt();
    void setDeletedAt(LocalDateTime deletedAt);
}
