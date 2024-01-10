import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ProfileRequest} from "../model/ProfileRequest";
import {LoginResponse} from "../model/LoginResponse";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl : string = 'http://localhost:8080/api/user';
  constructor(private http: HttpClient,) { }
  update(profileRequest : ProfileRequest){
    return this.http.patch<LoginResponse>(`${this.baseUrl}/update`,profileRequest)
  }
}
