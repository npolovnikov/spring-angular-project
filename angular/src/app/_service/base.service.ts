import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";
import {BaseListEntity} from "../_model/base-list-entity";
import {BaseEntity} from "../_model/base-entity";
import {BaseHistoryEntity} from "../_model/base-history-entity";

@Injectable({
  providedIn: 'root'
})
export class BaseService <ListEntity extends BaseListEntity, HistoryEntity extends BaseHistoryEntity, Entity extends BaseEntity<HistoryEntity>> {


  constructor(private _httpClient: HttpClient, private _entity: Entity) {}

  getObjectList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = `/api/${this._entity.getName()}/list`;
    return this._httpClient.post<Page>(href, new PageParams(page*pageSize, pageSize, {
      orderBy:sort,
      orderDir:order
    }));
  }

  getByIdd(idd: number): Observable<Entity> {
    const href = '/api/' + this._entity.getName() + '/' + idd;

    return this._httpClient.get<Entity>(href);
  }

  update(idd: number, data: Entity):Observable<Object> {
    const href = '/api/' + this._entity.getName() + '/' + idd;
    return this._httpClient.patch(href, data);
  }

  createRoom(data: Entity):Observable<Object> {
    const href = '/api/' + this._entity.getName();
    return this._httpClient.post(href, data);
  }
}
