import {CourseList} from "./course-list";

export class Student {
  idd:number;
  firstName:string;
  middleName:string;
  lastName:string;
  passport:string;
  birthDate:string;
  createDate:string;
  status:string;
  history:[];
  courses: CourseList[];
}
