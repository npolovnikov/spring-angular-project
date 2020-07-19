import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Page} from '../_model/page';
import {PageParams} from '../_model/page-params';
import {Course} from '../_model/course';
import {User} from '../_model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) {
  }

  getUsersList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = '/api/user/list';

    return this.httpClient.post<Page>(href, new PageParams(page * pageSize, pageSize, {
      orderBy: sort,
      orderDir: order
    }, sort, order));
  }

  createUser(data: User): Observable<object> {
    const href = '/api/user';
    return this.httpClient.post(href, data);
  }

  deleteUser(id: number): Observable<object> {
    const href = '/api/user/' + id;
    return this.httpClient.delete(href);
  }
}
