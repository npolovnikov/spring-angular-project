import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Page} from '../_model/page';
import {PageParams} from '../_model/page-params';

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
}
