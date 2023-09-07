import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ResponsePurchase} from "../model/response-purchase";
import {Purchase} from "../model/purchase";
import {OrderReview} from "../model/order-review";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {

  private url = environment.apiUrl +'v1/order';

  constructor(private http: HttpClient) {}

  placeOrder (purchase: Purchase): Observable<ResponsePurchase> {
    return this.http.post<ResponsePurchase>(this.url, purchase);
  }

  getOrderDetails(orderUuid: String): Observable<OrderReview> {
    return this.http.get<OrderReview>(`${this.url}/${orderUuid}`);
  }

}
