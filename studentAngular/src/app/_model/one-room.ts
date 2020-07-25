import {Instrument} from "./instrument";

export class OneRoom {
  idd: number;
  number: string;
  block: string;
  createDate: string;
  history:[];
  instruments: Instrument[];
}
