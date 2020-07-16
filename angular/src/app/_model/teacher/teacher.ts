import {BaseEntity} from "../base/base-entity";
import {TeacherHistory} from "./teacher-history";

export class Teacher extends BaseEntity<TeacherHistory>{
  getName() {
    return "teacher";
  }
}
