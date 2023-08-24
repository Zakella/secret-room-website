import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication.service";
import {Router, ActivatedRoute} from "@angular/router";


@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  isSuccess: boolean = false;

  form = this.fb.group({
    password: ["", Validators.required],
    passwordConfirmation: ["", Validators.required],
  });

  errorMessage: string | null = null;
  token: string | null = null;

  constructor(private fb: FormBuilder,
              private route: ActivatedRoute,
              private authenticationService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.token = params['token'];
    });
  }

  onSubmit() {
    this.errorMessage = null;

    const {password, passwordConfirmation} = this.form.value;
    if (!password) {
      return
    }

    if (password !== passwordConfirmation) {
      this.errorMessage = 'Passwords do not match';
      return;
    }


    if (this.token) {
      this.authenticationService.resetPassword(this.token, password).subscribe({
        next: () => {
          this.isSuccess = true;
          // Handle successful password reset
          // this.router.navigate(['/login'], {queryParams: {resetPassword: 'success'}});
        },
        error: error => {
          if (error.status === 404) {
            this.errorMessage = 'Invalid or expired token';
          } else {
            // If the error response has a message, use it. Otherwise, use a generic error message.
            this.errorMessage = error.error.message || 'An error occurred while resetting password';
            console.log(error);
          }
        }
      });
    } else {
      this.errorMessage = 'Token is missing';
    }
  }

  goToLoginPage() {
    this.router.navigate(['/login'], {queryParams: {resetPassword: 'success'}});
  }
}
