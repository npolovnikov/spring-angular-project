package ru.dfsystems.spring.tutorial.dao.student;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.tables.daos.StudentDao;

@Repository
@AllArgsConstructor
public class StudentDaoImpl extends StudentDao {
    final DSLContext jooq;
}
