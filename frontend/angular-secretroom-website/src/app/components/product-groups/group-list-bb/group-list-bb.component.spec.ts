import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupListBbComponent } from './group-list-bb.component';

describe('GroupListBbComponent', () => {
  let component: GroupListBbComponent;
  let fixture: ComponentFixture<GroupListBbComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GroupListBbComponent]
    });
    fixture = TestBed.createComponent(GroupListBbComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
