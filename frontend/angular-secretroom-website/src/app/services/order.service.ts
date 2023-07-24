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

  private orderUrl = 'http://localhost:8081/api/v1/orders';

  constructor(private http: HttpClient) {
  }

  sendOrder(order: Order): Observable<ResponseOrder> {
    return this.http.post<ResponseOrder>(this.orderUrl, order);
  }


}
