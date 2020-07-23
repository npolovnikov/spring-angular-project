import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";
import {Course} from "../_model/course";
import {InstrumentList} from "../_model/instrument-list";
import {CourseList} from "../_model/course-list";

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  constructor(private _httpClient: HttpClient) {}

  getCourseList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = '/api/course/list';

    return this._httpClient.post<Page>(href, new PageParams(page*pageSize, pageSize, {
      orderBy:sort,
      orderDir:order
    }));
  }

  getCourseByIdd(idd: number): Observable<CourseList> {
    const href = '/api/course/' + idd;

    return this._httpClient.get<CourseList>(href);
  }
  deleteCourse(idd: number) {
    const href = '/api/course/' + idd;
    return this._httpClient.delete(href).subscribe(data => {
      console.log(data);
    });
  }


}
