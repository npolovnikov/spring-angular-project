import { Injectable } from '@angular/core';
import {TeacherList} from "../_model/teacherList";
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";

@Injectable({
  providedIn: 'root'
})
export class TeacherService extends BaseService<TeacherList>{
  constructor(_httpClient: HttpClient) {
    super(_httpClient, new TeacherList());
  }
}
