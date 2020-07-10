import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {InstrumentList} from "../_model/instrumentList";

@Injectable({
  providedIn: 'root'
})
export class InstrumentService extends BaseService<InstrumentList>{
  constructor(_httpClient: HttpClient) {
    super(_httpClient, new InstrumentList());
  }
}
