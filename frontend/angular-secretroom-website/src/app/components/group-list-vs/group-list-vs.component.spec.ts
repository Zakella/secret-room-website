import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupListVsComponent } from './group-list-vs.component';

describe('GroupListVsComponent', () => {
  let component: GroupListVsComponent;
  let fixture: ComponentFixture<GroupListVsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GroupListVsComponent]
    });
    fixture = TestBed.createComponent(GroupListVsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
