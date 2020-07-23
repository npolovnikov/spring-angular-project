import {InstrumentList} from "./instrument-list";
import {CourseList} from "./course-list";

export class Student {
  idd:number;
  firstName:string;
  lastName:string;
  middleName:string;
  passport:string;
  birthDate:string;
  status:string;
  createDate:string;

  history: [];
  courses:CourseList[];
}
