import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {CommonService} from "./common.service";

@Injectable({
  providedIn: 'root'
})
export class InstrumentService {

  constructor(private _httpClient: HttpClient) {}

  getInstrumentList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = '/api/instrument/list';

    return CommonService.getObjectList(this._httpClient, href, sort, order, page, pageSize);
  }
}
