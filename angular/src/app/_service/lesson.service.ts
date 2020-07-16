import { Injectable } from '@angular/core';
import {BaseService} from "./base.service";
import {LessonList} from "../_model/lessonList";
import {HttpClient} from "@angular/common/http";
import {LessonHistory} from "../_model/lesson-history";
import {Lesson} from "../_model/lesson";

@Injectable({
  providedIn: 'root'
})
export class LessonService extends BaseService<LessonList, LessonHistory, Lesson>{
  constructor(_httpClient: HttpClient) {
    super(_httpClient, new Lesson());
  }
}
