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
  activeTab = 0;

  constructor(
    private authService: AuthService,
    private router: Router) {
    this.isLoginPage = !document.cookie;
    switch (location.pathname) {
      case '/student': this.activeTab = 1; break;
      case '/course': this.activeTab = 2; break;
      case '/room': this.activeTab = 3; break;
      case '/instrument': this.activeTab = 4; break;
      case '/users': this.activeTab = 5; break;
    }
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
