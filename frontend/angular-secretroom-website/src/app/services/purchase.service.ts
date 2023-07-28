import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Order} from "../model/order";
import {Observable} from "rxjs";
import {ResponseOrder} from "../model/response-order";
import {ResponsePurchase} from "../model/response-purchase";
import {Purchase} from "../model/purchase";

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {

  private checkoutUrl = 'http://localhost:8081/api/v1/checkout';

  constructor(private http: HttpClient) {}

  placeOrder (purchase: Purchase): Observable<ResponsePurchase> {
    return this.http.post<ResponsePurchase>(this.checkoutUrl, purchase);
  }
}
