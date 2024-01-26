import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from "../service/token-storage.service";
import {AuthenticationService} from "../service/authentication.service";
import {Router} from "@angular/router";
import {LoginResponse} from "../model/LoginResponse";
import {LoginRequest} from "../model/LoginRequest";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginResponse : LoginResponse = {};
  loginRequest : LoginRequest = {};
  loginForm = this.formBuilder.group({
    username: ['',
      [Validators.required]],
    password: ['',
      [Validators.required]]
  });
  message = '';
  constructor(
    private authService : AuthenticationService,
    private router : Router,
    private token: TokenStorageService,
    private formBuilder : FormBuilder
  ) {
  }


  login() {
    if (this.loginForm.valid ) {
      this.loginRequest = Object.assign(this.loginRequest, this.loginForm.value);
        this.authService.login(this.loginRequest).subscribe(
          response => {
            this.loginResponse = response;
            this.token.saveToken(this.loginResponse.token as string);
            this.token.saveUser(response);
            this.router.navigate(['dashboard']);
          },
          error => {
            console.log(error)
            this.message = "wrong username or password";
          });

  }

  }
}
