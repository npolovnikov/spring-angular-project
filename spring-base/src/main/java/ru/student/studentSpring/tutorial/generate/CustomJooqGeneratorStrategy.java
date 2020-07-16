package ru.student.studentSpring.tutorial.generate;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class CustomJooqGeneratorStrategy extends DefaultGeneratorStrategy {
    @Override
    public List<String> getJavaClassImplements(Definition definition, Mode mode) {

        return Collections.singletonList(JooqBase.class.getName());
    }
}
