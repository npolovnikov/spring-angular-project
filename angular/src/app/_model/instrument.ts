import {InstrumentList} from "./instrument-list";
import {RoomList} from "./room-list";

export class Instrument {
  idd: number;
  name: string;
  number: string;
  createDate: string;
  rooms:RoomList[];
  history:[];
}
