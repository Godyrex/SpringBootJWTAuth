import { Component } from '@angular/core';
import {TokenStorageService} from "../service/token-storage.service";
import {LoginResponse} from "../model/LoginResponse";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  constructor(
    private tokenService : TokenStorageService
  ) {
  }
  user : LoginResponse = this.tokenService.getUser();
}
