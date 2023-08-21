import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {AuthenticationService} from "../services/authentication.service";
 // Обновите этот путь

interface CanActivate {
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree
}

@Injectable({
  providedIn: 'root'
})
export class AccessGuardService implements CanActivate{
  private isLoggedIn!: boolean;

  constructor(private router:Router, private authService: AuthenticationService) {
    this.authService.isLoggedIn().subscribe(loggedIn => this.isLoggedIn = loggedIn);
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.isLoggedIn) {
      return true;
    } else {
      this.router.navigate(['login']);
      return false;
    }
  }
}
