import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";
import {RoomList} from "../_model/room-list";

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  constructor(private _httpClient: HttpClient) {}

  getRoomList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = '/api/room/list';

    return this._httpClient.post<Page>(href, new PageParams(page*pageSize, pageSize, {
      orderBy:sort,
      orderDir:order
    }, null, null));
  }

  getRoomByIdd(idd: number): Observable<RoomList> {
    const href = '/api/room/' + idd;

    return this._httpClient.get<RoomList>(href);
  }

  updateRoom(idd: number, data: RoomList):Observable<Object> {
    const href = '/api/room/' + idd;
    return this._httpClient.patch(href, data);
  }

  createRoom(data: RoomList):Observable<Object> {
    const href = '/api/room';
    return this._httpClient.post(href, data);
  }
}
