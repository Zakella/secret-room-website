import { Component, Input, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

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

  private _value: any = '';
  onChange: any = () => {};
  onTouch: any = () => {};

  get value() {
    return this._value;
  }

  set value(val) {
    this._value = val;
    this.onChange(val);
    this.onTouch(val);
  }

  writeValue(value: any): void {
    this.value = value;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouch = fn;
  }
}
