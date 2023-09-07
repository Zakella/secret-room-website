import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Order} from '../model/order';
import {catchError} from "rxjs/operators";
import {ResponseOrder} from "../model/response-order";
import {environment} from "../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private orderUrl = environment.shopApiUrl + 'v1/purchase';

  constructor(private http: HttpClient) {
  }



}
