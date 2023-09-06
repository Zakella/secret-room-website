import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable, BehaviorSubject, Subscriber} from 'rxjs';
import { tap } from 'rxjs/operators';
import { User } from "../model/user";
import { UserDetails } from "../model/user-details";
import { JwtHelperService } from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private jwtHelper = new JwtHelperService();
  loggedIn = new BehaviorSubject<boolean>(this.hasValidTokenInLocalStorage());

  constructor(private http: HttpClient) { }

  isLoggedIn(): Observable<boolean> {
    return new Observable<boolean>(subscriber => {
      if (this.hasValidTokenInLocalStorage()) {
        this.notifyLoggedInStatus(true, subscriber);
        console.log("Its ok!" + new Date());
      } else {
        console.log("Auto logout" + new Date());
        this.notifyLoggedInStatus(false, subscriber);
        localStorage.removeItem('user');
      }
      subscriber.complete();
    });
  }

  private hasValidTokenInLocalStorage(): boolean {
    // const storedUser = localStorage.getItem("user");
    // if (storedUser && storedUser !== "null") {
    //   const userDetails: UserDetails = JSON.parse(storedUser);
    //   const token = userDetails.accessToken;
    //   return Boolean(token) && !this.jwtHelper.isTokenExpired(token);
    // }
    return false;
  }

  private notifyLoggedInStatus(isLoggedIn: boolean, subscriber: Subscriber<boolean>) {
    this.loggedIn.next(isLoggedIn);
    subscriber.next(isLoggedIn);
  }

  registration(user: User): Observable<UserDetails> {
    const url = 'http://localhost:8081/api/v1/users';
    return this.http.post<UserDetails>(url, user).pipe(
      tap((userDetails: UserDetails) => {
        this.loggedIn.next(true);
      })
    );
  }

  login(user: User): Observable<UserDetails> {
    const url = 'http://localhost:8081/api/v1/users/login';
    return this.http.post<UserDetails>(url, user).pipe(
      tap(() => {
        this.loggedIn.next(true);
      })
    );
  }

  logout(): void {
    // const userDetails = this.getUserDetails();
    // if (userDetails) {
    //   const token = userDetails.accessToken;
    //   const refreshToken = userDetails.refreshToken;
    //   if (token && refreshToken) {
    //     this.http.post('http://localhost:8081/api/v1/users/logout', {
    //       refresh_token: refreshToken
    //     }, {
    //       headers: {
    //         Authorization: `Bearer ${token}`
    //       }
    //     }).subscribe(() => {
    //       localStorage.removeItem('user');
    //       this.loggedIn.next(false);
    //     });
    //   }
    // }
  }

  getUserDetails(): UserDetails | null {
    const storedUser = localStorage.getItem("user");
    if (storedUser && storedUser !== "null") {
      return JSON.parse(storedUser);
    }
    return null;
  }

  restorePassword(email: string, lang: string): Observable<any> {
    const url = 'http://localhost:8081/api/v1/users/restore-password'; // Replace with your restore password API endpoint from UserController
    return this.http.get(url, { params: { email, lang } });
  }

  resetPassword(token: string, newPassword: string): Observable<void> {
    const url = 'http://localhost:8081/api/v1/users/reset-password'; // Replace with your reset password API endpoint from UserController
    return this.http.post<void>(url, { token, newPassword });
  }

}
