import {BaseEntity} from "./base-entity";
import {StudentHistory} from "./student-history";

export class Student extends BaseEntity<StudentHistory>{
  getName() {
    return "student";
  }
}
