import {Component} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication.service";
import {User} from "../../model/user";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {

  form = this.fb.group({
    email: ["", [Validators.pattern(/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/)]],
    password: ["", Validators.required],
  });

  restoreForm = this.fb.group({
    email: ["", [Validators.pattern(/^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/)]],
  });

  badCredentials: boolean = false
  visible: boolean = false;

  errorMessage: string | null = null;

  loading: boolean = false;

  isSuccess: boolean = false;

  constructor(private fb: FormBuilder,
              private auth: AuthenticationService,
              private rotuer: Router,
              private messageService: MessageService) {

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


  showDialog() {
    this.visible = true;
  }

  onSubmitRestore() {
    if (this.restoreForm.valid) {
      const email = this.restoreForm.value.email;
      const lang = localStorage.getItem('lang') || "ro"; // Get lang from localStorage
      if (email) {
        this.loading = true;
        this.auth.restorePassword(email, lang).subscribe({
          next: () => {
            this.visible = false;
            this.loading = false;
            this.errorMessage = null;
            this.isSuccess = true; // Set isSuccess to true
            this.restoreForm.controls.email.reset();
          },
          error: (err) => {
            this.loading = false;
            if (err.status === 404) {
              this.errorMessage = err.error.message;
            } else {
              console.log(err);
              this.errorMessage = err.error.message;
            }
          }
        });
      }
    }
  }

}
