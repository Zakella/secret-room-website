import { Component } from '@angular/core';
import {Router} from "@angular/router";


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

  constructor(private router: Router) {
  }

  goToLoginPage() {
    this.router.navigate(['/login']);
    // this.authService.login();
  }


}
