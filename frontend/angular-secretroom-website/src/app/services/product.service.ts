import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Product } from "../model/product/Product";
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrL = "http://localhost:8081/api/v1/products";

  constructor(private httpClient: HttpClient) {}

  getProductsByGroupId(categoryId: string | null, thePage: number, thePageSize: number): Observable<GetResponse> {
    return this.httpClient.get<GetResponse>(`${this.baseUrL}/${categoryId}?page=${thePage}&size=${thePageSize}`);
  }

  getAllProductsByBrand(brand: string, thePage: number, thePageSize: number): Observable<GetResponse> {
    return this.httpClient.get<GetResponse>(`${this.baseUrL}/${brand}?page=${thePage}&size=${thePageSize}`);
  }

  search(query: string, brand: string, thePage: number, thePageSize: number): Observable<GetResponse> {
    return this.httpClient.get<GetResponse>(`${this.baseUrL}/${brand}/searchByNameContaining?name=${query}`);
  }

  getProductById(id: string | null): Observable<Product> {
    return this.httpClient.get<Product>(`${this.baseUrL}/findProduct/${id}`);
  }
}

interface GetResponse {
  content: Product[];
  pageable: {
    pageNumber: number;
    pageSize: number;
  };
  totalElements: number;
}
