import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Product} from "../model/product/Product";
import {map} from 'rxjs/operators'

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrL = "http://localhost:8081/api/v1/products";

  constructor(private httpClient:HttpClient) {}

  getProductsByGroupId(categoryId: string | null): Observable<Product[]> {
    return this.httpClient.get<GetResponse>(`${this.baseUrL}/${categoryId}`).pipe(
      map(response => response ? response.content : []) // Check if response is null and return an empty array in that case
    );
  }

  getAllProductsByBrand(brand: string): Observable<Product[]> {
    return this.httpClient.get<GetResponse>(`${this.baseUrL}/${brand}`).pipe(
      map(response => response ? response.content : []) // Check if response is null and return an empty array in that case
    );
  }

  search(query: string, brand: string): Observable<Product[]> {
    return this.httpClient.get<GetResponse>(`${this.baseUrL}/${brand}/searchByNameContaining?name=${query}`).pipe(
      map(response => response ? response.content : []) // Check if response is null and return an empty array in that case
    );
  }


  getProductById(id: string | null): Observable<Product> {
    return this.httpClient.get<Product>(`${this.baseUrL}/findProduct/${id}`);
  }



}

interface GetResponse {
  content: Product[]
}
