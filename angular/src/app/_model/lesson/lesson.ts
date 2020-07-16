import {BaseEntity} from "../base/base-entity";
import {LessonHistory} from "./lesson-history";

export class Lesson extends BaseEntity<LessonHistory>{
  getName() {
    return "lesson";
  }
}
