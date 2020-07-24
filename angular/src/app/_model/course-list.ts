import {TeacherList} from "./teacher-list";

export interface CourseList {
  idd: number;
  name: string;
  description: string;
  teacher: TeacherList;
  maxCountStudent: number;
  dateStart: Date;
  dateEnd: Date;
  createDate :Date;
  status: string;
}
