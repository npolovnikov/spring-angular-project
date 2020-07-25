import {BaseHistoryEntity} from "../base/base-history-entity";

export class StudentHistory extends BaseHistoryEntity {
  firstName:string;
  middleName:string;
  lastName:string;
  passport:string;
  birthDate:string;
}
