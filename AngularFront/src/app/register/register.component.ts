import { Component } from '@angular/core';
import {AuthenticationService} from "../service/authentication.service";
import {RegisterRequest} from "../model/RegisterRequest";
import {FormBuilder, Validators} from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent {
  registerRequest : RegisterRequest = {};
  message = '';
  messagesuccess = '';
  registerForm = this.formBuilder.group({
    username: ['',
      [
        Validators.required,
        Validators.maxLength(20),
        Validators.minLength(3)
      ]
    ],
    email: ['',
      [
        Validators.required,
        Validators.email
      ]],
    password: ['',
      [
        Validators.required,
        Validators.maxLength(50),
        Validators.minLength(8)
      ]],
  });
  constructor(
    private authService : AuthenticationService,
    private formBuilder : FormBuilder
  ) {
  }
  registerUser(){
    if(this.registerForm.valid) {
      this.registerRequest = Object.assign(this.registerRequest,this.registerForm.value);
      console.log(this.registerRequest);
      this.authService.register(this.registerRequest)
        .subscribe(data => {
            console.log(data)
            this.messagesuccess = data.msg!;
          },
          error => {
            console.log("register error :",error)
            this.message = error.message;
          });
    }

  }
}
