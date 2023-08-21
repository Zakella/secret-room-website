import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable, BehaviorSubject, tap} from 'rxjs';
import {User} from "../model/user";
import {UserDetails} from "../model/user-details";
import {AccessGuardService} from "../authentication/access-guard.service";
import {JwtHelperService} from "@auth0/angular-jwt";


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private loggedIn = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient) {
    this.userIsLoggedIn();
  }

  userIsLoggedIn(): void {
    const storedUser = localStorage.getItem("user");
    if (storedUser && storedUser !== "null") {
      const userDetails: UserDetails = JSON.parse(storedUser);
      const token = userDetails.accessToken;
      if (token) {
        const jwtHelper:JwtHelperService = new JwtHelperService();
        const isTokenNonExpired = !jwtHelper.isTokenExpired(token);
        this.loggedIn.next(isTokenNonExpired);
      }
    }
  }

  isLoggedIn(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  registration(user: User): Observable<UserDetails> {
    const url = 'http://localhost:8081/api/v1/users'; // Replace with your registration API endpoint from UserController
    return this.http.post<UserDetails>(url, user).pipe(
      tap((userDetails: UserDetails) => {
        // Login the user after successful registration
        this.loggedIn.next(true);
      })
    );
  }

  login(user: User): Observable<UserDetails> {
    const url = 'http://localhost:8081/api/v1/users/login'; // Replace with your login API endpoint from UserController
    return this.http.post<UserDetails>(url, user).pipe(
      tap(() => {
        this.loggedIn.next(true);
      })
    );
  }

  logout(): void {
    const storedUser = localStorage.getItem("user");
    if (storedUser && storedUser !== "null") {
      const userDetails: UserDetails = JSON.parse(storedUser);
      const token = userDetails.accessToken;
      if (token) {
        this.http.get('http://localhost:8081/api/v1/users/logout', {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }).subscribe(() => {
          localStorage.removeItem('user');
          this.loggedIn.next(false);
        });
      }
    }
  }

  getUserDetails(): UserDetails | null {
    const storedUser = localStorage.getItem("user");
    if (storedUser && storedUser !== "null") {
      return JSON.parse(storedUser);
    }
    return null;
  }
}
