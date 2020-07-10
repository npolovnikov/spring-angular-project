import { Injectable } from '@angular/core';
import {BaseService} from "./base.service";
import {LessonList} from "../_model/lessonList";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LessonService extends BaseService<LessonList>{
  constructor(_httpClient: HttpClient) {
    super(_httpClient, new LessonList());
  }
}
