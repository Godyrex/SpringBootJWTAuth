import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {TokenStorageService} from "./token-storage.service";
import {LoginRequest} from "../model/LoginRequest";
import {RegisterRequest} from "../model/RegisterRequest";
import {LoginResponse} from "../model/LoginResponse";
@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
private baseUrl : string = 'http://localhost:8080/api/auth';
  constructor(private http: HttpClient,
              private token:TokenStorageService) { }
  register(registerRequest : RegisterRequest){
    return this.http.post(`${this.baseUrl}/signup`,registerRequest)
  }
  login(loginRequest : LoginRequest){
    return this.http.post<LoginResponse>(`${this.baseUrl}/signing`,loginRequest);
  }
}
