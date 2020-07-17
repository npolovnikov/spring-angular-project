import { Component, OnInit } from '@angular/core';
import {AuthService} from "../_service/auth/auth.service";
import {Router} from "@angular/router";
import {User} from "../_model/user/user";

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit {

  user:User;

  constructor(private _authService:AuthService, private router: Router) {}

  ngOnInit(): void {
    this._authService.getCurrentUser()
      .pipe()
      .subscribe(res => {
        this.user = res;
      })
  }

  onClick(): void {
    this._authService.logout()
      .pipe()
      .subscribe(res => {
          this.router.navigateByUrl('/login');
    });
  }

}
