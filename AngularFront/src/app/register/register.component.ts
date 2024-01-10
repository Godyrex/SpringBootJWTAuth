import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../service/authentication.service";
import {RegisterRequest} from "../model/RegisterRequest";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent {
  registerRequest : RegisterRequest = {};
  message = '';
  constructor(
    private authService : AuthenticationService,
    private router : Router
  ) {
  }
  registerUser(){
    this.message = '';
    this.authService.register(this.registerRequest)
      .subscribe(data => {
          console.log(data)
          this.message ='Account created successfully\\nPlease verify your email';
        },
        error => {
          console.log(error)
          this.message =error.error.message;
        });

  }
}
