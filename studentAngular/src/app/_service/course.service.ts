import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";
import {OneRoom} from "../_model/one-room";
import {OneCourse} from "../_model/one-course";

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  constructor(private _httpClient: HttpClient) {
  }

  getCourseList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = 'api/course/list';
    return this._httpClient.post<Page>(href, new PageParams(page * pageSize, pageSize, {
      orderBy:sort,
      orderDir:order
    }));
  }

  getCourseByIdd(idd: number): Observable<OneCourse> {
    const href = '/api/course/' + idd;

    return this._httpClient.get<OneCourse>(href)
  }

  updateCourse(idd: number, data: OneCourse): Observable<Object> {
    const href = '/api/course/' + idd;
    return this._httpClient.patch(href, data);
  }

  createCourse(data: OneCourse): Observable<Object> {
    const href = '/api/course';
    return this._httpClient.post(href, data);
  }

  deleteCourse(idd: number){
    const href = '/api/course/' + idd;
    return this._httpClient.delete(href).subscribe(data => {
      console.log(data);
    } );
  }
}
