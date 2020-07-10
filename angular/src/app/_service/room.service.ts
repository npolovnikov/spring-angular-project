import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RoomList} from "../_model/roomList";
import {BaseService} from "./base.service";

@Injectable({
  providedIn: 'root'
})
export class RoomService extends BaseService<RoomList>{
  constructor(_httpClient: HttpClient) {
    super(_httpClient, new RoomList());
  }
}
