import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";

@Injectable({
  providedIn: 'root'
})
export class InstrumentService {
  constructor(private _httpClient: HttpClient) {
  }

  getInstrumentList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = 'api/instrument/list';
    const params : PageParams = new PageParams(page*pageSize, pageSize, {
      /* получаем параметры сортировки */
      orderBy:sort,
      orderDir:order
    })

    /* наш пост запрос листа */
    return this._httpClient.post<Page>(href, params);
  }
}
