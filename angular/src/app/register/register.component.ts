import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../_service/auth.service";
import {first} from "rxjs/operators";
import {RegisterService} from "../_service/register.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  reigsterForm: FormGroup;
  loading = false;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private _registerService: RegisterService
  ) {

  }

  ngOnInit(): void {

  }

  get f() {
    return this.reigsterForm.controls;
  }

  onSubmit() {
    this.reigsterForm = this.formBuilder.group({
      login: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.submitted = true;

    if (this.reigsterForm.invalid){
      return;
    }

    this.loading = true;
    this._registerService.register(this.f.firstName.value, this.f.midlleName.value, this.f.lastName.value, this.f.status.value, this.f.courseId.value, this.f.login.value, this.f.password.value)
      .pipe(first())
      .subscribe(() => this.router.navigateByUrl('/home'), () => this.loading = false);
  }

}
