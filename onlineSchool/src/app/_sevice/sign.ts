import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class signService {

  constructor(private _httpClient:HttpClient) { }

  public getCurrentUser() {
    return this._httpClient.get('/api/sign/current')
  }

  public login(login: String, password:String):Observable<Object>{
    return this._httpClient.post('/api/sign/login', {login:login, password:password})
  }

  //todo ДЗ logout
}
