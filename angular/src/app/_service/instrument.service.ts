import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {InstrumentList} from "../_model/instrumentList";
import {Observable} from "rxjs";
import {InstrumentHistory} from "../_model/instrument-history";
import {Instrument} from "../_model/instrument";

@Injectable({
  providedIn: 'root'
})
export class InstrumentService extends BaseService<InstrumentList, InstrumentHistory, Instrument> {
  private httpClient: HttpClient;

  constructor(_httpClient: HttpClient) {
    super(_httpClient, new Instrument());
    this.httpClient = _httpClient;
  }

  getInstrumentByIdd(idd: number): Observable<InstrumentList> {
    const href = '/api/instrument/' + idd;

    return this.httpClient.get<InstrumentList>(href);
  }
}
