import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";
import {BaseListEntity} from "../_model/base-list-entity";

@Injectable({
  providedIn: 'root'
})
export class BaseService <ListEntity extends BaseListEntity> {


  constructor(private _httpClient: HttpClient, private _listEntity: ListEntity) {}

  getObjectList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = `/api/${this._listEntity.getName()}/list`;
    return this._httpClient.post<Page>(href, new PageParams(page*pageSize, pageSize, {
      orderBy:sort,
      orderDir:order
    }));
  }
}
