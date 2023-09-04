import {Component} from '@angular/core';
import {FormBuilder, ValidatorFn, Validators} from '@angular/forms';
import {AuthenticationService} from "../../services/authentication.service";
import {User} from "../../model/user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],

})
export class RegistrationComponent {

  nameValidator: ValidatorFn[] = [
    Validators.required,
    Validators.pattern('^[A-Za-z\\s]*$')
  ];

  passwordValidator: ValidatorFn[] = [
    Validators.required,
    Validators.minLength(8),
    Validators.pattern('^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$')
  ];

  registrationForm = this.fb.group({
    email: ["", [Validators.required ,Validators.pattern(/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/)]],
    password: ["", this.passwordValidator],
    firstName: ["", this.nameValidator],
    lastName: ["", this.nameValidator],
  });
  userExists: boolean = false;

  constructor(
    private fb: FormBuilder,
    private authenticationService: AuthenticationService,
    private router: Router
  ) {
  }

  onSubmit(): void {
    if (this.registrationForm.valid) {
      const user = new User(
        this.registrationForm.value.email || '',
        this.registrationForm.value.password || '',
        this.registrationForm.value.firstName || '',
        this.registrationForm.value.lastName  || ''
      );

      this.authenticationService.registration(user).subscribe(
        {
          next: authResponse => {

            localStorage.setItem('user', JSON.stringify(authResponse))
            this.router.navigate(["myAccount"])

          },
          error: error => {
            console.log(error);

            if (error.status === 400) {
              console.error('Bad Request', error.error);
            }

            if (error.status === 409) {
              this.userExists  = true;
              setTimeout(() => this.userExists = false, 3000); // Hide the message after 5 seconds

            }

          }
        }
      );
    }
  }
}
