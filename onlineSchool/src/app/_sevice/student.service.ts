import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {student} from "../_model/student";

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(private _httpClient: HttpClient) {}

  getStudentList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = '/api/student/list';
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

  getStudentByIdd(idd: number): Observable<student> {
    const href = '/api/student/' + idd;

    return this._httpClient.get<student>(href);
  }

  createStudent(data: student): Observable<Object> {
    const href = '/api/student';
    return this._httpClient.post(href, data);
  }

  updateStudent(idd: number, data: student) {
    const href = '/api/student/' + idd;
    return this._httpClient.patch(href, data);
  }
}

