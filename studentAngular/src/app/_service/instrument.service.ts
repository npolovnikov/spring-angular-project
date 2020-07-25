import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";
import {OneRoom} from "../_model/one-room";
import {Instrument} from "../_model/instrument";

@Injectable({
  providedIn: 'root'
})
export class InstrumentService {

  constructor(private _httpClient: HttpClient) {
  }

  getInstrumentList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = 'api/instrument/list';
    return this._httpClient.post<Page>(href, new PageParams(page * pageSize, pageSize, {
      orderBy:sort,
      orderDir:order
    }));
  }

  getInstrumentByIdd(idd: number): Observable<Instrument> {
    const href = '/api/instrument/' + idd;

    return this._httpClient.get<Instrument>(href)
  }
}
