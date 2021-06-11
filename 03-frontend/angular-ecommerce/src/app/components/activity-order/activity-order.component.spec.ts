import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityOrderComponent } from './activity-order.component';

describe('ActivityOrderComponent', () => {
  let component: ActivityOrderComponent;
  let fixture: ComponentFixture<ActivityOrderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ActivityOrderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivityOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
