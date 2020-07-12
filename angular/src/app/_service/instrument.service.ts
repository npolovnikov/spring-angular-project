import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";
import {InstrumentList} from "../_model/instrument-list";

@Injectable({
  providedIn: 'root'
})
export class InstrumentService {

  constructor(private _httpClient: HttpClient) {}

  getInstrumentList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = '/api/instrument/list';

    return this._httpClient.post<Page>(href, new PageParams(page*pageSize, pageSize, {
      orderBy:sort,
      orderDir:order
    }, null, null));
  }

  getInstrumentByIdd(idd: number): Observable<InstrumentList> {
    const href = '/api/instrument/' + idd;

    return this._httpClient.get<InstrumentList>(href);
  }
}
