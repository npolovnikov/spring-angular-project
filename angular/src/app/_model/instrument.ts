import {BaseEntity} from "./base-entity";
import {InstrumentHistory} from "./instrument-history";

export class Instrument extends BaseEntity<InstrumentHistory>{
  getName() {
    return "instrument";
  }
}
