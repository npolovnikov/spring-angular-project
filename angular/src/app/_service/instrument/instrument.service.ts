import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "../base/base.service";
import {InstrumentList} from "../../_model/instrument/instrumentList";
import {InstrumentHistory} from "../../_model/instrument/instrument-history";
import {Instrument} from "../../_model/instrument/instrument";

@Injectable({
  providedIn: 'root'
})
export class InstrumentService extends BaseService<InstrumentList, InstrumentHistory, Instrument> {
  constructor(_httpClient: HttpClient) {
    super(_httpClient, new Instrument());
  }
}
