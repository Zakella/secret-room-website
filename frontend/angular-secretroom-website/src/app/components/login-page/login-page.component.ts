import { Component } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {CartService} from "../../services/cart.service";
import {Router} from "@angular/router";
import {PurchaseService} from "../../services/purchase.service";
import {ShippingService} from "../../services/shipping.service";
import {MessageService} from "primeng/api";
import { HttpClient, HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {

  form = this.fb.group({
    email: ["", Validators.required],
    password: ["",Validators.required],
  });

  constructor(private fb: FormBuilder, private httpClient: HttpClient) {

    // this.keycloak.
  }


  onSubmit($event: any) {



  }
}
