import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VsLogoComponent } from './vs-logo.component';

describe('VsLogoComponent', () => {
  let component: VsLogoComponent;
  let fixture: ComponentFixture<VsLogoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VsLogoComponent]
    });
    fixture = TestBed.createComponent(VsLogoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
