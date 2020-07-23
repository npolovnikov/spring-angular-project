import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class TeacherService {
  constructor(private _httpClient: HttpClient) {}

  getTeacherList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = '/api/teacher/list';

    return this._httpClient.post<Page>(href, new PageParams(page*pageSize, pageSize, {
      orderBy:sort,
      orderDir:order
    }));
  }
  deleteTeacher(idd: number) {
    const href = '/api/teacher/' + idd;
    return this._httpClient.delete(href).subscribe(data => {
      console.log(data);
    });
  }
}
