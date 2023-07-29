import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Order} from '../model/order';
import {catchError} from "rxjs/operators";
import {ResponseOrder} from "../model/response-order";


@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private orderUrl = 'http://localhost:8081/api/v1/purchase';

  constructor(private http: HttpClient) {
  }

  getOrderDetails(orderUuid: String): Observable<Order> {
    return this.http.post<Order>(this.orderUrl, orderUuid);
  }


}
