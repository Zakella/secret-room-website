import { Component } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
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
