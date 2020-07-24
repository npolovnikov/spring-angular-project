import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {User} from "../_model/user";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private _httpClient: HttpClient) {}

  getUserList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = '/api/users/list';
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

  getUserByIdd(idd: number): Observable<User> {
    const href = '/api/users/' + idd;

    return this._httpClient.get<User>(href);
  }

  updateUser(idd: number, data: User): Observable<Object> {
    const href = '/api/users/' + idd;
    return this._httpClient.patch(href, data);
  }

  createUser(data: User): Observable<Object> {
    const href = '/api/users';
    return this._httpClient.post(href, data);
  }
}

