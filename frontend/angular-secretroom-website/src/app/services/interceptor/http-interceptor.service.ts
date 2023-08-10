import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

import {JwtHelperService} from "@auth0/angular-jwt";
import {UserDetails} from "../../model/user-details";
import {ProtectedEndpoints} from "../../model/protected-endpoint";

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  constructor() {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log("Request intercepted");

    const storedUser = localStorage.getItem("user");

    if (storedUser && storedUser !== "null") {
      const userDetails: UserDetails = JSON.parse(storedUser);
      const token = userDetails.accessToken;

      if (token && this.isProtectedEndpoint(req.url)) {
        const autReq = req.clone({
            headers: new HttpHeaders({Authorization: "Bearer " + token})
          }
        );

        return next.handle(autReq);
      }
    }
    return next.handle(req);
  }

  private isProtectedEndpoint(url: string): boolean {
    return ProtectedEndpoints.ENDPOINTS.some(endpoint => url.includes(endpoint));
  }
}
