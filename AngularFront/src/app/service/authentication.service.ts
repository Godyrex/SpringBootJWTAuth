import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {TokenStorageService} from "./token-storage.service";
import {LoginRequest} from "../model/LoginRequest";
import {RegisterRequest} from "../model/RegisterRequest";
import {LoginResponse} from "../model/LoginResponse";
import {Observable} from "rxjs";
import {JsonResponse} from "../model/JsonResponse";
@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
private baseUrl : string = 'http://localhost:8080/api/auth';
  constructor(private http: HttpClient) { }
  register(registerRequest : RegisterRequest)  {
    return this.http.post<JsonResponse>(`${this.baseUrl}/signup`,registerRequest)
  }
  login(loginRequest : LoginRequest){
    return this.http.post<LoginResponse>(`${this.baseUrl}/signing`,loginRequest);
  }
}
