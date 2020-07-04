package ru.student.studentSpring.tutorial.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.student.studentSpring.tutorial.dto.room.RoomParams;

import java.io.Serializable;

@Data
public class PageParams<T> implements Serializable {
    private int start;
    private int page;
    private T params;

//    public RoomParams getParams() {
//        return null;
//    }
//
//    public int getStart() {
//        return start;
//    }
//
//    public void setStart(int start) {
//        this.start = start;
//    }
//
//    public int getPage() {
//        return page;
//    }
//
//    public void setPage(int page) {
//        this.page = page;
//    }
//
//    public void setParams(T params) {
//        this.params = params;
//    }
}
