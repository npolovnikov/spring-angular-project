import { Injectable } from '@angular/core';
import {StudentList} from "../_model/studentList";
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {StudentHistory} from "../_model/student-history";
import {Student} from "../_model/student";

@Injectable({
  providedIn: 'root'
})
export class StudentService extends BaseService<StudentList, StudentHistory, Student>{
  constructor(_httpClient: HttpClient) {
    super(_httpClient, new Student);
  }
}
