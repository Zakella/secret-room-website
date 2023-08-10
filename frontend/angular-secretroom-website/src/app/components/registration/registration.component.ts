import {Component} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {AuthenticationService} from "../../services/authentication.service";
import {User} from "../../model/user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],

})
export class RegistrationComponent {
  registrationForm = this.fb.group({
    email: [null, Validators.required],
    password: [null, Validators.required],
    firstName: [null, Validators.required],
    lastName: [null, Validators.required],
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
        this.registrationForm.value.lastName || ''
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
