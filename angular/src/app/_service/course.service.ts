import { Injectable } from '@angular/core';
import {BaseService} from "./base.service";
import {HttpClient} from "@angular/common/http";
import {CourseList} from "../_model/courseList";

@Injectable({
  providedIn: 'root'
})
export class CourseService extends BaseService<CourseList>{
  constructor(_httpClient: HttpClient) {
    super(_httpClient, new CourseList());
  }
}
