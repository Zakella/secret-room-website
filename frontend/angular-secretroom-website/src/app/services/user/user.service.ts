import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {UserAccountInfo} from "../../model/user-account-info";


@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly API_URL = 'http://localhost:8081/api/v1/users/accountInfo';

  constructor(private http: HttpClient) {
  }

  getCustomerOrders(userEmail: string): Observable<UserAccountInfo> {
    return this.http.post<UserAccountInfo>(this.API_URL, {email: userEmail});
  }


}
