import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Page} from '../_model/page';
import {PageParams} from '../_model/page-params';
import {Course} from '../_model/course';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  constructor(private httpClient: HttpClient) {
  }

  getCourseList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = '/api/course/list';

    return this.httpClient.post<Page>(href, new PageParams(page * pageSize, pageSize, {
      orderBy: sort,
      orderDir: order
    }, null, null));
  }

  getList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    return this.getCourseList(sort, order, page, pageSize);
  }

  getCourseByIdd(idd: number): Observable<Course> {
    const href = '/api/course/' + idd;
    return this.httpClient.get<Course>(href);
  }

  updateCourse(idd: number, data: Course): Observable<object> {
    const href = '/api/course/' + idd;
    return this.httpClient.patch(href, data);
  }

  createCourse(data: Course): Observable<object> {
    const href = '/api/course';
    return this.httpClient.post(href, data);
  }

  delete(id: number): Observable<object>  {
    const href = '/api/course/' + id;
    return this.httpClient.delete(href);
  }
}
