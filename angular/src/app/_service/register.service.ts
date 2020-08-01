import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../_model/user";

export class RegisterService {

  constructor(private _httpClient: HttpClient) {
  }

  public register(firstName: string, middleName: string, lastName: string, status: string, courseId: number, login: string, password: string): Observable<Object> {
    return this._httpClient.post('/api/auth/register', {firstName: firstName,
      middleNAme: middleName,
      lastName: lastName,
      status: status,
      courseId: courseId,
      login: login,
      password: password});
  }
}
