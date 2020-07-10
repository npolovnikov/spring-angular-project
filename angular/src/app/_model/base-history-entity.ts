import {BaseListEntity} from "./base-list-entity";

export abstract class BaseHistoryEntity extends BaseListEntity{
  id:number;
  deleteDate:string;
}
