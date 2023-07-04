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


  getProductsByGroupId(categoryId: string | null): Observable<Product[]> {
    return this.httpClient.get<GetResponse> (`${this.baseUrL}/${categoryId}`).pipe(
      map(response => response.content)
    )
  }

  getAllProductsByBrand(brand: string) {
   return this.httpClient.get<GetResponse> (`${this.baseUrL}/${brand}`).pipe(
      map(response => response.content)
    )
  }


  search(query: string, brand:string) {
    return this.httpClient.get<GetResponse> (`${this.baseUrL}/${brand}/searchByNameContaining?name=${query}`).pipe(
      map(response => response.content)
    )
  }


}

interface GetResponse {
  content: Product[]
}
