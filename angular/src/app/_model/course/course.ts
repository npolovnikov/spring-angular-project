import {BaseEntity} from "../base/base-entity";
import {CourseHistory} from "./course-history";

export class Course extends BaseEntity<CourseHistory>{
  getName() {
    return "course";
  }
}
