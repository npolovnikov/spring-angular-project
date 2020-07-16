import { Injectable } from '@angular/core';
import {BaseService} from "./base.service";
import {HttpClient} from "@angular/common/http";
import {CourseList} from "../_model/courseList";
import {CourseHistory} from "../_model/course-history";
import {Course} from "../_model/course";

@Injectable({
  providedIn: 'root'
})
export class CourseService extends BaseService<CourseList, CourseHistory, Course>{
  constructor(_httpClient: HttpClient) {
    super(_httpClient, new Course);
  }
}
