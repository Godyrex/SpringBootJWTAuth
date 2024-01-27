import { Component } from '@angular/core';
import {ProfileRequest} from "../model/ProfileRequest";
import {UserService} from "../service/user.service";
import {TokenStorageService} from "../service/token-storage.service";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  profileRequest : ProfileRequest = {};
  profileForm = this.formBuilder.group({
    password : ['',[
      Validators.maxLength(50),
      Validators.minLength(8)
    ]
    ],
    email : ['',
      [
      Validators.email
    ]],
    name : ['',
      [
        Validators.maxLength(20)
    ]],
    lastname : ['',
      [
        Validators.maxLength(20)
      ]],

  })
  message = '';
  constructor(
    private userService : UserService,
    private token: TokenStorageService,
    private formBuilder : FormBuilder
  ) {
  }
  update() {
    this.message = '';
    if (this.profileForm.valid) {
      this.profileRequest.id = this.token.getUser().id;
      console.log(this.profileRequest);
      this.userService.update(this.profileRequest)
        .subscribe(data => {
            this.token.saveUser(data);
            this.message = 'Account updated successfully';
          },
          error => {
            console.log(error)
            this.message = error.error.message;
          });
      console.log(this.profileRequest);

    }
  }
}
