import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  static getObjectList(httpClient: HttpClient, href: string, sort: string, order: string, page: number, pageSize: number): Observable<Page> {

    return httpClient.post<Page>(href, new PageParams(page*pageSize, pageSize, {
      orderBy:sort,
      orderDir:order
    }));
  }
}
