import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {InstrumentList} from "../_model/instrumentList";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class InstrumentService extends BaseService<InstrumentList>{
  private httpClient: HttpClient;

  constructor(_httpClient: HttpClient) {
    super(_httpClient, new InstrumentList());
    this.httpClient = _httpClient;
  }

  getInstrumentByIdd(idd: number): Observable<InstrumentList> {
    const href = '/api/instrument/' + idd;

    return this.httpClient.get<InstrumentList>(href);
  }
}
