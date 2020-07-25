import {BaseEntity} from "../base/base-entity";
import {StudentHistory} from "./student-history";
import {CourseList} from "../../../../../../../../../../Study/spring-angular-project/angular/src/app/_model/course/courseList";

export class Student extends BaseEntity<StudentHistory>{
  firstName:string;
  middleName:string;
  lastName:string;
  passport:string;
  birthDate:string;
  courses:CourseList[];
  getName() {
    return "student";
  }
}
