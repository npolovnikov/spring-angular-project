import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {InstrumentList} from "../_model/instrumentList";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";
import {Room} from "../_model/room";
import {InstrumentList} from "../_model/instrument-list";

@Injectable({
  providedIn: 'root'
})
export class InstrumentService extends BaseService<InstrumentList>{
  constructor(_httpClient: HttpClient) {
    super(_httpClient, new InstrumentList());
  }

  getInstrumentByIdd(idd: number): Observable<InstrumentList> {
    const href = '/api/instrument/' + idd;

    return this._httpClient.get<InstrumentList>(href);
  }
}
