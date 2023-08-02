import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VsInputComponent } from './vs-input.component';

describe('VsInputComponent', () => {
  let component: VsInputComponent;
  let fixture: ComponentFixture<VsInputComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VsInputComponent]
    });
    fixture = TestBed.createComponent(VsInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
