import {BaseEntity} from "./base-entity";
import {TeacherHistory} from "./teacher-history";

export class Teacher extends BaseEntity<TeacherHistory>{
  getName() {
    return "teacher";
  }
}
