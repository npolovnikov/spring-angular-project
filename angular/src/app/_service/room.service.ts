import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RoomList} from "../_model/roomList";
import {BaseService} from "./base.service";
import {Observable} from "rxjs";
import {Room} from "../_model/room";

@Injectable({
  providedIn: 'root'
})
export class RoomService extends BaseService<RoomList>{
  private httpClient: HttpClient;

  constructor(_httpClient: HttpClient) {
    super(_httpClient, new RoomList());
    this.httpClient = _httpClient;
  }

  getRoomByIdd(idd: number): Observable<Room> {
    const href = '/api/room/' + idd;

    return this.httpClient.get<Room>(href);
  }

  updateRoom(idd: number, data: Room):Observable<Object> {
    const href = '/api/room/' + idd;
    return this.httpClient.patch(href, data);
  }

  createRoom(data: Room):Observable<Object> {
    const href = '/api/room';
    return this.httpClient.post(href, data);
  }
}
