import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BbLogoComponent } from './bb-logo.component';

describe('BbLogoComponent', () => {
  let component: BbLogoComponent;
  let fixture: ComponentFixture<BbLogoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BbLogoComponent]
    });
    fixture = TestBed.createComponent(BbLogoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
