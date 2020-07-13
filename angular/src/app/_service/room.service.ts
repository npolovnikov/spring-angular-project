import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RoomList} from "../_model/roomList";
import {BaseService} from "./base.service";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";
import {Room} from "../_model/room";

@Injectable({
  providedIn: 'root'
})
export class RoomService extends BaseService<RoomList>{
  constructor(_httpClient: HttpClient) {
    super(_httpClient, new RoomList());
  }

  getRoomByIdd(idd: number): Observable<Room> {
    const href = '/api/room/' + idd;

    return this._httpClient.get<Room>(href);
  }

  updateRoom(idd: number, data: Room):Observable<Object> {
    const href = '/api/room/' + idd;
    return this._httpClient.patch(href, data);
  }

  createRoom(data: Room):Observable<Object> {
    const href = '/api/room';
    return this._httpClient.post(href, data);
  }
}
