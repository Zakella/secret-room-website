import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {Product} from "../model/product";
import {HttpParams } from "@angular/common/http";
import { catchError } from 'rxjs/operators';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrL = environment.apiUrl + "v1/products";

  constructor(private httpClient: HttpClient) {}

  getProductsByGroupId(categoryId: string | null, thePage: number, thePageSize: number): Observable<GetResponse> {
    let params = new HttpParams()
      .set('page', thePage.toString())
      .set('size', thePageSize.toString());

    return this.httpClient.get<GetResponse>(`${this.baseUrL}/${categoryId}`, { params: params })
      .pipe(catchError(this.handleError));
  }

  getAllProductsByBrand(brand: string, thePage: number, thePageSize: number): Observable<GetResponse> {
    let params = new HttpParams()
      .set('page', thePage.toString())
      .set('size', thePageSize.toString());

    return this.httpClient.get<GetResponse>(`${this.baseUrL}/${brand}`,  { params: params })
      .pipe(catchError(this.handleError));
  }

  search(query: string, brand: string, thePage: number, thePageSize: number): Observable<GetResponse> {
    let params = new HttpParams()
      .set('name', query)
      .set('page', thePage.toString())
      .set('size', thePageSize.toString());

    return this.httpClient.get<GetResponse>(`${this.baseUrL}/${brand}/searchByNameContaining`, { params: params })
      .pipe(catchError(this.handleError));
  }

  private handleError(error: any) {
    console.error('Something has gone wrong', error);
    return throwError(error.message || error);
  }

  getProductById(id: string | null): Observable<Product> {
    return this.httpClient.get<Product>(`${this.baseUrL}/findProduct/${id}`)
      .pipe(catchError(this.handleError));
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
