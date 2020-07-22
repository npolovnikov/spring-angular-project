//package ru.dfsystems.spring.tutorial.dto.lesson;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.Data;
//
//import java.time.LocalDateTime;
//
//@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class LessonParams {
//    private String name;
//    private String description;
//    private String extraInstruments;
//
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
//    private LocalDateTime lessonDateStart;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
//    private LocalDateTime lessonDateEnd;
//}
