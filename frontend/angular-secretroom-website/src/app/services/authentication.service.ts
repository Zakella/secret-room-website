import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {User} from "../model/user";
import {UserResponse} from "../model/user-response";


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) { }

  registration(user: User): Observable<UserResponse> {
    const url = 'http://localhost:8081/api/v1/users'; // Replace with your registration API endpoint from UserController
    return this.http.post<UserResponse>(url, user);
  }

  login(credentials: any): Observable<any> {
    // Replace with your login API endpoint
    const url = 'https://your-api-endpoint/login';
    return this.http.post(url, credentials);
  }
}
