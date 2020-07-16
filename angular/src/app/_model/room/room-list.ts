import {BaseListEntity} from "../base/base-list-entity";

export class RoomList extends BaseListEntity{
  number:string;
  block:string;
  getName() {
    return "room";
  }
}
