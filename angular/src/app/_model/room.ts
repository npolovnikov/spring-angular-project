import {BaseEntity} from "./base-entity";
import {RoomHistory} from "./room-history";
import {InstrumentList} from "./instrumentList";

export class Room extends BaseEntity<RoomHistory>{
  number:string;
  block:string;
  instruments:InstrumentList[];
}
