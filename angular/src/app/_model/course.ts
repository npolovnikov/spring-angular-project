import {StudentList} from "./student-list";

export class Course {
  idd:number;
  name:string;
  description:string;
  teacherIdd:number;
  maxCountStudent:number;
  startDate:string;
  endDate:string;
  createDate:string;
  status:string;
  history:[];
  students: StudentList[];
}
