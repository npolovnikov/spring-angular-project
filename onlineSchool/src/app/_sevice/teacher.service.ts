import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";

@Injectable({
  providedIn: 'root'
})
export class TeacherService {

  constructor(private _httpClient: HttpClient) {}

  getTeacherList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = '/api/teacher/list';
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
}
