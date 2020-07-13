package ru.dfsystems.spring.tutorial.dao.user;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.tables.daos.UserDao;

@Repository
@AllArgsConstructor
public class UserDaoImpl extends UserDao {
    private final DSLContext jooq;
}

