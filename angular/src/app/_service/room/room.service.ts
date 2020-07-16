import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RoomList} from "../../_model/room/room-list";
import {BaseService} from "../base/base.service";
import {Room} from "../../_model/room/room";
import {RoomHistory} from "../../_model/room/room-history";

@Injectable({
  providedIn: 'root'
})
export class RoomService extends BaseService<RoomList, RoomHistory, Room> {
  private httpClient: HttpClient;

  constructor(_httpClient: HttpClient) {
    super(_httpClient, new Room());
    this.httpClient = _httpClient;
  }
}
