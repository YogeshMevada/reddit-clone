import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SignupRequest } from '../signup/signup-request.payload';
import { Http } from '@angular/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  constructor(private httpClient: HttpClient) { }

  signup(signupRequest: SignupRequest) : Observable<any> {
   return this.httpClient.post('http://loclhost:8080/reddit-clone/api/v1/auth/register', signupRequest, {responseType: 'text'}); 
  }
}
