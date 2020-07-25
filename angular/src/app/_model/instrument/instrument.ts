import {BaseEntity} from "../base/base-entity";
import {InstrumentHistory} from "./instrument-history";

export class Instrument extends BaseEntity<InstrumentHistory>{
  name:string;
  number:string;
  getName() {
    return "instrument";
  }
}
