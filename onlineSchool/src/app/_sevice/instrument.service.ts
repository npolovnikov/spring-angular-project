import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {Instrument} from "../_model/instrument";

@Injectable({
  providedIn: 'root'
})
export class InstrumentService {

  constructor(private _httpClient: HttpClient) {}

  getUserList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = '/api/instrument/list';
    const params = {
      start:page*pageSize,
      page: pageSize,
      params:{
        orderBy:sort,
        orderDir:order
      }
    }
    return this._httpClient.post<Page>(href, params);
  }

  getInstrumentByIdd(idd: number): Observable<Instrument> {
    const href = '/api/instrument/' + idd;

    return this._httpClient.get<Instrument>(href);
  }

  updateInstrument(idd: number, data: Instrument): Observable<Object> {
    const href = '/api/instrument/' + idd;
    return this._httpClient.patch(href, data);
  }

  createInstrument(data: Instrument): Observable<Object> {
    const href = '/api/instrument';
    return this._httpClient.post(href, data);
  }
}

