import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";
import {Room} from "../_model/room";

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  constructor(private _httpClient: HttpClient) {}

  getRoomList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = '/api/room/list';
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

  getRoomByIdd(idd: number): Observable<Room> {
      const href = '/api/room/' + idd;

      return this._httpClient.get<Room>(href);
  }

  updateRoom(idd: number, data: Room): Observable<Object> {
    const href = '/api/room/' + idd;
    return this._httpClient.patch(href, data);
  }

  createRoom(data: Room): Observable<Object> {
    const href = '/api/room';
    return this._httpClient.post(href, data);
  }
}

