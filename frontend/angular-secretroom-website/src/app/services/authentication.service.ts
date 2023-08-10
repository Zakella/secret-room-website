import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {User} from "../model/user";
import {UserDetails} from "../model/user-details";


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) { }

  registration(user: User): Observable<UserDetails> {
    const url = 'http://localhost:8081/api/v1/users'; // Replace with your registration API endpoint from UserController
    return this.http.post<UserDetails>(url, user);
  }

  login(user: User): Observable<UserDetails> {
    const url = 'http://localhost:8081/api/v1/users/login'; // Replace with your login API endpoint from UserController
    return this.http.post<UserDetails>(url, user);
  }
}
