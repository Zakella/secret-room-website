import { Component } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import { HttpClient, HttpParams } from '@angular/common/http';
import {AuthenticationService} from "../../services/authentication.service";
import {User} from "../../model/user";

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

  constructor(private fb: FormBuilder,
              private httpClient: HttpClient,
              private auth: AuthenticationService) {

  }


  onSubmit() {

    // if (this.form.valid) {
    //   const user: User = new User(this.form.value.email || '', this.form.value.password|| '');
    // }



    // this.auth.login(this.form.value).subscribe({
    //
    //   next: (data) => {
    //     console.log(data);
    //   }, error: (err) => {
    //     console.log(err);
    //   }
    //
    // }}
    //
    //
    //
    //
    //



  }
}
