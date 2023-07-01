import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Product} from "../model/product/product";
import {map} from 'rxjs/operators'

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrL = "http://localhost:8081/api/v1/products"
  constructor(private httpClient:HttpClient) {}

  getProductList() : Observable<Product[]> {
    return this.httpClient.get<GetResponse> (this.baseUrL).pipe(
      map(response => response._embedded.products)
    )
  }

  getProductByGroupId(categoryId: number): Observable<Product[]> {
    return this.httpClient.get<Product[]>(`${this.baseUrL}/${categoryId}`);
  }

}

interface GetResponse {
  _embedded : {
    products: Product[];
  }
}
