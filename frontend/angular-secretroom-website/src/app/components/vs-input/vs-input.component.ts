import { Component, Input, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, Validators } from '@angular/forms';

@Component({
  selector: 'app-vs-input',
  templateUrl: './vs-input.component.html',
  styleUrls: ['./vs-input.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => VsInputComponent),
      multi: true
    }
  ]
})
export class VsInputComponent implements ControlValueAccessor {
  @Input() label!: string;
  @Input() id!: string;
  @Input() type: string = 'text';
  @Input() disabled: boolean = false;
  @Input() minLength?: number;
  @Input() maxLength?: number;
  @Input() mask: string = '';
  @Input() prefix: string = '';
  @Input() invalidLabel: boolean = false;
  @Input() highlightWhenEmpty: boolean = false;


  value: string = '';
  onChange = (value: string) => {};
  onTouched = () => {};
  isInvalid: boolean = false;

  writeValue(value: string): void {
    this.value = value;
    this.isInvalid = !value;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  onBlur(): void {
    this.isInvalid = !this.value;
    this.onTouched();
  }
}
