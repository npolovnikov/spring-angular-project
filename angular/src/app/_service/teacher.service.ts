import { Injectable } from '@angular/core';
import {TeacherList} from "../_model/teacherList";
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {TeacherHistory} from "../_model/teacher-history";
import {Teacher} from "../_model/teacher";

@Injectable({
  providedIn: 'root'
})
export class TeacherService extends BaseService<TeacherList, TeacherHistory, Teacher>{
  constructor(_httpClient: HttpClient) {
    super(_httpClient, new Teacher);
  }
}
