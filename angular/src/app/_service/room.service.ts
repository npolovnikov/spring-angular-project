import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";
import {Room} from "../_model/room";

@Injectable({
  providedIn: 'root'
})

export class RoomService {
  constructor(private _httpClient: HttpClient) {
  }

  /* возвращает  Page данных из пост запроса 'api/room/list'*/
  getRoomList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    /* путь к запросу api/room/list */
    /* данные берутся из данного маппинга контроллера бэк-енда */
    const href = 'api/room/list';
    const params: PageParams = new PageParams(page * pageSize, pageSize, {
      /* получаем параметры сортировки */
      orderBy: sort,
      orderDir: order
    })

    /* наш пост запрос листа */
    return this._httpClient.post<Page>(href, params);
  }

  getRoomByIdd(idd: number): Observable<Room> {
    const href = 'api/room/' + idd;

    /* наш пост запрос на получение рум дто */
    return this._httpClient.get<Room>(href);

  }

  updateRoom(idd: number, data: Room): Observable<Object> {
    const href = '/api/room/' + idd;
    return this._httpClient.patch(href, data);
  }

  createRoom(data: Room): Observable<Object> {
    const href = '/api/room';
    return this._httpClient.post(href, data);
  }

  /* удаление объекта */
  deleteObjectByIdd(deleteIdd: number) {
    const href = '/api/room/' + deleteIdd;
    return this._httpClient.delete(href).subscribe(data => {
      console.log(data);
    });
  }
}
