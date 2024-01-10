import { Component } from '@angular/core';
import {ProfileRequest} from "../model/ProfileRequest";
import {UserService} from "../service/user.service";
import {TokenStorageService} from "../service/token-storage.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  profileRequest : ProfileRequest = {};
  message = '';
  constructor(
    private userService : UserService,
    private token: TokenStorageService
  ) {
  }
  update(){
    this.message = '';
    this.profileRequest.id = this.token.getUser().id;
    console.log(this.profileRequest);
    this.userService.update(this.profileRequest)
      .subscribe(data => {
        this.token.saveUser(data);
        this.message ='Account updated successfully';
        },
        error => {
          console.log(error)
          this.message =error.error.message;
        });
    console.log(this.profileRequest);

  }
}
