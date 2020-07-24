import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable, of, throwError} from "rxjs";
import {Router} from "@angular/router";
import {catchError} from "rxjs/operators";
import {MatDialog} from "@angular/material/dialog";
import {ErrorDialogComponent} from "../error-dialog/error-dialog.component";

@Injectable()
/* handler, перенаправляет на логин c любого роутинга, если не залогинен*/
export class AuthInterceptor implements HttpInterceptor{

  constructor(private router: Router, private dialog:MatDialog){}

  /* перехватили ошибку, перенаправили на handleAuthError */
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError(x => this.handleAuthError(x)));
  }

  private handleAuthError(err: HttpErrorResponse): Observable<any>{
    if (err.status === 401 || err.status === 403) {
      this.router.navigateByUrl('/login');
      return of(err.message);
      /* если перехватываем 500 - открываем error dialog */
    } else if (err.status === 500) {
      this.dialog.open(ErrorDialogComponent, {
        width: '750px',
        /* MAT_DIALOG_DATA - объект данных, поля которого мы будем исп-ть во view*/
        data: {title:'Ошибка', message:err.error}
      });
    }
    return throwError(err);
  }

}
