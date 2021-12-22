import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthRequest } from '../model/auth-request';
import { AuthResponse } from '../model/auth-response';
import { StorageService } from './storage-service.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private storageService: StorageService) { }

  login(authRequest: AuthRequest): Observable<AuthResponse> {
    console.log("Auth request.")
    return this.http.post<AuthResponse>(environment.API_URL + "/auth/login", authRequest);
  }

  updateToken(token: string, username: string) {
    this.storageService.set("csrf_token", token);
    this.storageService.set("user_name", username);
    return true;
  }

  isLoggedIn() {
    let token = this.storageService.get('csrf_token');
    if (token === undefined || token == "" || token === null) {
      return false;
    }
    return true;
  }

  logout() {
    this.removeToken();
    return true;
  }

  removeToken() {
    this.storageService.remove("csrf_token");
    this.storageService.remove("user_name");
    return true;
  }
}
