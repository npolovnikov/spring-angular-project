import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";

@Injectable({
  providedIn: 'root'
})
export class RoomService {
  constructor(private _httpClient: HttpClient) {
  }

  getRoomList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    /* путь к запросу api/room/list */
    /* данные берутся из данного маппинга контроллера бэк-енда */
    const href = 'api/room/list';
    const params : PageParams = new PageParams(page*pageSize, pageSize, {
      /* получаем параметры сортировки */
      orderBy:sort,
      orderDir:order
    })

    /* наш пост запрос листа */
    return this._httpClient.post<Page>(href, params);
  }
}
