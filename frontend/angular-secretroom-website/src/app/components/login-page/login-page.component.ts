import { Component } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication.service";
import {User} from "../../model/user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {

  form = this.fb.group({
    email: ["", [Validators.pattern(/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/)]],
    password: ["",Validators.required],
  });
  badCredentials: boolean = false

  constructor(private fb: FormBuilder,
              private auth: AuthenticationService,
              private rotuer: Router  ) {

  }


  onSubmit() {
    if (this.form.valid) {
      const email = this.form.value.email || '';
      const password = this.form.value.password || '';
      const user: User = new User(email, password, "", "");
      this.auth.login(user).subscribe({
        next: (authResponse) => {
          localStorage.setItem('user', JSON.stringify(authResponse))
          this.rotuer.navigate(['/myAccount']);

        },
        error: (err) => {
          console.log(err);
          if (err.status === 401) {
            this.badCredentials = true;
            setTimeout(() => this.badCredentials = false, 3000); // Hide the message after 5 seconds

          }
        }
      });
    }
  }

}
