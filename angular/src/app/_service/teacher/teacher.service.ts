import { Injectable } from '@angular/core';
import {TeacherList} from "../../_model/teacher/teacherList";
import {HttpClient} from "@angular/common/http";
import {BaseService} from "../base/base.service";
import {TeacherHistory} from "../../_model/teacher/teacher-history";
import {Teacher} from "../../_model/teacher/teacher";

@Injectable({
  providedIn: 'root'
})
export class TeacherService extends BaseService<TeacherList, TeacherHistory, Teacher>{
  constructor(_httpClient: HttpClient) {
    super(_httpClient, new Teacher);
  }
}
