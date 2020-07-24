import {StudentList} from "./student-list";
import {LessonList} from "./lesson-list";
import {Teacher} from "./teacher";
import {TeacherList} from "./teacher-list";

export class Course {
  idd: number;
  name: string;
  description: string;
  teacher: TeacherList;
  maxCountStudent: number;
  dateStart: string;
  dateEnd: string;
  createDate :Date;
  status: string;
  students: StudentList[];
  lessons: LessonList[];
  history:[];
}
