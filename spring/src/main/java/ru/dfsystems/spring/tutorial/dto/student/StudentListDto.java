package ru.dfsystems.spring.tutorial.dto.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentListDto extends BaseListDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private String passport;
//    private String birthDate;
//    private LocalDateTime birthDate;
    private String status;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime birthDate;

}
