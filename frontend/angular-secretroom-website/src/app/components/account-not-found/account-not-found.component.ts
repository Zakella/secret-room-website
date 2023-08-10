import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-account-not-found',
  templateUrl: './account-not-found.component.html',
  styleUrls: ['./account-not-found.component.css']
})
export class AccountNotFoundComponent {

  constructor(private  router:Router) {
  }
  goHome() {
    const brand = this.router.url.includes('vs') ? 'vs' : 'bb';
    this.router.navigate([brand])

  }

}
