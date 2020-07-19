import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Page} from '../_model/page';
import {PageParams} from '../_model/page-params';
import {Student} from '../_model/student';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(private httpClient: HttpClient) {
  }

  getStudentList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = '/api/student/list';

    return this.httpClient.post<Page>(href, new PageParams(page * pageSize, pageSize, {
      orderBy: sort,
      orderDir: order
    }, sort, order));
  }

  getStudentByIdd(idd: number): Observable<Student> {
    const href = '/api/student/' + idd;
    return this.httpClient.get<Student>(href);
  }

  updateStudent(idd: number, data: Student): Observable<object> {
    const href = '/api/student/' + idd;
    return this.httpClient.patch(href, data);
  }

  createStudent(data: Student): Observable<object> {
    const href = '/api/student';
    return this.httpClient.post(href, data);
  }
}
