import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";
import {Room} from "../_model/room";
import {Instrument} from "../_model/instrument";

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

  getInstrumentByIdd(idd: number): Observable<Instrument> {
    const href = 'api/instrument/' + idd;

    /* наш пост запрос на получение instrument дто */
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

  /* удаление объекта */
  deleteObjectByIdd(deleteIdd: number) {
    const href = '/api/instrument/' + deleteIdd;
    return this._httpClient.delete(href).subscribe(data => {
      console.log(data);
    });
  }
}
