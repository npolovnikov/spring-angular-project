import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {first} from "rxjs/operators";
import {signService} from "../_sevice/sign";

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class signInComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private _signService:signService
  ) {
    _signService.getCurrentUser()
      .pipe()
      .subscribe(res => {
        if (res) {
          router.navigateByUrl('/room');
        }
      })
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      login: ['', Validators.required],
      password: ['', Validators.required]
    })
  }

  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    if (this.loginForm.invalid){
      return;
    }

    this.loading = true;
    this._signService.login(this.f.login.value, this.f.password.value)
      .pipe(first())
      .subscribe(() => this.router.navigateByUrl('/room'), () => this.loading = false);
  }

}
