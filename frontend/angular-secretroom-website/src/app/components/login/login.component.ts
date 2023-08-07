import { Component } from '@angular/core';
import {Router} from "@angular/router";
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile } from 'keycloak-js';
import {AuthService} from "../../authentication/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  // user = new User();
  //
  // public isLoggedIn = false;
  // public userProfile: KeycloakProfile | null = null;

  constructor(private authService: AuthService,) {
  }

  goToLoginPage() {
    // this.router.navigate(['/login']);
    this.authService.login();
  }


}
