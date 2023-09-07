import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ProductCategory} from "../model/product-category";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ProductCategoryService {

  private baseUrL = environment.shopApiUrl +  "v1/product-category"

  constructor(private httpClient: HttpClient) {
  }

  getProductList(): Observable<ProductCategory[]> {
    return this.httpClient.get<ProductCategory[]>(this.baseUrL);
  }
  // getProductList(): Observable<any[]> {
  //   return this.httpClient.get<any[]>(this.baseUrL);
  // }
}
