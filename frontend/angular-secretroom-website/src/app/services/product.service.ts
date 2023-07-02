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


  getProductByGroupId(categoryId: string | null): Observable<Product[]> {
    return this.httpClient.get<GetResponse> (`${this.baseUrL}/${categoryId}`).pipe(
      map(response => response.content)
    )
  }

  getProductAllProducts(brand: string) {
   return this.httpClient.get<GetResponse> (`${this.baseUrL}/${brand}`).pipe(
      map(response => response.content)
    )
  }
}

interface GetResponse {
  content: Product[]
}
