import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LanguagePanelComponent } from './language-panel.component';

describe('LanguagePannelComponent', () => {
  let component: LanguagePanelComponent;
  let fixture: ComponentFixture<LanguagePanelComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LanguagePanelComponent]
    });
    fixture = TestBed.createComponent(LanguagePanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
