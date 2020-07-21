package ru.dfsystems.spring.tutorial.dao;

import lombok.val;
import org.jooq.*;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.tools.Checker;
import ru.dfsystems.spring.tutorial.tools.SQLer;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.CourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.generated.tables.records.CourseRecord;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.dfsystems.spring.tutorial.generated.tables.Course.COURSE;
import static ru.dfsystems.spring.tutorial.generated.tables.StudentToCourse.STUDENT_TO_COURSE;

@Repository
public class CourseDaoImpl extends CourseDao  implements BaseDao<Course>, BaseListDao<Course, CourseParams> {
    private final DSLContext jooq;

    public CourseDaoImpl(Configuration configuration, DSLContext jooq) {
        super(configuration);
        this.jooq = jooq;
    }

    @Override
    public Page<Course> list(PageParams<CourseParams> pageParams) {
        val listQuery = getCourseSelect(pageParams);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Course> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Course.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<CourseRecord> getCourseSelect(PageParams<CourseParams> pageParams) {
        final CourseParams params = pageParams.getParams() == null ? new CourseParams() : pageParams.getParams();
        Condition condition = COURSE.DELETE_DATE.isNull();
        String name = params.getName();
        String status = params.getStatus();
        if (Checker.checkEmpty(name)) {
            condition = condition.and(COURSE.NAME.like(name));
        }
        if (Checker.checkEmpty(status)) {
            condition = condition.and(COURSE.STATUS.like(status));
        }

        Map<String, TableField<? extends TableRecord, ?>> orderMap = new HashMap<>();
        orderMap.put("idd", COURSE.IDD);
        orderMap.put("name", COURSE.NAME);
        orderMap.put("status", COURSE.STATUS);
        val sort = SQLer.getSortField(orderMap, COURSE.IDD, pageParams.getOrderBy(), pageParams.getOrderDir());

        return jooq.selectFrom(COURSE)
                .where(condition)
                .orderBy(sort);
    }

    @Override
    public Course create(Course course, Integer userId) {
        return this.create(course);
    }

    @Override
    public Course getActiveByIdd(Integer idd) {
        return jooq.select(COURSE.fields())
                .from(COURSE)
                .where(COURSE.IDD.eq(idd).and(COURSE.DELETE_DATE.isNull()))
                .fetchOneInto(Course.class);
    }

    public Course create(Course course) {
        course.setId(jooq.nextval(Sequences.COURSE_ID_SEQ));
        if (course.getIdd() == null) {
            course.setIdd(course.getId());
        }
        course.setCreateDate(LocalDateTime.now());
        course.setTeacherIdd(1); // TODO: 18.07.2020 заглушка для учителя
        super.insert(course);
        return course;
    }

    public List<Course> getCoursesByStudentIdd(Integer idd) {
        return jooq.select(COURSE.fields())
                .from(COURSE)
                .join(STUDENT_TO_COURSE)
                .on(COURSE.IDD.eq(STUDENT_TO_COURSE.COURSE_IDD))
                .where(STUDENT_TO_COURSE.STUDENT_IDD.eq(idd))
                .fetchInto(Course.class);
    }

}
