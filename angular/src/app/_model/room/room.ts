import {BaseEntity} from "../base/base-entity";
import {RoomHistory} from "./room-history";
import {InstrumentList} from "../instrument/instrumentList";

export class Room extends BaseEntity<RoomHistory>{
  number:string;
  block:string;
  instruments:InstrumentList[];
  getName() {
    return "room";
  }
}
