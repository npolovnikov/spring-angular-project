import { Injectable } from '@angular/core';
import {StudentList} from "../_model/studentList";
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";

@Injectable({
  providedIn: 'root'
})
export class StudentService extends BaseService<StudentList>{
  constructor(_httpClient: HttpClient) {
    super(_httpClient, new StudentList());
  }
}
