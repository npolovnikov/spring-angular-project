import {BaseListEntity} from "./base-list-entity";

export class StudentList extends BaseListEntity{
  firstName:string;
  middleName:string;
  lastName:string;
  passport:string;
  birthDate:string;
  status:string;
  getName() {
    return "student";
  }
}
