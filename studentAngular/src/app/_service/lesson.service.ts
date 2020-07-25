import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";
import {OneStudent} from "../_model/one-student";
import {OneLesson} from "../_model/one-lesson";

@Injectable({
  providedIn: 'root'
})
export class LessonService {

  constructor(private _httpClient: HttpClient) {
  }

  getLessonList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = 'api/lesson/list';
    return this._httpClient.post<Page>(href, new PageParams(page * pageSize, pageSize, {
      orderBy:sort,
      orderDir:order
    }));
  }

  getLessonByIdd(id: number): Observable<OneLesson> {
    const href = '/api/lesson/' + id;

    return this._httpClient.get<OneLesson>(href)
  }

  updateLesson(idd: number, data: OneLesson): Observable<Object> {
    const href = '/api/lesson/' + idd;
    return this._httpClient.patch(href, data);
  }

  createLesson(data: OneLesson): Observable<Object> {
    const href = '/api/lesson';
    return this._httpClient.post(href, data);
  }

  deleteLesson(idd: number){
    const href = '/api/lesson/' + idd;
    return this._httpClient.delete(href).subscribe(data => {
      console.log(data);
    } );
  }
}
