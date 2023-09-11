import {Component, OnInit} from '@angular/core';
import {FormBuilder, ValidatorFn, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication.service";
import {Router, ActivatedRoute} from "@angular/router";
import {TranslocoService} from "@ngneat/transloco";


@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  isSuccess: boolean = false;


  passwordValidator: ValidatorFn[] = [
    Validators.required,
    Validators.minLength(8),
    Validators.pattern('^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$')
  ];

  form = this.fb.group({
    password: ["", this.passwordValidator],
    passwordConfirmation: ["", Validators.required],
  });

  errorMessage: string | null = null;
  token: string | null = null;



  constructor(private fb: FormBuilder,
              private route: ActivatedRoute,
              private authenticationService: AuthenticationService,
              private router: Router,
              private translocoService: TranslocoService

  ) {
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
      this.errorMessage = this.translocoService.translate('resetPassword.passwordsDoNotMatch');
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
            this.errorMessage = this.translocoService.translate('resetPassword.invalidOrExpiredToken');
          } else {
            this.errorMessage = this.translocoService.translate('resetPassword.errorOccurred');
            console.log(error);
          }
        }
      });
    } else {
      this.errorMessage = this.translocoService.translate('resetPassword.tokenMissing');
    }
  }

  goToLoginPage() {
    this.router.navigate(['/login'], {queryParams: {resetPassword: 'success'}});
  }
}
