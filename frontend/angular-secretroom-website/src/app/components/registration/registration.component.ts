import { Component } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {

  form = this.fb.group({
    email: ["", Validators.required],
    firstName: ["", Validators.required],
    lastName: ["", Validators.required],
    password: ["",Validators.required],
  });

  constructor(private fb: FormBuilder) {}

  onSubmit($event: any) {

  }
}
