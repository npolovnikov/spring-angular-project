import {BaseListEntity} from "../base/base-list-entity";
import {Teacher} from "../teacher/teacher";

export class CourseList extends BaseListEntity{
  name:string;
  description:string;
  maxCountStudent:number;
  startDate:string;
  endDate:string;
  status:string;
  teacher:Teacher;
}
