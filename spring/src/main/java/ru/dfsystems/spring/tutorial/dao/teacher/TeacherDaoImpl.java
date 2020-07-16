package ru.dfsystems.spring.tutorial.dao.teacher;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.tables.daos.TeacherDao;

@Repository
@AllArgsConstructor
public class TeacherDaoImpl extends TeacherDao {
    final DSLContext jooq;
}
