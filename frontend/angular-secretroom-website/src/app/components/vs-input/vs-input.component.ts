import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-vs-input',
  templateUrl: './vs-input.component.html',
  styleUrls: ['./vs-input.component.css']
})
export class VsInputComponent {
  @Input() label!: string;
  @Input() id!: string;
  @Input() type: string = 'text';
}
