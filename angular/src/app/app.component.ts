import {Component, Inject} from '@angular/core';
import {AuthService} from './_service/auth.service';
import {first} from 'rxjs/operators';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  isLoginPage: boolean;
  title = 'angular';

  constructor(
    private authService: AuthService,
    private router: Router) {
    this.isLoginPage = !document.cookie;
  }

  logout() {
    this.authService.logout().pipe(first())
      .subscribe(() => {
          this.router.navigateByUrl('/login');
          this.isLoginPage = true;
        }
      );
  }

}
