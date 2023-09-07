import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ShippingOption} from "../model/shipping-option";
import {environment} from "../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class ShippingService {

  private apiUrl = environment.shopApiUrl + 'v1/shipping';

  constructor(private http: HttpClient) { }

  getShippingOptions(): Observable<ShippingOption[]> {
    return this.http.get<ShippingOption[]>(this.apiUrl);
  }
}
