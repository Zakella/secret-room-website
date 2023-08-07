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

    // const tokenEndpoint = 'https://keycloak.victoriasecret.md/realms/srWebTest/protocol/openid-connect/token';
    //
    //
    // const body = new HttpParams()
    //   .set('client_id', 'test2')
    //   .set('grant_type', 'password')
    //   .set('username', "Slava")
    //   .set('password', '456');
    //
    // this.httpClient.post(tokenEndpoint, body.toString(), {
    //   headers: {
    //     'Content-Type': 'application/x-www-form-urlencoded'
    //   }
    // }).subscribe(response => {
    //   // Обработка ответа. Сохраните токен и т.д.
    //   console.log(response);
    // }, error => {
    //   // Обработка ошибок.
    //   console.error('Error:', error);
    // });


  }
}
